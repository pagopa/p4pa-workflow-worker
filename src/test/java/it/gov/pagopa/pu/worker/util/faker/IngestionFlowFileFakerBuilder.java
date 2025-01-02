package it.gov.pagopa.pu.worker.util.faker;

import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.payhub.activities.dto.OrganizationDTO;
import it.gov.pagopa.payhub.activities.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;

import java.time.Instant;
import java.time.LocalDateTime;

public class IngestionFlowFileFakerBuilder {

  // Static builder for IngestionFlowFile entity
  public static IngestionFlowFile buildFakeIngestionFlowFile() {
    return IngestionFlowFile.builder()
      .ingestionFlowFileId(1L)
      .flowFileType(IngestionFlowFileType.PAYMENTS_REPORTING)
      .version(1)
      .organizationId(1001L)
      .status("ACTIVE")
      .numTotalRows(100L)
      .numCorrectlyImportedRows(95L)
      .creationDate(Instant.now())
      .lastUpdateDate(Instant.now())
      .flagActive(true)
      .operatorExternalUserId("MAPPEDEXTERNALUSERID")
      .flagSpontaneous(false)
      .filePathName("/path/to/file")
      .fileName("example_file.csv")
      .pdfGenerated(5)
      .codRequestToken("REQ123TOKEN")
      .codError("NO_ERROR")
      .pspIdentifier("PSP001")
      .flowDateTime(LocalDateTime.now())
      .status("VALID")
      .fileSourceCode("SRC001")
      .fileSize(10L)
      .discardFileName("discarded_file.csv")
      .build();
  }

  // Static builder for IngestionFlowFileDTO
  public static IngestionFlowFileDTO buildFakeIngestionFlowFileDTO() {
    return IngestionFlowFileDTO.builder()
      .ingestionFlowFileId(1L)
      .flowFileType(IngestionFlowFileType.PAYMENTS_REPORTING)
      .version(1)
      .org(OrganizationDTO.builder().orgId(123L).build())
      .status("ACTIVE")
      .numTotalRows(100L)
      .numCorrectlyImportedRows(95L)
      .creationDate(Instant.now())
      .lastUpdateDate(Instant.now())
      .flagActive(true)
      .operatorExternalUserId("MAPPEDEXTERNALUSERID")
      .flagSpontaneous(false)
      .filePathName("/path/to/file")
      .fileName("example_file.csv")
      .pdfGenerated(5L)
      .codRequestToken("REQ123TOKEN")
      .codError("NO_ERROR")
      .pspIdentifier("PSP001")
      .flowDateTime(LocalDateTime.now())
      .fileSourceCode("SRC001")
      .fileSize(10L)
      .discardFileName("discarded_file.csv")
      .build();
  }
}
