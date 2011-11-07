package dep.fmqd.service;

import dep.fmqd.repository.dao.BiNotifyAcctstatusMapper;
import dep.fmqd.repository.model.BiNotifyAcctstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-29
 * Time: обнГ3:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiNotifyAcctstatusService {
    @Autowired
    private BiNotifyAcctstatusMapper mapper;

    public int insertRecord(BiNotifyAcctstatus record) {
        return mapper.insertSelective(record);
    }
}
