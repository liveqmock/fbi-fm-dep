package dep.fmqd.service;

import dep.fmqd.repository.dao.BiNotifyAcctdetailMapper;
import dep.fmqd.repository.model.BiNotifyAcctdetail;
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
public class BiNotifyAcctdetailService {
    @Autowired
    private BiNotifyAcctdetailMapper mapper;

    public int insertRecord(BiNotifyAcctdetail record) {
        return mapper.insertSelective(record);
    }
}
