package dep.gateway.mq.impl;

import dep.gateway.crypt.DesCrypter;
import dep.gateway.mq.MessageCreatorAdapter;
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
 * Time: 下午7:28
 * 将数据解密，生成消息，消息关联,发回FMQD
 */
public class DeCryptMessageCreator extends MessageCreatorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DeCryptMessageCreator.class);
    private String corelationMessageId;
    private Message message;

    public DeCryptMessageCreator(String msgContent, String corelationMessageId) {
        super(msgContent);
        this.corelationMessageId = corelationMessageId;
    }

    @Override
    public Message createMessage(Session session) {

        String miContent = null;

        try {
            miContent = DesCrypter.getInstance().decrypt(msgContent);
        } catch (Exception e) {
            logger.error("DesCrypter解密异常！", e.getMessage());
            throw new RuntimeException("DesCrypter解密异常！" + e.getMessage());
        }
        TextMessage textMessage = null;
        try {
            textMessage = session.createTextMessage(miContent);
            textMessage.setJMSCorrelationID(corelationMessageId);
            message = textMessage;
        } catch (JMSException e) {
            logger.error("明文文本消息生成异常！", e.getMessage());
        }
        return textMessage;
    }

    public String getMessageID() throws JMSException {
        return message == null ? null : message.getJMSMessageID();
    }
}
