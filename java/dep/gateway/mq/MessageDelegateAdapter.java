package dep.gateway.mq;

import dep.gateway.mq.IMessageDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
/**
 * MessageListenerAdapter
 *
 * @author zhangxiaobo
 */
@Deprecated
public abstract class MessageDelegateAdapter implements IMessageDelegate {

    @Override
    public abstract void handleMessage(TextMessage message);

    @Override
    public void handleMessage(BytesMessage message) {
    }

    @Override
    public void handleMessage(MapMessage message) {
    }

    @Override
    public void handleMessage(ObjectMessage message) {
    }

    @Override
    public void handleMessage(StreamMessage message) {
    }
}
