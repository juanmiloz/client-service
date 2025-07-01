package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

@Configuration
public class KafkaConfig {

    @Bean
    public ReceiverOptions<String, String> kafkaReceiverOptions(
            @Value(value = "client-verification-request") String topic,
            KafkaProperties  kafkaProperties,
            SslBundles sslBundles
            ) throws UnknownHostException {
        kafkaProperties.setClientId(InetAddress.getLocalHost().getHostName());
        ReceiverOptions<String, String> basicReceiverOptions = ReceiverOptions.create(
                kafkaProperties.buildConsumerProperties()
        );
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, String> kafkaReceiverOptions
    ){
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }

}
