package skyline.common.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import javax.jms.*;

public class MQStatusTester {

    private static Logger logger = LoggerFactory.getLogger(MQStatusTester.class);
    private static MessageConsumer consumer;
    private static Connection connection;
    private static Session session;
    private static String queueName = PropertyManager.getProperty("dep.activemq.test.queue");
    private static final String BROKER_URL = PropertyManager.getProperty("dep.activemq.brokerurl");

    public MQStatusTester() {

    }

    public boolean sendTestMsg() {

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Destination requestQueue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(requestQueue);
            TextMessage textMessage = session.createTextMessage(DateFormatter.getDatetime18() + "检测服务器状态");
            producer.send(textMessage);
            this.close();
            return true;
        } catch (JMSException e) {
            return false;
        }
    }

    // 关闭连接
    private void close() throws JMSException {
        if (consumer != null) {
            consumer.close();
            consumer = null;
        }
        if (session != null) {
            session.close();
            session = null;
        }
        if(connection != null) {
            connection.close();
            connection = null;
        }

    }
}
