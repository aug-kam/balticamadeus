package com.augkam.payment_service.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.augkam.payment_service.dto.PaymentCancelFeeDTO;
import com.augkam.payment_service.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p.id FROM Payment p WHERE p.cancelFee IS NULL AND p.amount >= :minAmount")
    List<Long> findActivePaymentIdsWithMinAmount(@Param("minAmount") BigDecimal minAmount);

    @Query("SELECT p.id FROM Payment p WHERE p.cancelFee IS NULL")
    List<Long> findAllActivePaymentIds();

    @Query("SELECT new com.augkam.payment_service.dto.PaymentCancelFeeDTO(p.id, p.cancelFee) FROM Payment p WHERE p.id = :id")
    PaymentCancelFeeDTO getCancelFeeById(@Param("id") Long id);

}