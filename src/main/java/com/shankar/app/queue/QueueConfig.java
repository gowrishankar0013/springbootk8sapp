package com.shankar.app.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsProperties.DeliveryMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class QueueConfig {

	@Value("${activemq.brokerUrl}")
	private String brokerUrl;

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin","admin",brokerUrl);
		ActiveMQPrefetchPolicy qPrefetchPolicy = new ActiveMQPrefetchPolicy();
		qPrefetchPolicy.setQueuePrefetch(1);
		activeMQConnectionFactory.setPrefetchPolicy(qPrefetchPolicy);
		return activeMQConnectionFactory;
	}
	
	@Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setConcurrency("1");
        return factory;
    }
	
	@Bean
    public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory cf =  new CachingConnectionFactory(activeMQConnectionFactory());
	
        return cf;
    }
	
    @Bean
    public JmsTemplate jmsTemplate() {
    	JmsTemplate template =  new JmsTemplate(cachingConnectionFactory());
    	
    	template.setDeliveryMode(DeliveryMode.PERSISTENT.getValue());
    	template.setExplicitQosEnabled(true);
    	return template;
    }

    @Bean
    public QueueService queue() {
        return new QueueService();
    }
    
    

}
