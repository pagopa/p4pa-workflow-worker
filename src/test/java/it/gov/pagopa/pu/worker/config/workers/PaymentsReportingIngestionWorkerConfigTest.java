// PaymentsReportingIngestionWorkerConfigTest.java
package it.gov.pagopa.pu.worker.config.workers;

import com.uber.m3.tally.Scope;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import it.gov.pagopa.payhub.activities.dao.IngestionFlowFileDao;
import it.gov.pagopa.payhub.activities.dao.PaymentsReportingDao;
import it.gov.pagopa.payhub.activities.service.ingestionflow.IngestionFlowFileArchiverService;
import it.gov.pagopa.payhub.activities.service.ingestionflow.IngestionFlowFileRetrieverService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.FlussoRiversamentoUnmarshallerService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.PaymentsReportingIngestionFlowFileValidatorService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.PaymentsReportingMapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.Supplier;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "app.archive-relative-path=processed/")
class PaymentsReportingIngestionWorkerConfigTest {

  @Mock
  private PaymentsReportingIngestionFlowFileValidatorService paymentsReportingIngestionFlowFileValidatorService;
  @Mock
  private PaymentsReportingMapperService paymentsReportingMapperService;
  @Mock
  private PaymentsReportingDao paymentsReportingDao;
  @Mock
  private IngestionFlowFileArchiverService ingestionFlowFileArchiverService;
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
    verify(factory).newWorker( PaymentsReportingIngestionWorkerConfig.TASK_QUEUE);
  }


}
