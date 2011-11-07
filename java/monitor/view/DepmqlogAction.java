package monitor.view;

import monitor.repository.model.DepMqLog;
import monitor.service.MQLogMngService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
public class DepmqlogAction {

    private static Logger logger = LoggerFactory.getLogger(DepmqlogAction.class);
    @ManagedProperty(value = "#{MQLogMngService}")
    private MQLogMngService mqLogMngService;

    private List<DepMqLog> logList;
    private DepMqLog[] selectedRecords;

    @PostConstruct
    public void init() {
         logList = mqLogMngService.qryAllRecords();
    }

    public void delRecords() {
        if(selectedRecords == null || selectedRecords.length < 1) {
            MessageUtil.addWarn("����ѡ��һ����¼��");
            return;
        }
        try {
            mqLogMngService.delRecords(selectedRecords);
            MessageUtil.addInfo("��ѡ��¼ɾ���ɹ���");
            logList = mqLogMngService.qryAllRecords();
        } catch (Exception e) {
            MessageUtil.addError("����ʧ�ܣ�");
            logger.info("����ʧ�ܣ�" + e.getMessage());
        }
    }

    public List<DepMqLog> getLogList() {
        return logList;
    }

    public void setLogList(List<DepMqLog> logList) {
        this.logList = logList;
    }

    public MQLogMngService getMqLogMngService() {
        return mqLogMngService;
    }

    public void setMqLogMngService(MQLogMngService mqLogMngService) {
        this.mqLogMngService = mqLogMngService;
    }

    public DepMqLog[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(DepMqLog[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }
}
