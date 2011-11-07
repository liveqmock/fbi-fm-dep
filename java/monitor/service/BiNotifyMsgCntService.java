package monitor.service;

import monitor.repository.dao.BiNotifyMsgCntDao;
import monitor.repository.model.BiNotifyMsgCnt;
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
public class BiNotifyMsgCntService {

    @Autowired
    private BiNotifyMsgCntDao biNotifyMsgCntDao;

    public List<BiNotifyMsgCnt> qryRecordsCnt(String opcode, String bankcode, String startDate, String endDate) {
        return biNotifyMsgCntDao.qryRecordsCnt(opcode, bankcode, startDate, endDate);
    }
}
