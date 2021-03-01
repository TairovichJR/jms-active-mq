package com.tairovich.jms;

import javax.jms.*;
import javax.naming.InitialContext;

public class FirstTopic {
    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic)initialContext.lookup("topic/myTopic");
        ConnectionFactory connectionFactory = (ConnectionFactory)initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession();

        MessageProducer producer = session.createProducer(topic);

        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);
        MessageConsumer consumer3 = session.createConsumer(topic);

        TextMessage textMessage = session.createTextMessage("I am the creator of this message");
        producer.send(textMessage);
        System.out.println("Producer sent message: " + textMessage.getText());
        System.out.println("------------------------------------------------");
        connection.start();

        //consumers receiving the same message
        TextMessage receiveMsg1 = (TextMessage) consumer1.receive();
        System.out.println("Consumer 1 received message: " + receiveMsg1.getText() );

        TextMessage receiveMsg2 = (TextMessage) consumer2.receive();
        System.out.println("Consumer 2 received message: " + receiveMsg2.getText() );

        TextMessage receiveMsg3 = (TextMessage) consumer3.receive();
        System.out.println("Consumer 3 received message: " + receiveMsg3.getText() );


        connection.close();
        initialContext.close();
    }
}
