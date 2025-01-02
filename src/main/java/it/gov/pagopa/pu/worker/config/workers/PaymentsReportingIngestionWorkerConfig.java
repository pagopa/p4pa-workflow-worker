// PaymentsReportingIngestionWorkerConfig.java
package it.gov.pagopa.pu.worker.config.workers;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import it.gov.pagopa.payhub.activities.activity.paymentsreporting.PaymentsReportingIngestionFlowFileActivityImpl;
import it.gov.pagopa.payhub.activities.dao.IngestionFlowFileDao;
import it.gov.pagopa.payhub.activities.dao.PaymentsReportingDao;
import it.gov.pagopa.payhub.activities.service.ingestionflow.IngestionFlowFileArchiverService;
import it.gov.pagopa.payhub.activities.service.ingestionflow.IngestionFlowFileRetrieverService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.FlussoRiversamentoUnmarshallerService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.PaymentsReportingIngestionFlowFileValidatorService;
import it.gov.pagopa.payhub.activities.service.paymentsreporting.PaymentsReportingMapperService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
  "it.gov.pagopa.payhub"
})
public class PaymentsReportingIngestionWorkerConfig {

  public static final String TASK_QUEUE = "PaymentsReportingIngestionWF";

  private final PaymentsReportingIngestionFlowFileValidatorService paymentsReportingIngestionFlowFileValidatorService;
  private final PaymentsReportingMapperService paymentsReportingMapperService;
  private final PaymentsReportingDao paymentsReportingDao;
  private final IngestionFlowFileArchiverService ingestionFlowFileArchiverService;

  @Value("${app.archive-relative-path:processed/}")
  private String archiveRelativePath;

  public PaymentsReportingIngestionWorkerConfig(PaymentsReportingIngestionFlowFileValidatorService paymentsReportingIngestionFlowFileValidatorService,
                                                PaymentsReportingMapperService paymentsReportingMapperService,
                                                PaymentsReportingDao paymentsReportingDao,
                                                IngestionFlowFileArchiverService ingestionFlowFileArchiverService) {

    this.paymentsReportingIngestionFlowFileValidatorService = paymentsReportingIngestionFlowFileValidatorService;
    this.paymentsReportingMapperService = paymentsReportingMapperService;
    this.paymentsReportingDao = paymentsReportingDao;
    this.ingestionFlowFileArchiverService = ingestionFlowFileArchiverService;
  }

  @Bean
  @Qualifier("workflowServiceStubs")
  public WorkflowServiceStubs workflowServiceStubs() {
    return WorkflowServiceStubs.newLocalServiceStubs();
  }

  @Bean
  @Qualifier("workflowClient")
  public WorkflowClient workflowClient(@Qualifier("workflowServiceStubs") WorkflowServiceStubs service) {
    return WorkflowClient.newInstance(service);
  }

  @Bean
  @Qualifier("workerFactory")
  public WorkerFactory workerFactory(@Qualifier("workflowClient") WorkflowClient client) {
    return WorkerFactory.newInstance(client);
  }

  @Bean
  public Worker worker(@Qualifier("workerFactory") WorkerFactory factory,
                       IngestionFlowFileDao ingestionFlowFileDao,
                       IngestionFlowFileRetrieverService ingestionFlowFileRetrieverService,
                       FlussoRiversamentoUnmarshallerService flussoRiversamentoUnmarshallerService) {

    Worker worker = factory.newWorker(TASK_QUEUE);

    worker.registerActivitiesImplementations(new PaymentsReportingIngestionFlowFileActivityImpl(
      archiveRelativePath, ingestionFlowFileDao, ingestionFlowFileRetrieverService,
      flussoRiversamentoUnmarshallerService,
      paymentsReportingIngestionFlowFileValidatorService,
      paymentsReportingMapperService,
      paymentsReportingDao,
      ingestionFlowFileArchiverService));

    factory.start();
    return worker;
  }
}
