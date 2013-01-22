package dep.service;

import dep.common.AppCode;
import dep.common.BiDownType;
import dep.common.BoolType;
import dep.common.StringHelper;
import dep.gateway.crypt.DesCrypter;
import dep.repository.dao.BiRouteDao;
import dep.repository.dao.CommonDao;
import dep.repository.model.DepBirouteLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-6
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiRouteLogService {

    private static Logger logger = LoggerFactory.getLogger(BiRouteLogService.class);

    @Autowired
    private BiRouteDao biRouteDao;
    @Autowired
    private CommonDao commonDao;

    private String messageContent;

    @Transactional
    public DepBirouteLog insertRecord(Message message) {

        DepBirouteLog routeLog = null;
        try {

            String msgId = message.getJMSMessageID();
            TextMessage textMessage = (TextMessage) message;
            messageContent = DesCrypter.getInstance().decrypt(textMessage.getText());

            logger.info(messageContent);
            String bankCode = StringHelper.getSubstrBetweenStrs(messageContent, "<BankCode>", "</BankCode>");
            String opCode = StringHelper.getSubstrBetweenStrs(messageContent, "<OpCode>", "</OpCode>");

            routeLog = new DepBirouteLog();
            routeLog.setPkid(commonDao.getSysGuid());
            routeLog.setMsgId(msgId);
            routeLog.setMsgFrom(bankCode);
            routeLog.setMsgTo(AppCode.FMQD.getCode());
            routeLog.setCurrentAddr(AppCode.DEP.getCode());
            routeLog.setIsArrive(BoolType.FALSE.getCode());
            routeLog.setCorralationId(message.getJMSCorrelationID());
            routeLog.setMsgType(message.getStringProperty("messageType"));
            routeLog.setBankcode(bankCode);
            routeLog.setOpcode(opCode);
            routeLog.setLogdate(new Date());

            // TODO 判断是否落地  默认正常通过
            routeLog.setDownStatus(BiDownType.NORMAL.getCode());

            biRouteDao.insertRecord(routeLog);

        } catch (Exception e) {
            logger.error("保存报文到接口路由表失败！", e);
            routeLog = null;
        }
        return routeLog;
    }

    @Transactional
    public int updateRtncodeByPkid(String pkid, String rtnCode) {
        return biRouteDao.updateRtncodeByPkid(pkid, rtnCode);
    }

    // ===========================================================

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
