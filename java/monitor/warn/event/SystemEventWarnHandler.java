package monitor.warn.event;

import monitor.warn.camel.CamelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-18
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SystemEventWarnHandler {

    private static Logger logger = LoggerFactory.getLogger(SystemEventWarnHandler.class);

    @Autowired
    private SysEventScannerService sysEventScannerService;
    @Autowired
    private CamelService camelService;

    public void handleWarn() {
        try {
            sysEventScannerService.warnAllElmts();
        } catch (Exception e) {
            logger.error("系统检测预警事件信息失败！", e.getMessage());
        }
        try {
            camelService.startForShort();
        } catch (Exception e) {
            logger.error("系统发送邮件预警信息失败！", e.getMessage());
        }
    }
}
