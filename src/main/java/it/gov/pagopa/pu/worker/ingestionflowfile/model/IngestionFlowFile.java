package it.gov.pagopa.pu.worker.ingestionflowfile.model;

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
  private String flowFileType;
  private int version;
  private Long org;
  private String status;
  private String iuf;
  private int numTotalRows;
  private int numCorrectlyImportedRows;
  private Instant creationDate;
  private Instant lastUpdateDate;
  private boolean flagActive;
  private String operatorName;
  private boolean flagSpontaneous;
  private String filePathName;
  private String fileName;
  private int pdfGenerated;
  private String codRequestToken;
  private String codError;
  private String pspIdentifier;
  private LocalDateTime flowDateTime;
  private String state;
  private String fileSourceCode;
  private String discardFileName;
}
