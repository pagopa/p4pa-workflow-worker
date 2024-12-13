package it.gov.pagopa.pu.worker.ingestionflowfile.mapper;

import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileMapper {

  public IngestionFlowFileDTO mapIngestionFlowFile2DTO(IngestionFlowFile model) {
    return IngestionFlowFileDTO.builder().build();
  }

  public IngestionFlowFile mapIngestionFlowFileDTO2Model(IngestionFlowFileDTO dto) {
    return IngestionFlowFile.builder().build();
  }
}
