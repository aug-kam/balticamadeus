package com.augkam.payment_service.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NewPaymentValidation.class)
@Documented
public @interface ValidatePayment {
    String message() default "Invalid payment";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}