package dep.service;

import dep.common.BoolType;
import dep.common.StringHelper;
import dep.repository.dao.BiNotifyMsgDao;
import dep.repository.dao.CommonDao;
import dep.repository.model.BiNotifyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-6
 * Time: ÉÏÎç9:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiNotifyMsgService {

    private static Logger logger = LoggerFactory.getLogger(BiNotifyMsgService.class);

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private BiNotifyMsgDao biNotifyMsgDao;


    public int updateGetflag(String pkid) {
        return biNotifyMsgDao.updateGetflagByPkid(pkid);
    }

    public String insertRecord(String datagram) {

        String opCode = StringHelper.getSubstrBetweenStrs(datagram, "<OpCode>", "</OpCode>");
        String bankCode = StringHelper.getSubstrBetweenStrs(datagram, "<BankCode>", "</BankCode>");

        return insertRecord(opCode, bankCode);

    }

    public String insertRecord(String opCode, String bankCode) {

        BiNotifyMessage message = new BiNotifyMessage();
        message.setOpcode(opCode);
        message.setBankcode(bankCode);
        Date date = new Date();
        message.setNotifydate(date);
        message.setRowId(commonDao.getSysGuid());
        message.setGetFlag(BoolType.FALSE.getCode());
        message.setCreatedBy(-9495L);
        message.setCreatedDate(date);
        message.setLastUpdBy(-9495L);
        message.setLastUpdDate(date);
        message.setModificationNum(1);

        if (biNotifyMsgDao.insertRecord(message) == 1) {
            return message.getRowId();
        }

        return null;
    }

}
