//package org.example.activemq;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//
//public class SubscriberOne {
//
//    public static void main(String[] args) throws JMSException {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        Connection connection = factory.createConnection();
//        connection.setClientID("employeeOne");
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Topic destination = session.createTopic("managerAnnouncement");
////        MessageConsumer consumer = session.createConsumer(destination);
//        // This is where we create a Durable Subscriber
//        MessageConsumer consumer = session.createDurableSubscriber(destination, "Listener");
//
//        Message message;
//        while (true) {
//            message = consumer.receive(1000);
//            if (message instanceof TextMessage) {
//                TextMessage textMessage = (TextMessage) message;
//                String text = textMessage.getText();
//                System.out.println("Employee One received: \n\t" + text);
//            }
//        }
//    }
//}
