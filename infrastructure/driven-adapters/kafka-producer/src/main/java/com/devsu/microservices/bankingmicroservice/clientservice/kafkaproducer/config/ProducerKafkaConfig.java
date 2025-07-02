package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.config;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data.VerificationResponse;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class ProducerKafkaConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, VerificationResponse> kafkaProducerTemplate(
            KafkaProperties kafkaProperties
    ) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        SenderOptions<String, VerificationResponse> senderOptions =
                SenderOptions.<String, VerificationResponse>create(props);


        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }

}
