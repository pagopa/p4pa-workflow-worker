package it.gov.pagopa.pu.worker.wf;

import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.UpdateIngestionFlowStatusActivity;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.UpdateIngestionFlowStatusActivityImpl;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.email.SendEmailIngestionFlowActivity;
import it.gov.pagopa.payhub.activities.activity.ingestionflow.email.SendEmailIngestionFlowActivityImpl;
import it.gov.pagopa.payhub.activities.activity.paymentsreporting.PaymentsReportingIngestionFlowFileActivity;
import it.gov.pagopa.payhub.activities.activity.paymentsreporting.PaymentsReportingIngestionFlowFileActivityImpl;
import it.gov.pagopa.pu.worker.BaseTemporalSpringBootIntegrationTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class PaymentsReportingIngestionWFIntegrationTest extends BaseTemporalSpringBootIntegrationTest {
  private static final String TASK_QUEUE = "PaymentsReportingIngestionWF";

  @MockitoBean(enforceOverride = true)
  private PaymentsReportingIngestionFlowFileActivityImpl fileActivityMock;
  @MockitoBean(enforceOverride = true)
  private SendEmailIngestionFlowActivityImpl emailActivityMock;
  @MockitoBean(enforceOverride = true)
  private UpdateIngestionFlowStatusActivityImpl statusActivityMock;

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
      buildActivityStub(UpdateIngestionFlowStatusActivity.class).updateStatus(ingestionFlowFileId, "STATUS");
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
    Mockito.verify(statusActivityMock).updateStatus(ingestionFlowFileId, "STATUS");
    Mockito.verify(emailActivityMock).sendEmail(ingestionFlowFileId, true);
  }
}
