package com.augkam.payment_service.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.augkam.payment_service.dto.NewPaymentDTO;
import com.augkam.payment_service.dto.PaymentCancelFeeDTO;
import com.augkam.payment_service.exception.PaymentNotFoundException;
import com.augkam.payment_service.model.Payment;
import com.augkam.payment_service.model.enums.PaymentType;
import com.augkam.payment_service.repository.PaymentRepository;
import com.augkam.payment_service.service.IPaymentNotifierService;
import com.augkam.payment_service.service.IPaymentService;

import jakarta.validation.ValidationException;

@Service
public class PaymentService implements IPaymentService {

    private final PaymentRepository repository;
    private final IPaymentNotifierService paymentNotifierService;

    public PaymentService(PaymentRepository repository, IPaymentNotifierService paymentNotifierService) {
        this.repository = repository;
        this.paymentNotifierService = paymentNotifierService;
    }

    @Override
    public Payment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Override
    public void create(NewPaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setCurrency(paymentDTO.getCurrency());
        payment.setDebtorIban(paymentDTO.getDebtorIban());
        payment.setCreditorIban(paymentDTO.getCreditorIban());
        if (PaymentType.TYPE1.equals(paymentDTO.getPaymentType())
                || PaymentType.TYPE2.equals(paymentDTO.getPaymentType())) {
            payment.setDetails(paymentDTO.getDetails());
            payment.setSuccessfullyNotified(paymentNotifierService.notifyAndGetStatus(payment.getPaymentType()));
            payment.setNotified(true);
        } else {
            payment.setCreditorBankBic(paymentDTO.getCreditorBankBic());
        }
        payment.setCreatedAt(LocalDateTime.now());
        repository.save(payment);
    }

    @Override
    public void cancelPayment(Long id) {
        Payment payment = findById(id);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = payment.getCreatedAt();
        if (payment.getCancelFee() != null) {
            throw new ValidationException("Payment is already canceled");

        }
        if (!now.toLocalDate().isEqual(createdAt.toLocalDate())) {
            throw new ValidationException("Payment can only be canceled on the same day");
        }
        payment.setCancelFee(payment.getPaymentType().getCancelFeeCoefficient()
                .multiply(BigDecimal.valueOf(Duration.between(createdAt, now).toHours())));
        payment.setCanceledAt(now);
        repository.save(payment);
    }

    @Override
    public List<Long> getActivePaymentIds(BigDecimal minAmount) {
        return minAmount != null ? repository.findActivePaymentIdsWithMinAmount(minAmount)
                : repository.findAllActivePaymentIds();
    }

    @Override
    public PaymentCancelFeeDTO getCancelFeeById(Long id) {
        PaymentCancelFeeDTO paymentCancelFeeDTO = repository.getCancelFeeById(id);
        if (paymentCancelFeeDTO == null) {
            throw new PaymentNotFoundException(id);
        }
        return repository.getCancelFeeById(id);
    }

}
