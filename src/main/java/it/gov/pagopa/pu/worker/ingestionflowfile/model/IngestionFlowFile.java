package it.gov.pagopa.pu.worker.ingestionflowfile.model;

import it.gov.pagopa.payhub.activities.enums.IngestionFlowFileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingestion_flow_file")
public class IngestionFlowFile implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingestion_flow_file_generator")
  @SequenceGenerator(name = "ingestion_flow_file_generator", sequenceName = "ingestion_flow_file_seq", allocationSize = 1)
  private Long ingestionFlowFileId;
  private IngestionFlowFileType flowFileType;
  private int version;
  private Long organizationId;
  private String status;
  private Long numTotalRows;
  private Long numCorrectlyImportedRows;
  private Instant creationDate;
  private Instant lastUpdateDate;
  private boolean flagActive;
  private String operatorExternalUserId;
  private boolean flagSpontaneous;
  private String filePathName;
  private String fileName;
  private Integer pdfGenerated;
  private String codRequestToken;
  private String codError;
  private String pspIdentifier;
  private LocalDateTime flowDateTime;
  private Long fileSize;
  private String fileSourceCode;
  private String discardFileName;
}
