package com.augkam.payment_service.exception;

public class PaymentNotFoundException extends RuntimeException {

  public PaymentNotFoundException(Long id) {
    super("Could not find payment " + id);

  }
}