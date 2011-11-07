package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;
import skyline.repository.dao.ActivemqMsgsMapper;
import skyline.repository.model.ActivemqMsgs;
import skyline.repository.model.ActivemqMsgsExample;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÉÏÎç11:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MQPstMsgService {

    @Autowired
    private ActivemqMsgsMapper mapper;
    private static final String TEST_QUEUE = PropertyManager.getProperty("dep.activemq.test.queue");

    public List<ActivemqMsgs> qryAllPstmsgs() {
        ActivemqMsgsExample example = new ActivemqMsgsExample();
        example.createCriteria().andContainerNotLike("%" + TEST_QUEUE + "%");
        return mapper.selectByExample(example);
    }

    public List<ActivemqMsgs> qryTestPstmsgs() {
        ActivemqMsgsExample example = new ActivemqMsgsExample();
        example.createCriteria().andContainerLike("%" + TEST_QUEUE + "%");
        return mapper.selectByExample(example);
    }

    @Transactional
    public void clearTestMsgs() {
        ActivemqMsgsExample example = new ActivemqMsgsExample();
        example.createCriteria().andContainerLike("%" + TEST_QUEUE + "%");
        mapper.deleteByExample(example);
    }
}
