package dep.gateway.mq.impl;

import dep.gateway.domain.CommonRes;
import dep.gateway.mq.RealtimeMQService;
import dep.gateway.socket.SimpleSocket;
import dep.common.PropertyManager;
import dep.common.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class FmqdMessageListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(FmqdMessageListener.class);

    @Autowired
    private RealtimeMQService realtimeMQService;

    private static List<String> mqBanks = Arrays.asList(PropertyManager.getProperty("fm.mq.bankcode").split(","));
    private static List<String> socketBanks = Arrays.asList(PropertyManager.getProperty("fm.socket.bankcode").split(","));
    private static String mbpIP = PropertyManager.getProperty("socket.mbp.ip");
    private static int mbpPORT = PropertyManager.getIntProperty("socket.mbp.port");
    private static int mbpTIMEOUT = PropertyManager.getIntProperty("socket.mbp.timeout");

    @Override
    public void onMessage(Message message) {

        String strMsg = null;
        String msgId = null;
        try {
            msgId = message.getJMSMessageID();
            TextMessage textMessage = (TextMessage) message;
            String msgContent = textMessage.getText();

            logger.info("=== DEP接收来自FMQD的报文 ====\r\n" + msgContent);
            // 发送到银行端并同步等待接收数据

            String bankCode = StringHelper.getSubstrBetweenStrs(msgContent, "<BankCode>", "</BankCode>");
            if ((!mqBanks.isEmpty()) && mqBanks.contains(bankCode)) {
                strMsg = realtimeMQService.sendAndRecvForFmqd(msgContent);
                realtimeMQService.decryptAndSendToFmqd(strMsg, msgId);
            } else if ((!socketBanks.isEmpty()) && socketBanks.contains(bankCode)) {
                // 发送到MBP，并接收新报文
                SimpleSocket l_ss = new SimpleSocket(mbpIP, mbpPORT, mbpTIMEOUT);
                if (!l_ss.go(msgContent)) {
                    CommonRes res = new CommonRes();
                    res.head.RetCode = "9000";
                    res.head.RetMsg = "连接前置机失败！";
                    strMsg = res.toXml();
                } else {
                    strMsg = l_ss.getRecv();
                }
                // TODO 接收到明文，直接转发回FMQD
                realtimeMQService.directSendToFmqd(strMsg, msgId);
            }

        } catch (JMSException e) {
            logger.error("FMQD消息监听异常", e.getMessage());
            CommonRes res = new CommonRes();
            res.head.RetCode = "9001";
            res.head.RetMsg = "消息发送失败！";
            strMsg = res.toXml();
            realtimeMQService.directSendToFmqd(strMsg, msgId);
        }
    }
}
