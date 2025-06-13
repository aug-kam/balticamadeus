package com.augkam.payment_service.controller;

import java.math.BigDecimal;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.augkam.payment_service.service.IPaymentService;

import jakarta.validation.Valid;

import com.augkam.payment_service.dto.NewPaymentDTO;
import com.augkam.payment_service.dto.PaymentCancelFeeDTO;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@Valid @RequestBody NewPaymentDTO paymentDTO) {
        paymentService.create(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully");
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.status(HttpStatus.OK).body("Payment canceled successfully");
    }

    @GetMapping("/active")
    public ResponseEntity<List<Long>> getActivePayments(
            @RequestParam(required = false) BigDecimal minAmount) {
        return ResponseEntity.ok(paymentService.getActivePaymentIds(minAmount));
    }

    @GetMapping("/{id}")
    public EntityModel<PaymentCancelFeeDTO> getById(@PathVariable Long id) {
        PaymentCancelFeeDTO payment = paymentService.getCancelFeeById(id);
        return EntityModel.of(payment,
                linkTo(methodOn(PaymentController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(PaymentController.class).getActivePayments(null)).withRel("active"),
                linkTo(methodOn(PaymentController.class).cancelPayment(id)).withRel("cancel"));

    }
}
