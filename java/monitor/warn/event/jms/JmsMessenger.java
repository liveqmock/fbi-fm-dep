package monitor.warn.event.jms;

import monitor.common.WarnType;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import javax.jms.*;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA. User: zhangxiaobo Date: 11-10-2 Time: 下午1:45 To
 * change this template use File | Settings | File Templates.
 */
public class JmsMessenger {

    private static Logger logger = LoggerFactory.getLogger(JmsMessenger.class);

    private static Connection connection = null;

    private static final String BROKER_URL = PropertyManager
            .getProperty("dep.activemq.brokerurl");
    private static final String mailQueueName = PropertyManager
            .getProperty("dep.queue.warn.mail");
    private static final String smsQueueName = PropertyManager
            .getProperty("dep.queue.warn.sms");

    private static final String SYS_ADDRESS = "<br><br>监控系统地址：" + PropertyManager.getProperty("dep.monitor.sys.address");
    private static final String SYS_LOGIN_INFO = "<br><a href='http://" + SYS_ADDRESS + "'>点击此处登录系统</a>";

    private static JmsMessenger messenger = null;

    private JmsMessenger() throws NamingException, JMSException {
        ConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(
                BROKER_URL);
        connection = queueConnectionFactory.createConnection();
        connection.start();

    }

    public static JmsMessenger getInstance() throws NamingException,
            JMSException {
        if (messenger == null) {
            messenger = new JmsMessenger();
        }
        return messenger;
    }

    public void sendMsg(WarnType warnType, String strMsg) throws JMSException {

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = null;
        if (WarnType.EMAIL.getCode().equals(warnType.getCode())) {
            queue = session.createQueue(mailQueueName);
        }else if (WarnType.SMS.getCode().equals(warnType.getCode())) {
            queue = session.createQueue(smsQueueName);
        }
        MessageProducer sender = session.createProducer(queue);
        sender.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage txtMessage = session.createTextMessage(strMsg + SYS_ADDRESS + SYS_LOGIN_INFO);

        logger.info("=============================================================");
        logger.info("发送预警信息到队列 ： " + strMsg + SYS_ADDRESS + SYS_LOGIN_INFO);
        logger.info("=============================================================");
        sender.send(txtMessage);
        session.close();
    }
}
