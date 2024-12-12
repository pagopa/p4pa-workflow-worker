package it.gov.pagopa.pu.worker.paymentsreporting.service;

import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import it.gov.pagopa.pu.worker.paymentsreporting.repository.PaymentsReportingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsReportingService {
    private final PaymentsReportingRepository repository;

	public PaymentsReportingService(PaymentsReportingRepository repository) {
		this.repository = repository;
	}

    public List<PaymentsReporting> saveAll(List<PaymentsReporting> paymentsReportings) {
        return repository.saveAll(paymentsReportings);
    }
}
