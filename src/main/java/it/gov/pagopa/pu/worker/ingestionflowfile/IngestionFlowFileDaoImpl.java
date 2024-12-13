package it.gov.pagopa.pu.worker.ingestionflowfile;

import it.gov.pagopa.payhub.activities.dao.IngestionFlowFileDao;
import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngestionFlowFileDaoImpl implements IngestionFlowFileDao {
  @Override
  public Optional<IngestionFlowFileDTO> findById(Long ingestionFlowFileId) {
    return Optional.empty();
  }

  @Override
  public boolean updateStatus(Long ingestionFlowFileId, String status) {
    return false;
  }
}
