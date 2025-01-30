package it.gov.pagopa.pu.worker;

import com.uber.m3.tally.NoopScope;
import io.temporal.activity.ActivityOptions;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowExecutionStatus;
import io.temporal.api.workflow.v1.WorkflowExecutionInfo;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowFailedException;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ApplicationFailure;
import io.temporal.internal.client.WorkflowClientHelper;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.UpdateIngestionFlowStatusActivity;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.UpdateIngestionFlowStatusActivityImpl;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.email.SendEmailIngestionFlowActivity;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.email.SendEmailIngestionFlowActivityImpl;
import it.gov.pagopa.payhub.activities.activity.paymentsreporting.PaymentsReportingIngestionFlowFileActivity;
import it.gov.pagopa.payhub.activities.activity.paymentsreporting.PaymentsReportingIngestionFlowFileActivityImpl;
import it.gov.pagopa.payhub.activities.connector.processexecutions.IngestionFlowFileService;
import it.gov.pagopa.payhub.activities.exception.NotRetryableActivityException;
import it.gov.pagopa.pu.processexecutions.dto.generated.IngestionFlowFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;
import java.util.Set;
import java.util.function.Consumer;

@SpringBootTest(classes = WorkerApplication.class)
@TestPropertySource(properties = {
  "spring.temporal.test-server.enabled: true",
  "spring.temporal.workers-auto-discovery.packages: it.gov.pagopa.pu.worker",
  "folders.shared: build"
})
class TemporalSpringBootIntegrationTest {
//region base test configuration code
  private final Set<WorkflowExecutionStatus> wfTerminationStatuses = Set.of(
    WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_COMPLETED,
    WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_FAILED,
    WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_TERMINATED,
    WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_CANCELED
  );

  @Autowired
  private WorkflowClient temporalClient;

  protected static <T> T buildActivityStub(Class<T> activityClass) {
    return Workflow.newActivityStub(activityClass, ActivityOptions.newBuilder()
      .setStartToCloseTimeout(Duration.ofSeconds(1))
        .setRetryOptions(RetryOptions.newBuilder()
          .setDoNotRetry(NotRetryableActivityException.class.getName())
          .build())
      .build());
  }

  @SuppressWarnings("SameParameterValue")
  protected  <T> void execute(Class<T> wfInterface, String taskQueue, Consumer<T> wfStarter){
    String workflowId = "wfId";
    T wf = buildWorkflowStub(wfInterface, taskQueue, workflowId);
    wfStarter.accept(wf);
    waitUntilWfCompletion(workflowId);
  }

  protected <T> T buildWorkflowStub(Class<T> workflowClass, String taskQueue, String workflowId) {
    return temporalClient.newWorkflowStub(
      workflowClass,
      WorkflowOptions.newBuilder()
        .setTaskQueue(taskQueue)
        .setWorkflowId(workflowId)
        .build());
  }

  protected void waitUntilWfCompletion(String workflowId) {
    WorkflowExecutionInfo info;
    do {
      info = WorkflowClientHelper.describeWorkflowInstance(temporalClient.getWorkflowServiceStubs(), "default", WorkflowExecution.newBuilder().setWorkflowId(workflowId).build(), new NoopScope());
    } while (!wfTerminationStatuses.contains(info.getStatus()));

    Assertions.assertEquals(WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_COMPLETED, info.getStatus());
  }
//endregion

  private static final String TASK_QUEUE = "PaymentsReportingIngestionWF";

  @MockitoBean(enforceOverride = true)
  private PaymentsReportingIngestionFlowFileActivityImpl fileActivityMock;
  @MockitoBean(enforceOverride = true)
  private SendEmailIngestionFlowActivityImpl emailActivityMock;
  @MockitoSpyBean
  private UpdateIngestionFlowStatusActivityImpl statusActivitySpy;
  @MockitoBean(enforceOverride = true)
  private IngestionFlowFileService ingestionFlowFileServiceMock;

  @WorkflowInterface
  public interface PaymentsReportingIngestionDummyWF {
    @WorkflowMethod
    void execute(Long ingestionFlowFileId);
  }

  @WorkflowImpl(taskQueues = TASK_QUEUE)
  public static class PaymentsReportingIngestionDummyWFImpl implements PaymentsReportingIngestionDummyWF {

    @Override
    public void execute(Long ingestionFlowFileId) {
      buildActivityStub(PaymentsReportingIngestionFlowFileActivity.class).processFile(ingestionFlowFileId);
      buildActivityStub(UpdateIngestionFlowStatusActivity.class).updateStatus(ingestionFlowFileId, IngestionFlowFile.StatusEnum.COMPLETED, null, null);
      buildActivityStub(SendEmailIngestionFlowActivity.class).sendEmail(ingestionFlowFileId, true);
    }
  }

  @Test
  void test() {
    // Given
    long ingestionFlowFileId = 1L;

    // When
    execute(PaymentsReportingIngestionDummyWF.class, TASK_QUEUE, wf -> wf.execute(ingestionFlowFileId));

    // Then
    Mockito.verify(fileActivityMock).processFile(ingestionFlowFileId);
    Mockito.verify(statusActivitySpy).updateStatus(ingestionFlowFileId, IngestionFlowFile.StatusEnum.COMPLETED, null, null);
    Mockito.verify(emailActivityMock).sendEmail(ingestionFlowFileId, true);
  }

  @Test
  void testNotRetryableActivityExceptionOverride() {
    // Given
    long ingestionFlowFileId = 1L;

    NotRetryableActivityException expectedNestedException = new NotRetryableActivityException("DUMMYEXCEPTION") {};
    Mockito.when(ingestionFlowFileServiceMock.updateStatus(ingestionFlowFileId, IngestionFlowFile.StatusEnum.COMPLETED, null, null))
      .thenThrow(expectedNestedException);
    // When
    WorkflowFailedException result = Assertions.assertThrows(WorkflowFailedException.class, () -> execute(PaymentsReportingIngestionDummyWF.class, TASK_QUEUE, wf -> wf.execute(ingestionFlowFileId)));

    // Then
    Assertions.assertEquals(expectedNestedException.getClass().getName(), ((ApplicationFailure) result.getCause().getCause()).getType());
    Assertions.assertEquals(expectedNestedException.getMessage(), ((ApplicationFailure) result.getCause().getCause()).getOriginalMessage());
  }
}
