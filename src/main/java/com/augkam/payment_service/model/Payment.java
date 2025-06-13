package com.augkam.payment_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.augkam.payment_service.model.enums.Currency;
import com.augkam.payment_service.model.enums.PaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private String debtorIban;

    @Column(nullable = false)
    private String creditorIban;

    private String details;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime canceledAt;

    private BigDecimal cancelFee;

    private String creditorBankBic;

    private boolean notified;

    private Boolean successfullyNotified;

    public Payment() {
    }

    public Payment(BigDecimal amount, Currency currency, PaymentType paymentType, String debtorIban,
            String creditorIban, String details, LocalDateTime createdAt) {
        this.amount = amount;
        this.currency = currency;
        this.paymentType = paymentType;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(BigDecimal cancelFee) {
        this.cancelFee = cancelFee;
    }

    public String getCreditorBankBic() {
        return creditorBankBic;
    }

    public void setCreditorBankBic(String creditorBankBic) {
        this.creditorBankBic = creditorBankBic;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public Boolean getSuccessfullyNotified() {
        return successfullyNotified;
    }

    public void setSuccessfullyNotified(Boolean successfullyNotified) {
        this.successfullyNotified = successfullyNotified;
    }

    
}
