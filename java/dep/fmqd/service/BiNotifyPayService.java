package dep.fmqd.service;

import dep.fmqd.repository.dao.BiNotifyPayMapper;
import dep.fmqd.repository.model.BiNotifyPay;
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
public class BiNotifyPayService {
    @Autowired
    private BiNotifyPayMapper mapper;

    public int insertRecord(BiNotifyPay record) {
        return mapper.insertSelective(record);
    }
}
