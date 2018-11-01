package ru.cinimex.mdb;

import org.jboss.annotation.ejb.ResourceAdapter;
import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(name = "UcbOepBackMDB", activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "${wmq.destination}"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "${wmq.destination}"),
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
        @ActivationConfigProperty(propertyName = "channel", propertyValue = "${wmq.channel}"),
        @ActivationConfigProperty(propertyName = "hostName", propertyValue = "${wmq.hostName}"),
        @ActivationConfigProperty(propertyName = "queueManager", propertyValue = "${wmq.queueManager}"),
        @ActivationConfigProperty(propertyName = "port", propertyValue = "${wmq.port}"),
        @ActivationConfigProperty(propertyName = "username", propertyValue = "${wmq.username}"),
        @ActivationConfigProperty(propertyName = "password", propertyValue = "${wmq.password}"),
        @ActivationConfigProperty(propertyName = "transportType", propertyValue = "CLIENT"),
//        @ActivationConfigProperty(propertyName = "rebalanceConnections", propertyValue = "true"),
//        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "com.ibm.mq.connector.outbound.ManagedQueueConnectionFactoryImpl")
})
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@ResourceAdapter(value="wmq.jmsra.rar")


//@MessageDriven(name = "UcbOepBackMDB", activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jboss/MdbQ"),
//        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
//        @ActivationConfigProperty(propertyName = "channel", propertyValue = "SYSTEM.ADMIN.SVRCONN"),
//        @ActivationConfigProperty(propertyName = "hostName", propertyValue = "ucb-02.vm.cmx.ru"),
//        @ActivationConfigProperty(propertyName = "queueManager", propertyValue = "NewQM"),
//        @ActivationConfigProperty(propertyName = "port", propertyValue = "1414"),
//        @ActivationConfigProperty(propertyName = "username", propertyValue = "mqm"),
//        @ActivationConfigProperty(propertyName = "password", propertyValue = "mqm"),
//        @ActivationConfigProperty(propertyName = "transportType", propertyValue = "CLIENT"),
//        @ActivationConfigProperty(propertyName = "rebalanceConnections", propertyValue = "true")
////        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "com.ibm.mq.connector.outbound.ManagedQueueConnectionFactoryImpl")
//})
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//@ResourceAdapter(value="wmq.jmsra.rar")
public class UcbOepBackMDB implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(UcbOepBackMDB.class.getName());

    public void onMessage(Message rcvMessage) {
        TextMessage msg;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOGGER.info("Received Message from queue: " + msg.getText());
            } else {
                LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }


}
