package dep.gateway.mq;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;

@Deprecated
public interface IMessageDelegate {

    void handleMessage(TextMessage message);

    void handleMessage(BytesMessage message);

    void handleMessage(MapMessage message);

    void handleMessage(ObjectMessage message);

    void handleMessage(StreamMessage message);
}
