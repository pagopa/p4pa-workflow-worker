package it.gov.pagopa.pu.worker.paymentsreporting.mapper;

import it.gov.pagopa.payhub.activities.dto.paymentsreporting.PaymentsReportingDTO;
import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;
import org.springframework.stereotype.Service;

@Service
public class PaymentsReportingMapper {

    public PaymentsReportingDTO mapPaymentsReporting2DTO(PaymentsReporting model) {
        return PaymentsReportingDTO.builder()
            .paymentsReportingId(model.getPaymentsReportingId())
            .version(model.getVersion())
            .creationDate(model.getCreationDate())
            .lastUpdateDate(model.getLastUpdateDate())
            .organizationId(model.getOrganizationId())
            .ingestionFlowFileId(model.getIngestionFlowFileId())
            .pspIdentifier(model.getPspIdentifier())
            .flowIdentifierCode(model.getFlowIdentifierCode())
            .flowDateTime(model.getFlowDateTime())
            .regulationUniqueIdentifier(model.getRegulationUniqueIdentifier())
            .regulationDate(model.getRegulationDate())
            .senderPspType(model.getSenderPspType())
            .senderPspCode(model.getSenderPspCode())
            .senderPspName(model.getSenderPspName())
            .receiverOrganizationType(model.getReceiverOrganizationType())
            .receiverOrganizationCode(model.getReceiverOrganizationCode())
            .receiverOrganizationName(model.getReceiverOrganizationName())
            .totalPayments(model.getTotalPayments())
            .totalAmountCents(model.getTotalAmountCents())
            .creditorReferenceId(model.getCreditorReferenceId())
            .regulationId(model.getRegulationId())
            .amountPaidCents(model.getAmountPaidCents())
            .paymentOutcomeCode(model.getPaymentOutcomeCode())
            .payDate(model.getPayDate())
            .acquiringDate(model.getAcquiringDate())
            .transferIndex(model.getTransferIndex())
            .bicCodePouringBank(model.getBicCodePouringBank())
            .build();
    }

    public PaymentsReporting mapPaymentsReportingDTO2Model(PaymentsReportingDTO dto) {
        return PaymentsReporting.builder()
            .paymentsReportingId(dto.getPaymentsReportingId())
            .version(dto.getVersion())
            .creationDate(dto.getCreationDate())
            .lastUpdateDate(dto.getLastUpdateDate())
            .organizationId(dto.getOrganizationId())
            .ingestionFlowFileId(dto.getIngestionFlowFileId())
            .pspIdentifier(dto.getPspIdentifier())
            .flowIdentifierCode(dto.getFlowIdentifierCode())
            .flowDateTime(dto.getFlowDateTime())
            .regulationUniqueIdentifier(dto.getRegulationUniqueIdentifier())
            .regulationDate(dto.getRegulationDate())
            .senderPspType(dto.getSenderPspType())
            .senderPspCode(dto.getSenderPspCode())
            .senderPspName(dto.getSenderPspName())
            .receiverOrganizationType(dto.getReceiverOrganizationType())
            .receiverOrganizationCode(dto.getReceiverOrganizationCode())
            .receiverOrganizationName(dto.getReceiverOrganizationName())
            .totalPayments(dto.getTotalPayments())
            .totalAmountCents(dto.getTotalAmountCents())
            .creditorReferenceId(dto.getCreditorReferenceId())
            .regulationId(dto.getRegulationId())
            .amountPaidCents(dto.getAmountPaidCents())
            .paymentOutcomeCode(dto.getPaymentOutcomeCode())
            .payDate(dto.getPayDate())
            .acquiringDate(dto.getAcquiringDate())
            .transferIndex(dto.getTransferIndex())
            .bicCodePouringBank(dto.getBicCodePouringBank())
            .build();
    }
}
