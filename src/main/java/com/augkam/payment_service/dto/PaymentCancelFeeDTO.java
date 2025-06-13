package com.augkam.payment_service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentCancelFeeDTO {

    private Long id;
    
    @JsonProperty("cancel_fee")
    private BigDecimal cancelFee;

    public PaymentCancelFeeDTO(Long id, BigDecimal cancelFee) {
        this.id = id;
        this.cancelFee = cancelFee;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

}
