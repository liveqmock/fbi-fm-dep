package dep.fmqd.service;

import dep.fmqd.repository.dao.BiNotifyReverseMapper;
import dep.fmqd.repository.model.BiNotifyReverse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-29
 * Time: ����3:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiNotifyReverseService {
    @Autowired
    private BiNotifyReverseMapper mapper;

    public int insertRecord(BiNotifyReverse record) {
        return mapper.insertSelective(record);
    }
}
