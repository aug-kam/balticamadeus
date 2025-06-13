package com.augkam.payment_service.service.geo.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.augkam.payment_service.service.geo.IGeoLocationService;

@Service
public class GeoLocationService implements IGeoLocationService {

    private static final Logger logger = LoggerFactory.getLogger(GeoLocationService.class);

    private final WebClient webClient = WebClient.builder().baseUrl("https://ipapi.co").build();

    public void logCountryFromIp(String ip) {
        webClient.get()
                .uri("/{ip}/country_name/", ip)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(country -> logger.info("Client country: {} (ip: {})", country, ip))
                .doOnError(ex -> logger.warn("Failed to resolve country from IP {}: {}", ip, ex.getMessage()))
                .subscribe();
    }
}
