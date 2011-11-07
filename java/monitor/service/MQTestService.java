package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skyline.common.utils.MQStatusTester;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: обнГ3:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MQTestService {

    @Autowired
    private MQPstMsgService mqPstMsgService;

    public boolean checkMQStatus() throws JMSException {
        return new MQStatusTester().sendTestMsg();
    }

}
