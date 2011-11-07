package monitor.view;

import monitor.service.MQPstMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import skyline.repository.model.ActivemqMsgs;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: ÏÂÎç2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class MsgPstStatusAction {

    private static Logger logger = LoggerFactory.getLogger(MsgPstStatusAction.class);

    private String dbIP;
    private String dbUser;
    private String dbPassword;

    @ManagedProperty(value = "#{MQPstMsgService}")
    private MQPstMsgService mqPstMsgService;

    private List<ActivemqMsgs> pstMsgList;

    @PostConstruct
    public void init() {
        dbIP = PropertyManager.getProperty("pub.platform.db.ConnectionManager.sConnStr").split("@")[1];
        dbUser = PropertyManager.getProperty("pub.platform.db.ConnectionManager.user");
        dbPassword = PropertyManager.getProperty("pub.platform.db.ConnectionManager.passwd");

        pstMsgList = mqPstMsgService.qryAllPstmsgs();
    }

    public String getDbIP() {
        return dbIP;
    }

    public void setDbIP(String dbIP) {
        this.dbIP = dbIP;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public MQPstMsgService getMqPstMsgService() {
        return mqPstMsgService;
    }

    public void setMqPstMsgService(MQPstMsgService mqPstMsgService) {
        this.mqPstMsgService = mqPstMsgService;
    }

    public List<ActivemqMsgs> getPstMsgList() {
        return pstMsgList;
    }

    public void setPstMsgList(List<ActivemqMsgs> pstMsgList) {
        this.pstMsgList = pstMsgList;
    }
}
