package monitor.warn.event;

import monitor.common.WarnType;
import monitor.warn.event.impl.WarnEventMailHandlerImpl;
import monitor.warn.event.impl.WarnEventSMSHandlerImpl;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-16
 * Time: ÏÂÎç2:38
 * To change this template use File | Settings | File Templates.
 */
public class WarnEvent {

    private String eventDesc;
    private WarnType warnType;
    private String warnMsg;
    private boolean isSendWarnMsg;

    private WarnEventHandler eventHandler;

    public WarnEvent(String eventDesc, boolean isSendWarnMsg, String warnMsg, WarnType warnType) {
        this.eventDesc = eventDesc;
        this.isSendWarnMsg = isSendWarnMsg;
        this.warnMsg = warnMsg;
        this.warnType = warnType;
        if (isSendWarnMsg) {
            if (WarnType.EMAIL.getCode().equals(warnType.getCode())) {
                eventHandler = WarnEventMailHandlerImpl.getInstance();
            } else if (WarnType.SMS.getCode().equals(warnType.getCode())) {
                eventHandler = new WarnEventSMSHandlerImpl();
            }
        }
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public boolean isSendWarnMsg() {
        return isSendWarnMsg;
    }

    public void setSendWarnMsg(boolean sendWarnMsg) {
        isSendWarnMsg = sendWarnMsg;
    }

    public String getWarnMsg() {
        return warnMsg;
    }

    public void setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
    }

    public WarnType getWarnType() {
        return warnType;
    }

    public void setWarnType(WarnType warnType) {
        this.warnType = warnType;
    }

    public WarnEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(WarnEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void sendWarnMsg() throws Exception {
        this.eventHandler.onWarn(this);
    }
}
