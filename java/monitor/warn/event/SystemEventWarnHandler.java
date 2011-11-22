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
 * Time: ����1:23
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
            logger.error("ϵͳ���Ԥ���¼���Ϣʧ�ܣ�", e.getMessage());
        }
        try {
            camelService.startForShort();
        } catch (Exception e) {
            logger.error("ϵͳ�����ʼ�Ԥ����Ϣʧ�ܣ�", e.getMessage());
        }
    }
}
