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

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-28
 * Time: ����4:23
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

            DepBirouteLog routeLog = biRouteLogService.insertRecord(message);

            String msgContent = biRouteLogService.getMessageContent();
            logger.info("DEP���ձ��ģ�" + msgContent);

            int rtnResult = 0;
            CommonRes res = new CommonRes();
            String notifyMsgId = null;
            try {
                rtnResult = biDbService.handleMessage(routeLog.getOpcode(), msgContent);
                if (rtnResult != 1) {
                    res.head.RetCode = "9999";
                    res.head.RetMsg = "����ʧ�ܣ�";
                } else {
                    // ����NotifyMsg
                    notifyMsgId = biNotifyMsgService.insertRecord(routeLog.getOpcode(), routeLog.getBankcode());
                }
            } catch (Exception e) {
                res.head.RetCode = "9999";
                res.head.RetMsg = "����ʧ�ܣ�" + e.getMessage();
            }

            realtimeMQService.sendToBank(routeLog.getBankcode(), res.toXml(), routeLog.getMsgId());

            // ����NotifyMsg
            biNotifyMsgService.updateGetflag(notifyMsgId);
            //  ����pkid ���½ӿڱ�·��״̬
            biRouteLogService.updateRtncodeByPkid(routeLog.getPkid(), res.head.RetCode);
    }
}
