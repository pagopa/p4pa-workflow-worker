package it.gov.pagopa.pu.worker.ingestionflowfile.service;

import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import it.gov.pagopa.pu.worker.ingestionflowfile.repository.IngestionFlowFileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngestionFlowFileService {
  private final IngestionFlowFileRepository repository;

	public IngestionFlowFileService(IngestionFlowFileRepository repository) {
		this.repository = repository;
	}

  public Optional<IngestionFlowFile> getIngestionFlowFile(Long ingestionFlowFileId) {
    return repository.findById(ingestionFlowFileId);
  }
}
