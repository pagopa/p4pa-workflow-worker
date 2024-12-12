package it.gov.pagopa.pu.worker.ingestionflowfile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

}
