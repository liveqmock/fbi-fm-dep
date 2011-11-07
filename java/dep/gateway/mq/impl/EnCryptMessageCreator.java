package dep.gateway.mq.impl;

import dep.gateway.crypt.DesCrypter;
import dep.gateway.mq.MessageCreatorAdapter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: ����7:28
 * �����ݼ��ܣ�������Ϣ����Ϣ����,ת��������
 */
public class EnCryptMessageCreator extends MessageCreatorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EnCryptMessageCreator.class);
    private Message message;
    private String correlationID;
    private boolean isRequest = true;

    public EnCryptMessageCreator(String msgContent) {
        super(msgContent);
    }

    public EnCryptMessageCreator(String msgContent, String correlationID, boolean isRequest) {
        super(msgContent);
        this.correlationID = correlationID;
        this.isRequest = isRequest;
    }

    @Override
    public Message createMessage(Session session) {
        String miContent = null;

        try {
            miContent = DesCrypter.getInstance().encrypt(msgContent);
        } catch (Exception e) {
            logger.error("DesCrypter�����쳣��", e.getMessage());
            throw new RuntimeException("DesCrypter�����쳣��" + e.getMessage());
        }
        TextMessage textMessage = null;
        try {
            textMessage = session.createTextMessage(miContent);
            if (isRequest) {
                textMessage.setStringProperty("messageType", "request");
            } else {
                textMessage.setStringProperty("messageType", "response");
            }
            this.message = textMessage;
            if (!StringUtils.isEmpty(correlationID)) {
                textMessage.setJMSCorrelationID(correlationID);
            }
        } catch (JMSException e) {
            logger.error("�����ı���Ϣ�����쳣��", e.getMessage());
        }
        return textMessage;
    }

    public String getCreatedMessageId() throws JMSException {

        return message == null ? null : message.getJMSMessageID();
    }
}
