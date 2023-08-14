//package org.example.activemq;
//
//import javax.jms.Connection;
//import javax.jms.Destination;
//import javax.jms.Message;
//import javax.jms.MessageConsumer;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//public class Consumer implements Runnable {
//    String destinationQueue;
//
//    public Consumer(String destinationQueue) {
//        this.destinationQueue = destinationQueue;
//    }
//
//    @Override
//    public void run() {
//        try {
//            // Create a connection factory -> create connection -> connection start
//            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//            Connection connection = factory.createConnection();
//            connection.start();
//
//            // Create Session
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            //Create queue
//            Destination destination = session.createQueue(destinationQueue);
//
//            MessageConsumer messageConsumer = session.createConsumer(destination);
//
//            Message message = messageConsumer.receive(1000);
//
//            if (message instanceof TextMessage) {
//                TextMessage textMessage = (TextMessage) message;
//                String text = textMessage.getText();
//                System.out.println("Consumer Received:\n\t" + text);
//            }
//            session.close();
//            connection.close();
//
//        } catch (Exception ex) {
//            System.out.println("JMS Exception occurred.  Shutting down client.");
//        }
//    }
//}