package it.gov.pagopa.pu.worker.paymentsreporting.repository;

import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsReportingRepository extends JpaRepository<PaymentsReporting, Long> {
}
