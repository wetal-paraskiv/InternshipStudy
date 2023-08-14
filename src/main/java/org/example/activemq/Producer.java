package org.example.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer implements Runnable {
    private String messageToSend;
    private String destinationQueue;

    public Producer(String messageToSend, String destinationQueue) {
        this.messageToSend = messageToSend;
        this.destinationQueue = destinationQueue;
    }
    @Override
    public void run() {
        try {
            // Create a connection factory -> create connection -> connection start
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = factory.createConnection();
            connection.start();

            // Create a session which is non-transactional
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create Destination queue
            Destination queue = session.createQueue(destinationQueue);
            // Create a producer
            MessageProducer messageProducer = session.createProducer(queue);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // insert message
            TextMessage textMessage = session.createTextMessage(messageToSend);
            messageProducer.send(textMessage);

            session.close();
            connection.close();

        } catch (Exception ex) {
            System.out.println("JMS Exception occurred.  Shutting down client.");
            System.out.println(ex.getMessage());
        }
    }
}