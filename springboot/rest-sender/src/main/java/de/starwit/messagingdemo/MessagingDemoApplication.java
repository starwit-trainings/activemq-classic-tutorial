package de.starwit.messagingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class MessagingDemoApplication {

	private static Logger log = LoggerFactory.getLogger(MessagingDemoApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MessagingDemoApplication.class, args);
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		log.info("sending a message");

		jmsTemplate.convertAndSend("users", new MyUser("TestUser", "hans@meiser.com"));

	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all auto-configured defaults to this factory, including the
		// message converter
		configurer.configure(factory, connectionFactory);
		// You could still override some settings if necessary.
		return factory;
	}

}
