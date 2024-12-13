package it.gov.pagopa.pu.worker.ingestionflowfile;

import it.gov.pagopa.payhub.activities.dao.IngestionFlowFileDao;
import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.pu.worker.ingestionflowfile.mapper.IngestionFlowFileMapper;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import it.gov.pagopa.pu.worker.ingestionflowfile.repository.IngestionFlowFileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngestionFlowFileDaoImpl implements IngestionFlowFileDao {
	private final IngestionFlowFileRepository repository;
	private final IngestionFlowFileMapper ingestionFlowFileMapper;

	public IngestionFlowFileDaoImpl(IngestionFlowFileRepository repository,
	                                IngestionFlowFileMapper ingestionFlowFileMapper) {
		this.repository = repository;
		this.ingestionFlowFileMapper = ingestionFlowFileMapper;
	}

	@Override
	public Optional<IngestionFlowFileDTO> findById(Long ingestionFlowFileId) {
		Optional<IngestionFlowFile> ingestionFlowFile = repository.findById(ingestionFlowFileId);
		return ingestionFlowFile.map(ingestionFlowFileMapper::mapIngestionFlowFile2DTO);
	}

	@Override
	public boolean updateStatus(Long ingestionFlowFileId, String status) {
		return false;
	}
}
