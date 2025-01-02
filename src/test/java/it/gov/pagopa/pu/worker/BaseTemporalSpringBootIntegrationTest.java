package it.gov.pagopa.pu.worker;

import com.uber.m3.tally.NoopScope;
import io.temporal.activity.ActivityOptions;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowExecutionStatus;
import io.temporal.api.workflow.v1.WorkflowExecutionInfo;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.internal.client.WorkflowClientHelper;
import io.temporal.workflow.Workflow;
import it.gov.pagopa.pu.worker.ingestionflowfile.repository.IngestionFlowFileRepository;
import it.gov.pagopa.pu.worker.paymentsreporting.repository.PaymentsReportingRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;
import java.util.function.Consumer;

@SpringBootTest(classes = WorkerApplication.class)
@EnableAutoConfiguration(exclude = {
  DataSourceAutoConfiguration.class,
  DataSourceTransactionManagerAutoConfiguration.class,
  HibernateJpaAutoConfiguration.class})
@TestPropertySource(properties = {
  "spring.temporal.test-server.enabled: true",
  "spring.temporal.workers-auto-discovery.packages: it.gov.pagopa.pu.worker.wf"
})
public abstract class BaseTemporalSpringBootIntegrationTest {

  @MockitoBean
  private EntityManagerFactory entityManagerFactoryMock;

  @MockitoBean
  private IngestionFlowFileRepository ingestionFlowFileRepositoryMock;
  @MockitoBean
  private PaymentsReportingRepository paymentsReportingRepositoryMock;

  @Autowired
  private WorkflowClient temporalClient;

  protected static <T> T buildActivityStub(Class<T> activityClass) {
    return Workflow.newActivityStub(activityClass, ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(1)).build());
  }

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
    } while (!WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_COMPLETED.equals(info.getStatus()));
  }

}
