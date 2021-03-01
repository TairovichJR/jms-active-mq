package com.tairovich.jms_2;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class JMSContextDemo {
    public static void main(String[] args) throws Exception {


        InitialContext context = new InitialContext();
        Queue queue = (Queue)context.lookup("queue/myQueue");

        try(ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = factory.createContext()){

            jmsContext.createProducer().send(queue,"Arise  Awake not till the goal is not reached");

            String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
            System.out.println("Message Received: " + messageReceived);
        }


    }
}
