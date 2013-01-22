package dep.gateway.mq;

import dep.gateway.mq.impl.CommonMessageCreator;
import dep.gateway.mq.impl.DeCryptMessageCreator;
import dep.gateway.mq.impl.EnCryptMessageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import dep.common.PropertyManager;
import dep.common.StringHelper;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: ÏÂÎç6:00
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RealtimeMQService {

    private static Logger logger = LoggerFactory.getLogger(RealtimeMQService.class);
    private static final long RECV_TIMEOUT = PropertyManager.getLongProperty("dep.queue.timeout");

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    public String sendAndRecvForFmqd(String strMsg) throws JMSException {

        String bankCode = StringHelper.getSubstrBetweenStrs(strMsg, "<BankCode>", "</BankCode>");
        String queueName = PropertyManager.getProperty("queue.fm.to." + bankCode);

        EnCryptMessageCreator cryptMessageCreator = new EnCryptMessageCreator(strMsg, null, true);
        jmsTemplate.send(queueName, cryptMessageCreator);

        String createdMessageId = cryptMessageCreator.getCreatedMessageId();
        jmsTemplate.setReceiveTimeout(RECV_TIMEOUT);

//        queueName = PropertyManager.getProperty("queue.fm.from." + bankCode);
        TextMessage message = (TextMessage)jmsTemplate.receiveSelected(queueName, "JMSCorrelationID = '" + createdMessageId
                + "' and messageType = 'response'");
        String rtnMsg = (message == null)? null : message.getText();

        return rtnMsg;
    }

    public void decryptAndSendToFmqd(String strMsg, String corelationId) {

        DeCryptMessageCreator cryptMessageCreator = new DeCryptMessageCreator(strMsg, corelationId);
        jmsTemplate.send(cryptMessageCreator);
    }

    public void directSendToFmqd(String strMsg, String corelationId) {

        CommonMessageCreator messageCreator = new CommonMessageCreator(strMsg, corelationId);
        jmsTemplate.send(messageCreator);
    }

    public void sendToBank(String bankCode, String strMsg, String corelationId) {

        String queueName = PropertyManager.getProperty("queue.fm.from." + bankCode).trim();
        EnCryptMessageCreator cryptMessageCreator = new EnCryptMessageCreator(strMsg, corelationId, false);
        jmsTemplate.send(queueName, cryptMessageCreator);
    }

}
