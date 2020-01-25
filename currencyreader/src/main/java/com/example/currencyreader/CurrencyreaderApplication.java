package com.example.currencyreader;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Log4j2
public class CurrencyreaderApplication {

	private final Map<String, Double> currencies = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(CurrencyreaderApplication.class, args);
	}

	@KafkaListener(id = "currency1", topics = "currency")
	public void listen(@Payload(required = false) Double rate,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {

		if (rate == null) {
			this.currencies.remove(key);
		}
		else {
			this.currencies.put(key, rate);
		}
		log.info("Currencies now: " + this.currencies);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("currency")
				.compact()
				.partitions(1)
				.replicas(1)
				.build();
	}


}
