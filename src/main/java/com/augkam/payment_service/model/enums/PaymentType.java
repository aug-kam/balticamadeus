package com.augkam.payment_service.model.enums;

import java.math.BigDecimal;

public enum PaymentType {
    TYPE1(BigDecimal.valueOf(0.05)),
    TYPE2(BigDecimal.valueOf(0.10)),
    TYPE3(BigDecimal.valueOf(0.15));

    private final BigDecimal cancelFeeCoefficient;

    PaymentType(BigDecimal cancelFeeCoefficient) {
        this.cancelFeeCoefficient = cancelFeeCoefficient;
    }

    public BigDecimal getCancelFeeCoefficient() {
        return cancelFeeCoefficient;
    }
}
