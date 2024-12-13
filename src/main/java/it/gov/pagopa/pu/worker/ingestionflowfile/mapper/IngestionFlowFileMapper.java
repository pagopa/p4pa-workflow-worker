package it.gov.pagopa.pu.worker.ingestionflowfile.mapper;

import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.payhub.activities.dto.OrganizationDTO;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class IngestionFlowFileMapper {

  public IngestionFlowFile mapIngestionFlowFileDTO2Model(IngestionFlowFileDTO dto) {
    return IngestionFlowFile.builder()
      .ingestionFlowFileId(dto.getIngestionFlowFileId())
      .flowFileType(dto.getFlowFileType())
      .version(dto.getVersion())
      .org(dto.getOrg().getOrgId())
      .status(dto.getStatus())
      .iuf(dto.getIuf())
      .numTotalRows(dto.getNumTotalRows().intValue())
      .numCorrectlyImportedRows(dto.getNumCorrectlyImportedRows().intValue())
      .creationDate(dto.getCreationDate().toInstant())
      .lastUpdateDate(dto.getLastUpdateDate().toInstant())
      .flagActive(dto.isFlagActive())
      .operatorName(dto.getOperatorName())
      .flagSpontaneous(dto.getFlagSpontaneous())
      .filePathName(dto.getFilePath())
      .fileName(dto.getFileName())
      .pdfGenerated(dto.getPdfGenerated().intValue())
      .codRequestToken(dto.getCodRequestToken())
      .codError(dto.getCodError())
      .build();
  }

  public IngestionFlowFileDTO mapIngestionFlowFile2DTO(IngestionFlowFile model) {
    return IngestionFlowFileDTO.builder()
      .ingestionFlowFileId(model.getIngestionFlowFileId())
      .flowFileType(model.getFlowFileType())
      .version(model.getVersion())
      .org(OrganizationDTO.builder().orgId(model.getOrg()).build())
      .status(model.getStatus())
      .iuf(model.getIuf())
      .numTotalRows(Long.valueOf(model.getNumTotalRows()))
      .numCorrectlyImportedRows(Long.valueOf(model.getNumCorrectlyImportedRows()))
      .creationDate(Date.from(model.getCreationDate()))
      .lastUpdateDate(Date.from(model.getLastUpdateDate()))
      .flagActive(model.isFlagActive())
      .operatorName(model.getOperatorName())
      .flagSpontaneous(model.isFlagSpontaneous())
      .filePath(model.getFilePathName())
      .fileName(model.getFileName())
      .pdfGenerated(Long.valueOf(model.getPdfGenerated()))
      .codRequestToken(model.getCodRequestToken())
      .codError(model.getCodError())
      .build();
  }
}
