package monitor.warn.event;

import dep.common.BoolType;
import monitor.common.WarnType;
import monitor.pojo.LogFileBean;
import monitor.pojo.SystemElmtBean;
import monitor.repository.model.DbTableBean;
import monitor.service.DbStatusMngService;
import monitor.service.MQPstMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.platform.advance.utils.PropertyManager;
import skyline.service.OperatingSystemService;
import skyline.service.PlatformService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-16
 * Time: ����2:54
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SysEventScannerService {

    private static Logger logger = LoggerFactory.getLogger(SysEventScannerService.class);
    @Autowired
    private DbStatusMngService dbStatusMngService;
    @Autowired
    private MQPstMsgService mqPstMsgService;
    @Autowired
    private PlatformService platformService;

    public void warnAllElmts() {
        List<WarnEvent> warnEventList = scanDBTblRecordCnt();
        warnEventList.addAll(scanSysStatus());
        warnEventList.add(scanNotRecvedMsgs());
        warnEventList.add(scanLogFolder());
        try {
            for (WarnEvent event : warnEventList) {
                if (event != null && event.isSendWarnMsg()) {
                    event.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ϵͳ�����ʼ�ʱ�����쳣��", e.getMessage());
        }
    }

    public List<WarnEvent> scanAllElmts() {
        List<WarnEvent> warnEventList = scanSysStatus();
        warnEventList.addAll(scanDBTblRecordCnt());
        warnEventList.add(scanNotRecvedMsgs());
        warnEventList.add(scanLogFolder());

        return warnEventList;
    }

    // ��������ϵͳ״̬
    public List<WarnEvent> scanSysStatus() {
        List<WarnEvent> warnEventList = new ArrayList<WarnEvent>();
        try {
            for (SystemElmtBean elmt : OperatingSystemService.getAllSystemElmts()) {
                if (BoolType.TRUE.getCode().equals(elmt.getWarn().getCode())) {
                    WarnEvent warnEvent = new WarnEvent(elmt.getElmtName() + "�����ʹ��ߣ����ñ�����" + String.format("%.2f", elmt.getUsedPart() * 100) + "%",
                            true, "�����Ż���������", WarnType.EMAIL);
                    warnEventList.add(warnEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warnEventList;
    }

    // ������ݿ���¼��
    public List<WarnEvent> scanDBTblRecordCnt() {
        List<WarnEvent> warnEventList = new ArrayList<WarnEvent>();
        for (DbTableBean tableBean : dbStatusMngService.qrySyslogTblStatus()) {
            if (BoolType.TRUE.getCode().equals(tableBean.getHuge().getCode())) {
                WarnEvent warnEvent = new WarnEvent("���ݿ��" + tableBean.getTblname() + "��¼�����࣡",
                        true, "����ɾ���������ݣ�", WarnType.EMAIL);
                warnEventList.add(warnEvent);
            }
        }
        return warnEventList;
    }

    // ����Ƿ���δ�����յ���Ϣ
    public WarnEvent scanNotRecvedMsgs() {
        WarnEvent warnEvent = null;
        if (!mqPstMsgService.qryAllPstmsgs().isEmpty()) {
            warnEvent = new WarnEvent("��Ϣ�������־û�������δ�����յ���Ϣ��¼��", true, "������������·��", WarnType.EMAIL);
        }
        return warnEvent;
    }

    // ���ϵͳ��־�ļ�Ŀ¼��С�Ƿ񳬱�
    public WarnEvent scanLogFolder() {
        WarnEvent warnEvent = null;
        try {
            LogFileBean logFileBean = platformService.getLogFolderStatus();
            String strFolderSize = logFileBean.getFileSize();
            strFolderSize = strFolderSize.substring(0, strFolderSize.length() - 1);
            double folderSize = Double.parseDouble(strFolderSize);
            if (folderSize >= PropertyManager.getDoubleProperty("pub.platform.log.warn.limitsize")) {
                warnEvent = new WarnEvent("ϵͳ��־�ļ�Ŀ¼" + logFileBean.getFileName() + " ��С " +
                        logFileBean.getFileSize() + "�ѳ��꣡", true, "����ɾ�����־���־��", WarnType.EMAIL);
            }
        } catch (IOException e) {
            logger.error("��ȡϵͳ��־Ŀ¼�쳣!", e.getMessage());
        }

        return warnEvent;
    }

}
