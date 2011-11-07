package dep.gateway.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public abstract class MessageCreatorAdapter implements MessageCreator {

    protected String msgContent;

    public MessageCreatorAdapter(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public abstract Message createMessage(Session session);
}
