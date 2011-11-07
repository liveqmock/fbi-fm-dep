package monitor.service;

import monitor.repository.dao.DepMqLogMapper;
import monitor.repository.model.DepMqLog;
import monitor.repository.model.DepMqLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÉÏÎç11:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MQLogMngService {

    @Autowired
    private DepMqLogMapper mapper;

    public List<DepMqLog> qryAllRecords() {
        DepMqLogExample example = new DepMqLogExample();
        example.setOrderByClause("operdat desc");
        return mapper.selectByExample(example);
    }

    @Transactional
    public void insertRecord(DepMqLog log) {
        mapper.insertSelective(log);
    }

    @Transactional
    public void delRecords(DepMqLog[] logs) {
        for (DepMqLog log : logs) {
            DepMqLogExample example = new DepMqLogExample();
            example.createCriteria().andPkidEqualTo(log.getPkid());
            if (mapper.selectByExample(example) != null)
                mapper.deleteByExample(example);
        }
    }
}
