package com.augkam.payment_service.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.augkam.payment_service.model.Payment;
import com.augkam.payment_service.model.enums.Currency;
import com.augkam.payment_service.model.enums.PaymentType;
import com.augkam.payment_service.repository.PaymentRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(PaymentRepository repository) {

    return args -> {
      log.info("Preloading test_data" + repository.save(new Payment(BigDecimal.valueOf(45.5), Currency.EUR, PaymentType.TYPE1,
          "debtorIban_1", "creditorIban_1", "details for TYPE1", LocalDateTime.now().minusHours(2))));
      log.info("Preloading test_data" + repository.save(new Payment(BigDecimal.valueOf(456.564), Currency.USD, PaymentType.TYPE2,
          "debtorIban_1", "creditorIban_2", "details for TYPE2", LocalDateTime.now().minusDays(2))));
    };
  }
}