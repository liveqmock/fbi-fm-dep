package monitor.view;

import monitor.pojo.LogFileBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.PlatformService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.File;
import java.io.IOException;
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
public class SysDataClearAction {

    private static Logger logger = LoggerFactory.getLogger(SysDataClearAction.class);

    private LogFileBean infoLog;
    private LogFileBean errorLog;
    private LogFileBean platformLog;
    private LogFileBean logFolder;

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    private List<LogFileBean> depLogList;
    private List<LogFileBean> errorLogList;

    private LogFileBean[] selectedRecords;
    private LogFileBean[] selectedErrorRecords;


    @PostConstruct
    public void init() {
        try {
            qryLogfileSize();
            qryLogFiles();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取系统日志文件大小异常！", e.getMessage());
            MessageUtil.addError("读取系统日志文件大小异常！");
        }
    }

    private void qryLogfileSize() throws IOException {
        this.infoLog = platformService.getInfoLogFileSize();
        this.errorLog = platformService.getErrorLogFileSize();
        this.platformLog = platformService.getPlatformLogFileSize();
        this.logFolder = platformService.getLogFolderStatus();
    }

    private void qryLogFiles() throws IOException {
        depLogList = platformService.getLogFiles();
        errorLogList = platformService.getErrorLogFiles();
    }

    public void delInfoRecords() {
        if (selectedRecords == null || selectedRecords.length < 1) {
            MessageUtil.addWarn("至少选择一条记录！");
            return;
        }
        try {
            for (LogFileBean logFile : selectedRecords) {
                File file = new File(logFile.getFileName());
                if (file.exists()) {
                    file.delete();
                }
            }
            MessageUtil.addInfo("所选记录删除成功！");
            qryLogFiles();
        } catch (Exception e) {
            MessageUtil.addError("操作失败！");
            logger.info("操作失败！" + e.getMessage());
        }
    }

    public void delErrorRecords() {
        if (selectedErrorRecords == null || selectedErrorRecords.length < 1) {
            MessageUtil.addWarn("至少选择一条记录！");
            return;
        }
        try {
            for (LogFileBean logFile : selectedErrorRecords) {
                File file = new File(logFile.getFileName());
                if (file.exists()) {
                    file.delete();
                }
            }
            MessageUtil.addInfo("所选记录删除成功！");
            qryLogFiles();
        } catch (Exception e) {
            MessageUtil.addError("操作失败！");
            logger.info("操作失败！" + e.getMessage());
        }
    }
    //============================================================

    public LogFileBean getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(LogFileBean errorLog) {
        this.errorLog = errorLog;
    }

    public LogFileBean getInfoLog() {
        return infoLog;
    }

    public void setInfoLog(LogFileBean infoLog) {
        this.infoLog = infoLog;
    }

    public LogFileBean getPlatformLog() {
        return platformLog;
    }

    public void setPlatformLog(LogFileBean platformLog) {
        this.platformLog = platformLog;
    }

    public LogFileBean getLogFolder() {
        return logFolder;
    }

    public void setLogFolder(LogFileBean logFolder) {
        this.logFolder = logFolder;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public List<LogFileBean> getDepLogList() {
        return depLogList;
    }

    public void setDepLogList(List<LogFileBean> depLogList) {
        this.depLogList = depLogList;
    }

    public List<LogFileBean> getErrorLogList() {
        return errorLogList;
    }

    public void setErrorLogList(List<LogFileBean> errorLogList) {
        this.errorLogList = errorLogList;
    }

    public LogFileBean[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(LogFileBean[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public LogFileBean[] getSelectedErrorRecords() {
        return selectedErrorRecords;
    }

    public void setSelectedErrorRecords(LogFileBean[] selectedErrorRecords) {
        this.selectedErrorRecords = selectedErrorRecords;
    }
}
