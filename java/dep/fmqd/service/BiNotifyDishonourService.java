package dep.fmqd.service;

import dep.fmqd.repository.dao.BiNotifyDishonourMapper;
import dep.fmqd.repository.model.BiNotifyDishonour;
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
public class BiNotifyDishonourService {
    @Autowired
    private BiNotifyDishonourMapper mapper;

    public int insertRecord(BiNotifyDishonour record) {
        return mapper.insertSelective(record);
    }
}
