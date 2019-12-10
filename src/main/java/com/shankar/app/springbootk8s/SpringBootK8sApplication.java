package com.shankar.app.springbootk8s;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import com.shankar.app.queue.QueueService;

@SpringBootApplication(scanBasePackages = {"com.shankar.app"})
@EnableJms
public class SpringBootK8sApplication implements JmsListenerConfigurer {
	
	@Value("${queue.name}")
    private String queueName;

    @Value("${worker.name}")
    private String workerName;

    @Value("${worker.enabled}")
    private boolean workerEnabled;

    @Autowired
    private QueueService queueService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootK8sApplication.class, args);
	}
	
	@Override
	 public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
		    if (workerEnabled) {
	            SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
	            endpoint.setId(workerName);
	            endpoint.setDestination(queueName);
	            endpoint.setMessageListener(queueService);
	            registrar.registerEndpoint(endpoint);
	        }
	    }

}
