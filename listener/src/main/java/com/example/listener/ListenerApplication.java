package com.example.listener;

import com.example.listener.ChangeEvent.Type;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@SpringBootApplication
public class ListenerApplication {

	private static final Logger logger = LoggerFactory.getLogger(ListenerApplication.class);

	@Autowired
	private KafkaTemplate<String, Double> template;

	public static void main(String[] args) {
		SpringApplication.run(ListenerApplication.class, args);
	}

	@KafkaListener(id="cdc", topics="maxwell")
	public void listen(ChangeEvent changeEvent){
		logger.info("Received: " + changeEvent);

		if (changeEvent.getTable().equals("currency")) {
			Type type = changeEvent.getType();
			if (type.equals(Type.insert) || type.equals(Type.update)) {
				String symbol = changeEvent.getData().getSymbol();
				double rate = (changeEvent.getData().getRate()) / 1000.;
				this.template.send("currency", symbol, rate);
			}
			if(type.equals(Type.delete)){
				String symbol = changeEvent.getData().getSymbol();
				this.template.send("currency", symbol, null);
			}

		}
	}

	@Bean
	public NewTopic topic(){
		return TopicBuilder.name("currency").build();
	}

	@Bean
	public NewTopic maxwell(){
		return TopicBuilder.name("maxwell").compact().build();
	}

	@Bean
	public RecordMessageConverter converter(){
		return new StringJsonMessageConverter();
	}

}
