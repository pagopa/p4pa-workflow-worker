package it.gov.pagopa.pu.worker.paymentsreporting.service;

import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import it.gov.pagopa.pu.worker.paymentsreporting.repository.PaymentsReportingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentsReportingServiceTest {

    @Mock
    private PaymentsReportingRepository repositoryMock;

    private PaymentsReportingService service;

    @BeforeEach
    void setUp() {
        service = new PaymentsReportingService(repositoryMock);
    }

    @Test
    void whenSaveAllThenVerifyNewIds() {
        // Arrange
        PaymentsReporting report1 = new PaymentsReporting();
        PaymentsReporting report2 = new PaymentsReporting();
        List<PaymentsReporting> reports = List.of(report1, report2);

        when(repositoryMock.saveAll(reports)).thenReturn(reports);

        // Act
        List<PaymentsReporting> savedReports = service.saveAll(reports);

        // Assert
        assertEquals(2, savedReports.size());
        verify(repositoryMock, times(1)).saveAll(reports);
    }
}
