package it.gov.pagopa.pu.worker.ingestionflowfile.service;

import it.gov.pagopa.pu.worker.ingestionflowfile.model.IngestionFlowFile;
import it.gov.pagopa.pu.worker.ingestionflowfile.repository.IngestionFlowFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngestionFlowFileServiceTest {

  @Mock
  private IngestionFlowFileRepository repositoryMock;

  private IngestionFlowFileService service;

  @BeforeEach
  void setUp() { service = new IngestionFlowFileService(repositoryMock); }

  @Test
  void whenGetIngestionFlowFileThenGetResult() {
    // Arrange
    Long id = 123L;
    IngestionFlowFile ingestionFlowFile = IngestionFlowFile.builder()
      .ingestionFlowFileId(id)
      .build();

    when(repositoryMock.findById(id)).thenReturn(Optional.of(ingestionFlowFile));

    // Act
    Optional<IngestionFlowFile> result = service.getIngestionFlowFile(id);

    // Assert
    assertEquals(Optional.of(ingestionFlowFile), result);
    verify(repositoryMock, times(1)).findById(id);
  }
}
