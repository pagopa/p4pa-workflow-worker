package it.gov.pagopa.pu.worker.service;

import it.gov.pagopa.payhub.activities.dto.paymentsreporting.PaymentsReportingDTO;
import it.gov.pagopa.pu.worker.paymentsreporting.mapper.PaymentsReportingMapper;
import it.gov.pagopa.pu.worker.paymentsreporting.PaymentsReportingDaoImpl;
import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import it.gov.pagopa.pu.worker.paymentsreporting.service.PaymentsReportingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentsReportingDaoImplTest {

    @Mock
    private PaymentsReportingService paymentsReportingServiceMock;

    @Mock
    private PaymentsReportingMapper paymentsReportingMapperMock;

    private PaymentsReportingDaoImpl service;

    @BeforeEach
    void setUp() {
        service = new PaymentsReportingDaoImpl(paymentsReportingServiceMock, paymentsReportingMapperMock);
    }

    @Test
    void testSaveAll() {
        // Arrange
        PaymentsReportingDTO dto1 = new PaymentsReportingDTO();
        PaymentsReportingDTO dto2 = new PaymentsReportingDTO();
        List<PaymentsReportingDTO> dtos = List.of(dto1, dto2);

        PaymentsReporting entity1 = new PaymentsReporting();
        PaymentsReporting entity2 = new PaymentsReporting();
        List<PaymentsReporting> entities = List.of(entity1, entity2);

        PaymentsReportingDTO returnedDto1 = new PaymentsReportingDTO();
        PaymentsReportingDTO returnedDto2 = new PaymentsReportingDTO();
        List<PaymentsReportingDTO> returnedDtos = List.of(returnedDto1, returnedDto2);

        when(paymentsReportingMapperMock.mapPaymentsReportingDTO2Model(dto1)).thenReturn(entity1);
        when(paymentsReportingMapperMock.mapPaymentsReportingDTO2Model(dto2)).thenReturn(entity2);
        when(paymentsReportingMapperMock.mapPaymentsReporting2DTO(entity1)).thenReturn(returnedDto1);
        when(paymentsReportingMapperMock.mapPaymentsReporting2DTO(entity2)).thenReturn(returnedDto2);
        when(paymentsReportingServiceMock.saveAll(entities)).thenReturn(entities);

        // Act
        List<PaymentsReportingDTO> result = service.saveAll(dtos);

        // Assert
        assertEquals(2, result.size());
        assertEquals(returnedDtos, result);
    }
}
