package com.augkam.payment_service.service;

import java.math.BigDecimal;
import java.util.List;

import com.augkam.payment_service.dto.NewPaymentDTO;
import com.augkam.payment_service.dto.PaymentCancelFeeDTO;
import com.augkam.payment_service.model.Payment;

public interface IPaymentService {

    Payment findById(Long id);

    void create(NewPaymentDTO paymentDTO);

    void cancelPayment(Long id);

    List<Long> getActivePaymentIds(BigDecimal minAmount);

    PaymentCancelFeeDTO getCancelFeeById(Long id);
}
