package monitor.service;

import monitor.repository.dao.BiNotifyMessageDao;
import monitor.repository.model.BiNotifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÉÏÎç11:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiNotifyMessageService {

    @Autowired
    private BiNotifyMessageDao biNotifyMessageDao;

    public List<BiNotifyMessage> qryLastBiNotifyMsgs() {
        return biNotifyMessageDao.qryLastRecords();
    }

    public List<BiNotifyMessage> qryRecords(String opCode, String bankCode, String startDate, String endDate){
        return biNotifyMessageDao.qryRecords(opCode, bankCode, startDate, endDate);
    }
}
