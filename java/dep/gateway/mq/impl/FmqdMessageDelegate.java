package dep.gateway.mq.impl;

import dep.gateway.mq.MessageDelegateAdapter;
import dep.gateway.mq.RealtimeMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
@Component
@Deprecated
public class FmqdMessageDelegate extends MessageDelegateAdapter {

    private static Logger logger = LoggerFactory.getLogger(FmqdMessageDelegate.class);

    @Autowired
    private RealtimeMQService realtimeMQService;

    @Override
    public void handleMessage(TextMessage message) {

        // TODO 读取配置文件，判断接口方式（Socket、mq）
        // TODO Socket方式：将明文数据以Socket方式转发到MBP，同步接收

        try {
            String msgId = message.getJMSMessageID();
            String msgContent = message.getText();

            System.out.println("FMQD's MessageID: " + msgId + ", FMQD's MessageContent:" + msgContent);

            logger.info("FMQD's MessageID: " + msgId + ", FMQD's MessageContent:" + msgContent);

            // 发送到银行端并同步等待接收数据
            //TextMessage fmqdMessage = (TextMessage)fmqdRealtimeMQService.sendAndRecv(msgContent);
            //String fmqdMiDatagram = fmqdMessage.getText();

            // 将报文发送回fmqd
          //  fmqdRealtimeMQService.sendBack(fmqdMiDatagram, msgId);

        } catch (JMSException e) {
            logger.error("消息监听POJO异常", e.getMessage());
        }
    }
}
