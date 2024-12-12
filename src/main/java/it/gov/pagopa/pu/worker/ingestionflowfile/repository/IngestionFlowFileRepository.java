package it.gov.pagopa.pu.worker.ingestionflowfile.repository;

import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngestionFlowFileRepository extends JpaRepository<IngestionFlowFile, Long> {
}
