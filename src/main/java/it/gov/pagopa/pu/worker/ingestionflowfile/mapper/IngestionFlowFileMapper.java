package it.gov.pagopa.pu.worker.ingestionflowfile.mapper;

import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.payhub.activities.dto.OrganizationDTO;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import org.springframework.stereotype.Component;

@Component
public class IngestionFlowFileMapper {

  public IngestionFlowFile mapIngestionFlowFileDTO2Model(IngestionFlowFileDTO dto) {
    return IngestionFlowFile.builder()
      .ingestionFlowFileId(dto.getIngestionFlowFileId())
      .flowFileType(dto.getFlowFileType())
      .version(dto.getVersion())
      .organizationId(dto.getOrg().getOrgId())
      .status(dto.getStatus())
      .operatorExternalUserId(dto.getOperatorExternalUserId())
      .numTotalRows(dto.getNumTotalRows())
      .numCorrectlyImportedRows(dto.getNumCorrectlyImportedRows())
      .creationDate(dto.getCreationDate())
      .lastUpdateDate(dto.getLastUpdateDate())
      .flagActive(dto.isFlagActive())
      .flagSpontaneous(dto.getFlagSpontaneous())
      .filePathName(dto.getFilePathName())
      .fileName(dto.getFileName())
      .pdfGenerated(dto.getPdfGenerated().intValue())
      .codRequestToken(dto.getCodRequestToken())
      .codError(dto.getCodError())
      .pspIdentifier(dto.getPspIdentifier())
      .flowDateTime(dto.getFlowDateTime())
      .fileSourceCode(dto.getFileSourceCode())
      .fileSize(dto.getFileSize())
      .discardFileName(dto.getDiscardFileName())
      .build();
  }

  public IngestionFlowFileDTO mapIngestionFlowFile2DTO(IngestionFlowFile model) {
    return IngestionFlowFileDTO.builder()
      .ingestionFlowFileId(model.getIngestionFlowFileId())
      .operatorExternalUserId(model.getOperatorExternalUserId())
      .flowFileType(model.getFlowFileType())
      .version(model.getVersion())
      .org(OrganizationDTO.builder().orgId(model.getOrganizationId()).build())
      .status(model.getStatus())
      .numTotalRows(model.getNumTotalRows())
      .numCorrectlyImportedRows(model.getNumCorrectlyImportedRows())
      .creationDate(model.getCreationDate())
      .lastUpdateDate(model.getLastUpdateDate())
      .flagActive(model.isFlagActive())
      .flagSpontaneous(model.isFlagSpontaneous())
      .filePathName(model.getFilePathName())
      .fileName(model.getFileName())
      .pdfGenerated(Long.valueOf(model.getPdfGenerated()))
      .codRequestToken(model.getCodRequestToken())
      .codError(model.getCodError())
      .pspIdentifier(model.getPspIdentifier())
      .flowDateTime(model.getFlowDateTime())
      .fileSourceCode(model.getFileSourceCode())
      .fileSize(model.getFileSize())
      .discardFileName(model.getDiscardFileName())
      .build();
  }
}
