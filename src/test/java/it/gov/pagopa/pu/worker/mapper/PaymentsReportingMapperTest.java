package it.gov.pagopa.pu.worker.mapper;

import it.gov.pagopa.payhub.activities.dto.paymentsreporting.PaymentsReportingDTO;
import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.gov.pagopa.pu.worker.util.TestUtils.checkNotNullFields;
import static it.gov.pagopa.pu.worker.util.faker.PaymentsReportingFakerBuilder.buildPaymentsReporting;
import static it.gov.pagopa.pu.worker.util.faker.PaymentsReportingFakerBuilder.buildPaymentsReportingDTO;
import static org.junit.jupiter.api.Assertions.*;

class PaymentsReportingMapperTest {
    private PaymentsReportingMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PaymentsReportingMapper();
    }


    @Test
    void mapPaymentsReporting2DTO() {
        PaymentsReporting model = buildPaymentsReporting();
        PaymentsReportingDTO result = mapper.mapPaymentsReporting2DTO(model);

        assertNotNull(result);
        checkNotNullFields(result);
    }

    @Test
    void mapPaymentsReportingDTO2Model() {
        PaymentsReportingDTO dto = buildPaymentsReportingDTO();
        PaymentsReporting result = mapper.mapPaymentsReportingDTO2Model(dto);

        assertNotNull(result);
        checkNotNullFields(result);
    }
}
