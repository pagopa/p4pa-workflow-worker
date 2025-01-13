package it.gov.pagopa.pu.worker.paymentsreporting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments_reporting")
public class PaymentsReporting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_reporting_generator")
    @SequenceGenerator(name = "payments_reporting_generator", sequenceName = "payments_reporting_seq", allocationSize = 1)
    private String paymentsReportingId;
    private int version;
    private Instant creationDate;
    private Instant lastUpdateDate;
    private Long organizationId;
    private Long ingestionFlowFileId;
    private String pspIdentifier;
    private String flowIdentifierCode;
    private LocalDateTime flowDateTime;
    private String regulationUniqueIdentifier;
    private LocalDate regulationDate;
    private String senderPspType;
    private String senderPspCode;
    private String senderPspName;
    private String receiverOrganizationType;
    private String receiverOrganizationCode;
    private String receiverOrganizationName;
    private long totalPayments;
    private Long totalAmountCents;
    private String creditorReferenceId;
    private String regulationId;
    private Long amountPaidCents;
    private String paymentOutcomeCode;
    private LocalDate payDate;
    private LocalDate acquiringDate;
    private int transferIndex;
    private String bicCodePouringBank;
}
