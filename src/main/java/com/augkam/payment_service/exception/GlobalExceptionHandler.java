package com.augkam.payment_service.exception;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlePaymentNotFoundException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Object target = ex.getBindingResult().getTarget();
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> resolveJsonProperty(target, fieldError.getField()),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing));
        return new ResponseEntity<>(getBadRequestBody(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleEnumParseError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormat) {
            Class<?> targetType = invalidFormat.getTargetType();
            if (targetType.isEnum()) {
                String fieldName = invalidFormat.getPath().get(0).getFieldName();
                String invalidValue = invalidFormat.getValue().toString();
                String message = "Invalid value '" + invalidValue + "' for field '" + fieldName +
                        "'. Allowed values are: " + String.join(", ", getEnumValues(targetType));
                return new ResponseEntity<>(getBadRequestBody(message), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(getBadRequestBody("Invalid request payload"), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getBadRequestBody(Object errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);
        return body;
    }

    private String[] getEnumValues(Class<?> enumType) {
        Object[] enumConstants = enumType.getEnumConstants();
        String[] names = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            names[i] = enumConstants[i].toString();
        }
        return names;
    }

    private String resolveJsonProperty(Object target, String fieldName) {
        if (target == null)
            return fieldName;
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null && !jsonProperty.value().isBlank()) {
                return jsonProperty.value();
            }
        } catch (NoSuchFieldException ignored) {
        }
        return fieldName;
    }
}
