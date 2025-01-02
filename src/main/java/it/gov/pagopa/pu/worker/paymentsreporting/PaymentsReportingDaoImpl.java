package it.gov.pagopa.pu.worker.paymentsreporting;

import it.gov.pagopa.payhub.activities.dao.PaymentsReportingDao;
import it.gov.pagopa.payhub.activities.dto.paymentsreporting.PaymentsReportingDTO;
import it.gov.pagopa.pu.worker.paymentsreporting.mapper.PaymentsReportingMapper;
import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import it.gov.pagopa.pu.worker.paymentsreporting.repository.PaymentsReportingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsReportingDaoImpl implements PaymentsReportingDao {
  private final PaymentsReportingRepository repository;
  private final PaymentsReportingMapper paymentsReportingMapper;

  public PaymentsReportingDaoImpl(PaymentsReportingRepository repository,
                                  PaymentsReportingMapper paymentsReportingMapper) {
    this.repository = repository;
    this.paymentsReportingMapper = paymentsReportingMapper;
  }

  @Override
  public List<PaymentsReportingDTO> saveAll(List<PaymentsReportingDTO> dtos) {
    List<PaymentsReporting> paymentsReportings = repository
      .saveAll(dtos.stream()
        .map(paymentsReportingMapper::mapPaymentsReportingDTO2Model)
        .toList());
    return paymentsReportings.stream()
      .map(paymentsReportingMapper::mapPaymentsReporting2DTO)
      .toList();
  }

  @Override
  public List<PaymentsReportingDTO> findByOrganizationIdAndIuf(Long organizationId, String iuf) {
    return List.of(); // TODO
  }

  @Override
  public PaymentsReportingDTO findBySemanticKey(Long orgId, String iuv, String iur, int transferIndex) {
    return null; // TODO
  }
}
