//package org.example.activemq;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//
//public class SubscriberTwo {
//
//    public static void main(String[] args) throws JMSException {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        Connection connection = factory.createConnection();
//        connection.setClientID("employeeTwo");
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Topic destination = session.createTopic("managerAnnouncement");
//        MessageConsumer consumer = session.createDurableSubscriber(destination, "Listener");
//
//        Message message;
//        while (true) {
//            message = consumer.receive(1000);
//            if (message instanceof TextMessage) {
//                TextMessage textMessage = (TextMessage) message;
//                String text = textMessage.getText();
//                System.out.println("Employee Two received: \n\t" + text);
//            }
//        }
//    }
//}
