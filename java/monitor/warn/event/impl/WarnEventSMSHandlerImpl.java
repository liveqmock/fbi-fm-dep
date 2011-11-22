package monitor.warn.event.impl;

import monitor.warn.event.WarnEvent;
import monitor.warn.event.WarnEventHandler;
import monitor.warn.event.jms.JmsMessenger;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-14
 * Time: ÉÏÎç11:17
 * To change this template use File | Settings | File Templates.
 */
public class WarnEventSMSHandlerImpl implements WarnEventHandler {

    @Override
    public void onWarn(WarnEvent warnEvent) throws NamingException, JMSException {
        JmsMessenger.getInstance().sendMsg(warnEvent.getWarnType(), warnEvent.getEventDesc()+warnEvent.getWarnMsg());
    }
}
