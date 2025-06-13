package com.augkam.payment_service.service;

import com.augkam.payment_service.model.enums.PaymentType;

public interface IPaymentNotifierService {

    Boolean notifyAndGetStatus(PaymentType paymentType);

}
