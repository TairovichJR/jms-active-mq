package com.tairovich.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class QueueBrowserDemo {
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

            TextMessage textMessage1 = session.createTextMessage("This is message " + (int)(Math.random()*9000)+1000);
            TextMessage textMessage2 = session.createTextMessage("This is message " + (int)(Math.random()*9000)+1000);
            TextMessage textMessage3 = session.createTextMessage("This is message " + (int)(Math.random()*9000)+1000);
            producer.send(textMessage1);
            producer.send(textMessage2);
            producer.send(textMessage3);

            QueueBrowser queueBrowser = session.createBrowser(queue);

            Enumeration messagesEnum = queueBrowser.getEnumeration();

            while (messagesEnum.hasMoreElements()){
                TextMessage m  = (TextMessage)messagesEnum.nextElement();
                System.out.println("Browsing: " + m.getText());
            }

            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            System.out.println("-----------------------------------------------");

            TextMessage receiveMSG = (TextMessage)consumer.receive(5000);
            System.out.println("Message 1 Received: " + receiveMSG.getText());

            receiveMSG = (TextMessage)consumer.receive(5000);
            System.out.println("Message 2 Received: " + receiveMSG.getText());

            receiveMSG = (TextMessage)consumer.receive(5000);
            System.out.println("Message 3 Received: " + receiveMSG.getText());


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
