package it.gov.pagopa.pu.worker.ingestionflowfile.mapper;

import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.gov.pagopa.pu.worker.util.TestUtils.checkNotNullFields;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IngestionFlowFileMapperTest {
  private IngestionFlowFileMapper mapper;

  @BeforeEach
  void setUp() { mapper = new IngestionFlowFileMapper(); }

  @Test
  void mapIngestionFlowFile2DTO() {
    IngestionFlowFile model = IngestionFlowFile.builder().ingestionFlowFileId(123L).build();;
    IngestionFlowFileDTO result = mapper.mapIngestionFlowFile2DTO(model);

    assertNotNull(result);
    checkNotNullFields(result);
  }

  @Test
  void mapIngestionFlowFileDTO2Model() {
    IngestionFlowFileDTO dto = IngestionFlowFileDTO.builder().build();
    IngestionFlowFile result = mapper.mapIngestionFlowFileDTO2Model(dto);
    assertNotNull(result);
    checkNotNullFields(result);
  }
}
