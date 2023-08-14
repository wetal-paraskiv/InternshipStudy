//package org.example.activemq;
//
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.TextMessage;
//
///* Defining a MDB destination via CLI
//   http://localhost:9990/console/
//0. in JBoss/WildFly configuration set startup script: C:\wildfly-26.1.1.Final\bin\standalone.bat  -c standalone-full.xml
//   to activate JMS services on WildFly...
//1. start jboss-cli.bat from C:\wildfly-26.1.1.Final\bin
//2. connect
//3. jms-queue add  --queue-address= WetalQueue --entries=queue/WetalQueue,java:/jboss/exported/jms/queue/WetalQueue
//* **/
//
//@MessageDriven(activationConfig = {
//        @ActivationConfigProperty(
//                propertyName = "destination",
//                propertyValue = "WetalQueue"),
//        @ActivationConfigProperty(
//                propertyName = "destinationType",
//                propertyValue = "javax.jms.Queue")
//})
//public class ReadMessageMDB implements MessageListener {
//
//    public void onMessage(Message message) {
//        TextMessage textMessage = (TextMessage) message;
//        try {
//            System.out.println("\n\t--------There is a Message received-------\n\tmessage: " + textMessage.getText());
//
//        } catch (JMSException e) {
//            System.out.println("Error while trying to consume messages: " + e.getMessage());
//        }
//    }
//
//}
