package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.config;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.data.response.ClientVerificationResponse;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfig {

    @Bean
    public ReceiverOptions<String, String> kafkaReceiverOptions(
            @Value(value = "client-verification-request") String topic,
            KafkaProperties  kafkaProperties,
            SslBundles sslBundles
            ) throws UnknownHostException {
        kafkaProperties.setClientId(InetAddress.getLocalHost().getHostName());

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(
                kafkaProperties.buildConsumerProperties(sslBundles)
        );

        return receiverOptions.subscription(Collections.singleton(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, String> kafkaReceiverOptions
    ){
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }

}
