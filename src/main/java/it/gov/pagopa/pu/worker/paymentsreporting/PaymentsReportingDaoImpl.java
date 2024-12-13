package it.gov.pagopa.pu.worker.paymentsreporting;

import it.gov.pagopa.payhub.activities.dao.PaymentsReportingDao;
import it.gov.pagopa.payhub.activities.dto.paymentsreporting.PaymentsReportingDTO;
import it.gov.pagopa.pu.worker.paymentsreporting.mapper.PaymentsReportingMapper;
import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import it.gov.pagopa.pu.worker.paymentsreporting.service.PaymentsReportingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsReportingDaoImpl implements PaymentsReportingDao {
    private final PaymentsReportingService paymentsReportingService;
    private final PaymentsReportingMapper paymentsReportingMapper;

	public PaymentsReportingDaoImpl(PaymentsReportingService paymentsReportingService,
                                    PaymentsReportingMapper paymentsReportingMapper) {
		this.paymentsReportingService = paymentsReportingService;
		this.paymentsReportingMapper = paymentsReportingMapper;
	}

    @Override
    public List<PaymentsReportingDTO> saveAll(List<PaymentsReportingDTO> dtos) {
        List<PaymentsReporting> paymentsReportings = paymentsReportingService
            .saveAll(dtos.stream()
                .map(paymentsReportingMapper::mapPaymentsReportingDTO2Model)
                .toList());
        return paymentsReportings.stream()
            .map(paymentsReportingMapper::mapPaymentsReporting2DTO)
            .toList();
    }
}
