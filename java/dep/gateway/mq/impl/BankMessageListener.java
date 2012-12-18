package dep.gateway.mq.impl;

import dep.fmqd.service.BiDbService;
import dep.gateway.domain.CommonRes;
import dep.gateway.mq.RealtimeMQService;
import dep.repository.model.DepBirouteLog;
import dep.service.BiNotifyMsgService;
import dep.service.BiRouteLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BankMessageListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(BankMessageListener.class);

    @Autowired
    private RealtimeMQService realtimeMQService;
    @Autowired
    private BiDbService biDbService;
    @Autowired
    private BiRouteLogService biRouteLogService;
    @Autowired
    private BiNotifyMsgService biNotifyMsgService;

    @Override
    public void onMessage(Message message) {

        /*try {
            TextMessage txtMsg = (TextMessage) message;
            logger.info(txtMsg.getText());
            realtimeMQService.sendToBank("302", new CommonRes().toXml(), message.getJMSMessageID());
            return;
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/

        // -----------------------------

        DepBirouteLog routeLog = biRouteLogService.insertRecord(message);

        String msgContent = biRouteLogService.getMessageContent();

        int rtnResult = 0;
        CommonRes res = new CommonRes();
        String notifyMsgId = null;
        try {
            rtnResult = biDbService.handleMessage(routeLog.getOpcode(), msgContent);
            if (rtnResult != 1) {
                res.head.RetCode = "9999";
                res.head.RetMsg = "接收失败！";
            } else {
                // 保存NotifyMsg
                notifyMsgId = biNotifyMsgService.insertRecord(routeLog.getOpcode(), routeLog.getBankcode());
            }
        } catch (Exception e) {
            res.head.RetCode = "9999";
            res.head.RetMsg = "接收失败！" + e.getMessage();
        }

        realtimeMQService.sendToBank(routeLog.getBankcode(), res.toXml(), routeLog.getMsgId());

        // 更新NotifyMsg
        biNotifyMsgService.updateGetflag(notifyMsgId);
        //  根据pkid 更新接口表路由状态
        biRouteLogService.updateRtncodeByPkid(routeLog.getPkid(), res.head.RetCode);
    }
}
