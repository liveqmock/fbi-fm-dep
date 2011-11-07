package monitor.view;

import monitor.pojo.LogFileBean;
import monitor.pojo.SystemElmtBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.OperatingSystemService;
import skyline.service.PlatformService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
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
public class SysStatusWarnAction {

    private static Logger logger = LoggerFactory.getLogger(SysStatusWarnAction.class);

    private List<SystemElmtBean> elmtBeans;
    private LogFileBean infoLog;
    private LogFileBean errorLog;
    private LogFileBean platformLog;

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    @PostConstruct
    public void init() {
        try {
            qryLogfileSize();
            elmtBeans = OperatingSystemService.getAllSystemElmts();
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addError("系统状态监测异常！");
            elmtBeans = new ArrayList<SystemElmtBean>();
        }
    }

    private void qryLogfileSize() {
        this.infoLog = platformService.getInfoLogFileSize();
        this.errorLog = platformService.getErrorLogFileSize();
        this.platformLog = platformService.getPlatformLogFileSize();
    }

    //============================================================

    public List<SystemElmtBean> getElmtBeans() {
        return elmtBeans;
    }

    public void setElmtBeans(List<SystemElmtBean> elmtBeans) {
        this.elmtBeans = elmtBeans;
    }

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

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }
}
