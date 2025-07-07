package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.config;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.data.response.AccountVerificationResponse;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfig {

    @Bean
    public ReceiverOptions<String, AccountVerificationResponse> kafkaReceiverOptions(
            @Value(value = "client-verification-request") String topic,
            KafkaProperties  kafkaProperties
            ) throws UnknownHostException {
        kafkaProperties.setClientId(InetAddress.getLocalHost().getHostName());
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();

        return ReceiverOptions
                .<String, AccountVerificationResponse>create(props)
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(new JsonDeserializer<>(AccountVerificationResponse.class, false))
                .subscription(Collections.singleton(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, AccountVerificationResponse> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, AccountVerificationResponse> kafkaReceiverOptions
    ){
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }

}
