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
 * Time: 下午2:54
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
            logger.error("系统发送邮件时发生异常！", e.getMessage());
        }
    }

    public List<WarnEvent> scanAllElmts() {
        List<WarnEvent> warnEventList = scanSysStatus();
        warnEventList.addAll(scanDBTblRecordCnt());
        warnEventList.add(scanNotRecvedMsgs());
        warnEventList.add(scanLogFolder());

        return warnEventList;
    }

    // 检查服务器系统状态
    public List<WarnEvent> scanSysStatus() {
        List<WarnEvent> warnEventList = new ArrayList<WarnEvent>();
        try {
            for (SystemElmtBean elmt : OperatingSystemService.getAllSystemElmts()) {
                if (BoolType.TRUE.getCode().equals(elmt.getWarn().getCode())) {
                    WarnEvent warnEvent = new WarnEvent(elmt.getElmtName() + "利用率过高！利用比例：" + String.format("%.2f", elmt.getUsedPart() * 100) + "%",
                            true, "建议优化服务器！", WarnType.EMAIL);
                    warnEventList.add(warnEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warnEventList;
    }

    // 检测数据库表记录数
    public List<WarnEvent> scanDBTblRecordCnt() {
        List<WarnEvent> warnEventList = new ArrayList<WarnEvent>();
        for (DbTableBean tableBean : dbStatusMngService.qrySyslogTblStatus()) {
            if (BoolType.TRUE.getCode().equals(tableBean.getHuge().getCode())) {
                WarnEvent warnEvent = new WarnEvent("数据库表" + tableBean.getTblname() + "记录数过多！",
                        true, "建议删除部分数据！", WarnType.EMAIL);
                warnEventList.add(warnEvent);
            }
        }
        return warnEventList;
    }

    // 检查是否有未被接收的消息
    public WarnEvent scanNotRecvedMsgs() {
        WarnEvent warnEvent = null;
        if (!mqPstMsgService.qryAllPstmsgs().isEmpty()) {
            warnEvent = new WarnEvent("消息服务器持久化表中有未被接收的消息记录！", true, "建议检查数据链路！", WarnType.EMAIL);
        }
        return warnEvent;
    }

    // 检查系统日志文件目录大小是否超标
    public WarnEvent scanLogFolder() {
        WarnEvent warnEvent = null;
        try {
            LogFileBean logFileBean = platformService.getLogFolderStatus();
            String strFolderSize = logFileBean.getFileSize();
            strFolderSize = strFolderSize.substring(0, strFolderSize.length() - 1);
            double folderSize = Double.parseDouble(strFolderSize);
            if (folderSize >= PropertyManager.getDoubleProperty("pub.platform.log.warn.limitsize")) {
                warnEvent = new WarnEvent("系统日志文件目录" + logFileBean.getFileName() + " 大小 " +
                        logFileBean.getFileSize() + "已超标！", true, "建议删除部分旧日志！", WarnType.EMAIL);
            }
        } catch (IOException e) {
            logger.error("读取系统日志目录异常!", e.getMessage());
        }

        return warnEvent;
    }

}
