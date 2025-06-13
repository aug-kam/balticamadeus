package com.augkam.payment_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.augkam.payment_service.model.enums.PaymentType;
import com.augkam.payment_service.service.IPaymentNotifierService;

@Service
public class PaymentNotifierService implements IPaymentNotifierService {

    private final RestTemplate restTemplate;

    @Value("${external.notification.url.type1}")
    private String type1Url;

    @Value("${external.notification.url.type2}")
    private String type2Url;

    @Autowired
    public PaymentNotifierService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public Boolean notifyAndGetStatus(PaymentType paymentType) {
        Boolean success = false;
        String url = switch (paymentType) {
            case PaymentType.TYPE1 -> type1Url;
            case PaymentType.TYPE2 -> type2Url;
            default -> null;
        };

        if (url == null){
            return success;
        }

          try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            success = response.getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
        return success;
    }
}
