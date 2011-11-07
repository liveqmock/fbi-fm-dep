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
 * Time: ����2:05
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
                status = "����������";
                saveLog("״̬���");
            } else {
                isRun = false;
                status = "ϵͳδ����";
            }
            initList();

        } catch (JMSException e) {
            isRun = false;
            status = "ϵͳδ����";
        }
    }

    private void initList() {
        testMsgList = mqPstMsgService.qryTestPstmsgs();
    }

    public void startup() {
        if (isRun) {
            MessageUtil.addWarn("��Ϣ�������ѿ�����");
            return;
        }
        String cmd = PropertyManager.getProperty("dep.mq.start.cmd");
        try {
            toolsService.executeCmd(cmd);
            isRun = true;
            status = "����";
            saveLog(status);
            MessageUtil.addInfo("��Ϣ�����������У�");
        } catch (Exception e) {
            logger.error("������Ϣ������ʧ�ܣ�", "�ű���" + cmd + e.getMessage());
            MessageUtil.addError("������Ϣ������ʧ�ܣ�����ϵ����Ա�ֶ���������");
        }
    }

    public void shutdown() {
        if (!isRun) {
            MessageUtil.addWarn("��Ϣ�������ѹرգ�");
            return;
        }
        String cmd = PropertyManager.getProperty("dep.mq.shutdown.cmd");
        try {
            toolsService.executeCmd(cmd);
            isRun = false;
            status = "�ر�";
            saveLog(status);
            MessageUtil.addInfo("��Ϣ�������ر��У�");
        } catch (Exception e) {
            logger.error("�ر���Ϣ������ʧ�ܣ�", "�ű���" + cmd + e.getMessage());
            MessageUtil.addError("�ر���Ϣ������ʧ�ܣ�����ϵ����Ա�ֶ��رշ���");
        }
    }

    public void checkMqStatus() {
        try {
            if (mqTestService.checkMQStatus()) {
                isRun = true;
                status = "����������";
                MessageUtil.addInfo("״̬�������" + status);
            } else {
                isRun = false;
                status = "ϵͳδ����";
                saveLog("״̬���");
                MessageUtil.addInfo("״̬�������" + status);
            }
            initList();
        } catch (JMSException e) {
            isRun = false;
            status = "ϵͳδ����";
            MessageUtil.addInfo("״̬�������" + status);
        }
    }

    public void clearTestMsgs() {
        try {
            mqPstMsgService.clearTestMsgs();
            initList();
            saveLog("ɾ��������Ϣ");
            MessageUtil.addInfo("���Զ������������ɣ�");
        } catch (Exception e) {
            MessageUtil.addError("����ʧ�ܣ�" + e.getMessage());
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
            MessageUtil.addError("��¼������־ʧ�ܣ�");
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
