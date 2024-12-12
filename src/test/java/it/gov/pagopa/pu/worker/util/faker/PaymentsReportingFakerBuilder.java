package it.gov.pagopa.pu.worker.util.faker;

import it.gov.pagopa.payhub.activities.dto.paymentsreporting.PaymentsReportingDTO;
import it.gov.pagopa.pu.worker.paymentsreporting.model.PaymentsReporting;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentsReportingFakerBuilder {
    public static PaymentsReporting buildPaymentsReporting() {
        return PaymentsReporting.builder()
            .paymentsReportingId(1L)
            .version(1)
            .creationDate(Instant.now())
            .lastUpdateDate(Instant.now())
            .organizationId(1001L)
            .ingestionFlowFileId(2002L)
            .pspIdentifier("PSP123")
            .flowIdentifierCode("FLOW001")
            .flowDateTime(LocalDateTime.now())
            .regulationUniqueIdentifier("REG001")
            .regulationDate(LocalDate.now())
            .senderPspType("Bank")
            .senderPspCode("BANK001")
            .senderPspName("Sample Bank")
            .receiverOrganizationType("Government")
            .receiverOrganizationCode("GOV001")
            .receiverOrganizationName("Sample Gov")
            .totalPayments(10L)
            .totalAmountCents(50000L)
            .creditorReferenceId("CR001")
            .regulationId("REG001")
            .amountPaidCents(50000L)
            .paymentOutcomeCode("SUCCESS")
            .payDate(LocalDate.now())
            .acquiringDate(LocalDate.now().minusDays(1))
            .transferIndex(0)
            .bicCodePouringBank("BIC001")
            .build();
    }

    public static PaymentsReportingDTO buildPaymentsReportingDTO() {
        return PaymentsReportingDTO.builder()
            .paymentsReportingId(1L)
            .version(1)
            .creationDate(Instant.now())
            .lastUpdateDate(Instant.now())
            .organizationId(1001L)
            .ingestionFlowFileId(2002L)
            .pspIdentifier("PSP123")
            .flowIdentifierCode("FLOW001")
            .flowDateTime(LocalDateTime.now())
            .regulationUniqueIdentifier("REG001")
            .regulationDate(LocalDate.now())
            .senderPspType("Bank")
            .senderPspCode("BANK001")
            .senderPspName("Sample Bank")
            .receiverOrganizationType("Government")
            .receiverOrganizationCode("GOV001")
            .receiverOrganizationName("Sample Gov")
            .totalPayments(10L)
            .totalAmountCents(50000L)
            .creditorReferenceId("CR001")
            .regulationId("REG001")
            .amountPaidCents(50000L)
            .paymentOutcomeCode("SUCCESS")
            .payDate(LocalDate.now())
            .acquiringDate(LocalDate.now().minusDays(1))
            .transferIndex(0)
            .bicCodePouringBank("BIC001")
            .build();
    }
}
