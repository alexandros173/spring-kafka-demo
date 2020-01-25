package com.example.currencyreader;

import java.util.Collection;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

@Component
public class FactoryConfigurer {

	FactoryConfigurer(ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
		factory.getContainerProperties().setConsumerRebalanceListener(new ConsumerAwareRebalanceListener() {
			@Override
			public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
				consumer.seekToBeginning(partitions);
			}
		});
	}
}
