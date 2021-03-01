package com.tairovich.jms_1;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
    public static void main(String[] args) {

        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory)initialContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("I am the creator of this message");
            producer.send(textMessage);
            System.out.println("Message Sent: " + textMessage.getText());

            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();

            TextMessage receiveMSG = (TextMessage)consumer.receive(5000);

            System.out.println("Message Received: " + receiveMSG.getText());

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            if (initialContext != null){
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
