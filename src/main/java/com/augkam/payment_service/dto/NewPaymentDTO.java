package com.augkam.payment_service.dto;

import java.math.BigDecimal;

import com.augkam.payment_service.model.enums.Currency;
import com.augkam.payment_service.model.enums.PaymentType;
import com.augkam.payment_service.validation.ValidatePayment;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@ValidatePayment
public class NewPaymentDTO {

    @DecimalMin(value = "0", inclusive = false, message = "amount must be positive")
    private BigDecimal amount;

    private Currency currency;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @NotBlank(message = "debtor_iban must be provided")
    @JsonProperty("debtor_iban")
    private String debtorIban;

    @NotBlank(message = "creditor_iban must be provided")
    @JsonProperty("creditor_iban")
    private String creditorIban;

    private String details;

    @JsonProperty("creditor_bank_bic")
    private String creditorBankBic;

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

    public String getCreditorBankBic() {
        return creditorBankBic;
    }

    public void setCreditorBankBic(String creditorBankBic) {
        this.creditorBankBic = creditorBankBic;
    }

}
