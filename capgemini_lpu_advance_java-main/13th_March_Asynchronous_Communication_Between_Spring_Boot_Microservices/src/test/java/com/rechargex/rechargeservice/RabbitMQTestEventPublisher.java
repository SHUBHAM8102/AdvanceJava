/**
 * RabbitMQ Direct Event Test
 *
 * File: RabbitMQTestEventPublisher.java
 * Location: src/test/java/com/rechargex/rechargeservice/
 *
 * Purpose: Directly publish a test RechargeSuccessEvent to RabbitMQ queue
 * for manual testing and event visibility in RabbitMQ UI.
 *
 * Usage:
 * 1. Place this file in src/test/java/com/rechargex/rechargeservice/
 * 2. Run via IDE JUnit or: mvn test -Dtest=RabbitMQTestEventPublisher
 * 3. Check RabbitMQ UI (http://localhost:15672) -> Queues -> recharge.success
 */

package com.rechargex.rechargeservice;

import com.rechargex.rechargeservice.config.RabbitMQConfig;
import com.rechargex.rechargeservice.dto.RechargeSuccessEvent;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class RabbitMQTestEventPublisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void publishTestEventToQueue() {
        RechargeSuccessEvent event = RechargeSuccessEvent.builder()
                .rechargeId(1L)
                .userId(101L)
                .mobileNumber("9876543210")
                .operatorName("Airtel")
                .planName("1.5GB/day 28 days")
                .amount(new BigDecimal("299.00"))
                .validityDays(28)
                .userEmail("testuser@example.com")
                .transactionId("TXN-2026-03-13-001")
                .rechargedAt(LocalDateTime.now())
                .build();

        System.out.println("\n=== Publishing Test Event to RabbitMQ ===");
        System.out.println("Exchange: " + RabbitMQConfig.EXCHANGE_NAME);
        System.out.println("Routing Key: " + RabbitMQConfig.ROUTING_KEY);
        System.out.println("Queue: " + RabbitMQConfig.QUEUE_NAME);
        System.out.println("Event: " + event);
        System.out.println("\nCheck RabbitMQ UI: http://localhost:15672");
        System.out.println("Navigate to: Queues -> recharge.success -> Get Messages");
        System.out.println("====================================\n");

        amqpTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);

        System.out.println("[SUCCESS] Event published to queue!");
    }
}

