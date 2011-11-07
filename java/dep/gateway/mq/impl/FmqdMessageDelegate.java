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
 * Time: ����4:23
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

        // TODO ��ȡ�����ļ����жϽӿڷ�ʽ��Socket��mq��
        // TODO Socket��ʽ��������������Socket��ʽת����MBP��ͬ������

        try {
            String msgId = message.getJMSMessageID();
            String msgContent = message.getText();

            System.out.println("FMQD's MessageID: " + msgId + ", FMQD's MessageContent:" + msgContent);

            logger.info("FMQD's MessageID: " + msgId + ", FMQD's MessageContent:" + msgContent);

            // ���͵����ж˲�ͬ���ȴ���������
            //TextMessage fmqdMessage = (TextMessage)fmqdRealtimeMQService.sendAndRecv(msgContent);
            //String fmqdMiDatagram = fmqdMessage.getText();

            // �����ķ��ͻ�fmqd
          //  fmqdRealtimeMQService.sendBack(fmqdMiDatagram, msgId);

        } catch (JMSException e) {
            logger.error("��Ϣ����POJO�쳣", e.getMessage());
        }
    }
}
