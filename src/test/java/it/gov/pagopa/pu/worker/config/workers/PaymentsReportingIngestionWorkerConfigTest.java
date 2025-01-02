// PaymentsReportingIngestionWorkerConfigTest.java
package it.gov.pagopa.pu.worker.config.workers;

import com.uber.m3.tally.Scope;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import it.gov.pagopa.payhub.activities.dao.IngestionFlowFileDao;
import it.gov.pagopa.payhub.activities.service.ingestionflow.IngestionFlowFileRetrieverService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.FlussoRiversamentoUnmarshallerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentsReportingIngestionWorkerConfigTest {

  @Mock
  private IngestionFlowFileDao ingestionFlowFileDao;
  @Mock
  private IngestionFlowFileRetrieverService ingestionFlowFileRetrieverService;
  @Mock
  private FlussoRiversamentoUnmarshallerService flussoRiversamentoUnmarshallerService;

  @InjectMocks
  private PaymentsReportingIngestionWorkerConfig config;

  @Value("${app.archive-relative-path:processed/}")
  private String archiveRelativePath;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testWorkflowServiceStubsBean() {
    WorkflowServiceStubs serviceStubs = config.workflowServiceStubs();
    assertNotNull(serviceStubs);
  }

  @Test
  void testWorkflowClientBean() {
    WorkflowServiceStubs serviceStubs = mock(WorkflowServiceStubs.class);
    WorkflowServiceStubsOptions serviceStubsOptions = mock(WorkflowServiceStubsOptions.class);
    Scope metricsScope = mock(Scope.class);
    when(serviceStubsOptions.getMetricsScope()).thenReturn(metricsScope);
    when(serviceStubs.getOptions()).thenReturn(serviceStubsOptions);

    WorkflowClient client = config.workflowClient(serviceStubs);
    assertNotNull(client);
  }


  @Test
  void testWorkerBean() {
    WorkerFactory factory = mock(WorkerFactory.class);
    Worker worker = mock(Worker.class);
    when(factory.newWorker(anyString())).thenReturn(worker);

    Worker result = config.worker(factory, ingestionFlowFileDao,
      ingestionFlowFileRetrieverService, flussoRiversamentoUnmarshallerService);
    assertNotNull(result);
    verify(factory).newWorker(PaymentsReportingIngestionWorkerConfig.TASK_QUEUE);
  }


}
