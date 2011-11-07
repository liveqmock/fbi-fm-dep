package monitor.view;

import monitor.repository.model.DepMqLog;
import monitor.service.MQLogMngService;
import monitor.service.MQPstMsgService;
import monitor.service.MQTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;
import skyline.common.utils.MessageUtil;
import skyline.repository.model.ActivemqMsgs;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.jms.JMSException;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class MqStatusMngAction {

    private static Logger logger = LoggerFactory.getLogger(MqStatusMngAction.class);

    private String status;
    private boolean isRun;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{MQTestService}")
    private MQTestService mqTestService;
    @ManagedProperty(value = "#{MQPstMsgService}")
    private MQPstMsgService mqPstMsgService;
    @ManagedProperty(value = "#{MQLogMngService}")
    private MQLogMngService mqLogMngService;

    private List<ActivemqMsgs> testMsgList;

    @PostConstruct
    public void init() {
        try {
            if (mqTestService.checkMQStatus()) {
                isRun = true;
                status = "正常运行中";
                saveLog("状态检查");
            } else {
                isRun = false;
                status = "系统未启动";
            }
            initList();

        } catch (JMSException e) {
            isRun = false;
            status = "系统未启动";
        }
    }

    private void initList() {
        testMsgList = mqPstMsgService.qryTestPstmsgs();
    }

    public void startup() {
        if (isRun) {
            MessageUtil.addWarn("消息服务器已开启！");
            return;
        }
        String cmd = PropertyManager.getProperty("dep.mq.start.cmd");
        try {
            toolsService.executeCmd(cmd);
            isRun = true;
            status = "开启";
            saveLog(status);
            MessageUtil.addInfo("消息服务器启动中！");
        } catch (Exception e) {
            logger.error("启动消息服务器失败！", "脚本：" + cmd + e.getMessage());
            MessageUtil.addError("启动消息服务器失败！请联系管理员手动启动服务！");
        }
    }

    public void shutdown() {
        if (!isRun) {
            MessageUtil.addWarn("消息服务器已关闭！");
            return;
        }
        String cmd = PropertyManager.getProperty("dep.mq.shutdown.cmd");
        try {
            toolsService.executeCmd(cmd);
            isRun = false;
            status = "关闭";
            saveLog(status);
            MessageUtil.addInfo("消息服务器关闭中！");
        } catch (Exception e) {
            logger.error("关闭消息服务器失败！", "脚本：" + cmd + e.getMessage());
            MessageUtil.addError("关闭消息服务器失败！请联系管理员手动关闭服务！");
        }
    }

    public void checkMqStatus() {
        try {
            if (mqTestService.checkMQStatus()) {
                isRun = true;
                status = "正常运行中";
                MessageUtil.addInfo("状态检查结果：" + status);
            } else {
                isRun = false;
                status = "系统未启动";
                saveLog("状态检查");
                MessageUtil.addInfo("状态检查结果：" + status);
            }
            initList();
        } catch (JMSException e) {
            isRun = false;
            status = "系统未启动";
            MessageUtil.addInfo("状态检查结果：" + status);
        }
    }

    public void clearTestMsgs() {
        try {
            mqPstMsgService.clearTestMsgs();
            initList();
            saveLog("删除测试消息");
            MessageUtil.addInfo("测试队列数据清空完成！");
        } catch (Exception e) {
            MessageUtil.addError("操作失败！" + e.getMessage());
        }
    }

    private void saveLog(String action) {
        try {
            OperatorManager om = PlatformService.getOperatorManager();
            DepMqLog log = new DepMqLog();
            log.setSysnam("DEP");
            log.setAction(action);
            log.setOperid(om.getOperatorId());
            log.setOpernam(om.getOperatorName());
            log.setOperdat(new Date());
            mqLogMngService.insertRecord(log);
        } catch (Exception e) {
            MessageUtil.addError("记录操作日志失败！");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public MQTestService getMqTestService() {
        return mqTestService;
    }

    public void setMqTestService(MQTestService mqTestService) {
        this.mqTestService = mqTestService;
    }

    public MQLogMngService getMqLogMngService() {
        return mqLogMngService;
    }

    public void setMqLogMngService(MQLogMngService mqLogMngService) {
        this.mqLogMngService = mqLogMngService;
    }

    public List<ActivemqMsgs> getTestMsgList() {
        return testMsgList;
    }

    public void setTestMsgList(List<ActivemqMsgs> testMsgList) {
        this.testMsgList = testMsgList;
    }

    public MQPstMsgService getMqPstMsgService() {
        return mqPstMsgService;
    }

    public void setMqPstMsgService(MQPstMsgService mqPstMsgService) {
        this.mqPstMsgService = mqPstMsgService;
    }
}
