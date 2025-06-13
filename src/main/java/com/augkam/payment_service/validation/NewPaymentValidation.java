package com.augkam.payment_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

import com.augkam.payment_service.dto.NewPaymentDTO;
import com.augkam.payment_service.model.enums.Currency;
import com.augkam.payment_service.model.enums.PaymentType;

public class NewPaymentValidation implements ConstraintValidator<ValidatePayment, NewPaymentDTO> {

    private static final Set<Currency> ALLOWED_CURRENCIES = Set.of(Currency.EUR, Currency.USD);
    private static final Set<PaymentType> ALLOWED_PAYMENT_TYPES = Set.of(PaymentType.TYPE1, PaymentType.TYPE2,
            PaymentType.TYPE3);

    @Override
    public boolean isValid(NewPaymentDTO dto, ConstraintValidatorContext context) {

        if (dto.getCurrency() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("currency must be provided")
                    .addPropertyNode("currency")
                    .addConstraintViolation();
            return false;
        }
        if (dto.getPaymentType() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("payment_type must be provided")
                    .addPropertyNode("payment_type")
                    .addConstraintViolation();
            return false;
        }

        if (!ALLOWED_CURRENCIES.contains(dto.getCurrency())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("currency must be one of: EUR, USD")
                    .addPropertyNode("currency")
                    .addConstraintViolation();
            return false;
        }

        if (!ALLOWED_PAYMENT_TYPES.contains(dto.getPaymentType())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("payment_type must be one of: TYPE1, TYPE2, TYPE3")
                    .addPropertyNode("payment_type")
                    .addConstraintViolation();
            return false;
        }

        if (PaymentType.TYPE1.equals(dto.getPaymentType())
                && (dto.getDetails() == null || dto.getDetails().isBlank())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("details must be provided")
                    .addPropertyNode("details")
                    .addConstraintViolation();
            return false;
        }

        if (PaymentType.TYPE3.equals(dto.getPaymentType())
                && (dto.getCreditorBankBic() == null || dto.getCreditorBankBic().isBlank())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("creditor_bank_bic must be provided")
                    .addPropertyNode("creditor_bank_bic")
                    .addConstraintViolation();
            return false;
        }
        boolean valid = switch (dto.getPaymentType()) {
            case TYPE1 -> dto.getCurrency() == Currency.EUR;
            case TYPE2 -> dto.getCurrency() == Currency.USD;
            case TYPE3 -> true;
        };

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "invalid combination: " + dto.getPaymentType() + " with " + dto.getCurrency())
                    .addPropertyNode("currency")
                    .addConstraintViolation();
        }
        return valid;
    }

}
