package it.gov.pagopa.pu.worker.ingestionflowfile;

import it.gov.pagopa.payhub.activities.dto.IngestionFlowFileDTO;
import it.gov.pagopa.pu.worker.ingestionflowfile.mapper.IngestionFlowFileMapper;
import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import it.gov.pagopa.pu.worker.ingestionflowfile.repository.IngestionFlowFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngestionFlowFileDaoImplTest {

  @Mock
  private IngestionFlowFileRepository repositoryMock;
  @Mock
  private IngestionFlowFileMapper mapperMock;

  private IngestionFlowFileDaoImpl service;

  @BeforeEach
  void setUp() {
    service = new IngestionFlowFileDaoImpl(repositoryMock, mapperMock);
  }

  @Test
  void testFindById() {
    // Arrange
    Long id = 123L;
    IngestionFlowFile ingestionFlowFile = IngestionFlowFile.builder()
      .ingestionFlowFileId(id)
      .build();

    IngestionFlowFileDTO expectedDTO = IngestionFlowFileDTO.builder()
      .ingestionFlowFileId(id).build();

    when(mapperMock.mapIngestionFlowFile2DTO(ingestionFlowFile)).thenReturn(expectedDTO);
    when(repositoryMock.findById(id)).thenReturn(Optional.of(ingestionFlowFile));

    // Act
    Optional<IngestionFlowFileDTO> result = service.findById(id);

    // Assert
    assertEquals(Optional.of(expectedDTO), result);
  }
}
