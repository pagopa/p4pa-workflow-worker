package it.gov.pagopa.pu.worker.transfer;

import it.gov.pagopa.payhub.activities.dao.TransferDao;
import it.gov.pagopa.payhub.activities.dto.classifications.TransferSemanticKeyDTO;
import it.gov.pagopa.pu.debtposition.dto.generated.TransferDTO;
import org.springframework.stereotype.Service;

//TODO: class to be removed once the CRUD APIs have been implemented on the activities repository
@Service
public class TransferDaoImpl implements TransferDao {
  @Override
  public TransferDTO findBySemanticKey(TransferSemanticKeyDTO transferSemanticKey) {
    return null;
  }

  @Override
  public boolean notifyReportedTransferId(Long transferId) {
    return false;
  }
}
