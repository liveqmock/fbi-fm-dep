package monitor.warn.event.impl;

import monitor.warn.event.WarnEvent;
import monitor.warn.event.WarnEventHandler;
import monitor.warn.mail.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-14
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
public class WarnEventMailHandlerImpl implements WarnEventHandler {

    private static Logger logger = LoggerFactory.getLogger(WarnEventMailHandlerImpl.class);
    private static WarnEventMailHandlerImpl mailHandlerImpl;

    private WarnEventMailHandlerImpl() {}

    public static WarnEventHandler getInstance() {
      if(mailHandlerImpl == null) {
          return new WarnEventMailHandlerImpl();
      }
        return mailHandlerImpl;
    }

    @Override
    public void onWarn(WarnEvent warnEvent) throws Exception {
        String sysAddress = "<br><br>监控系统地址：" + "10.143.19.179:8080/dep";
        String loginMsg = "<br><a href='http://10.143.19.179:8080/dep'>点击此处登录系统</a>";
        String title = "数据交换平台监控系统预警信息";//所发送邮件的标题
        String from = "boluo_1314@163.com";//从那里发送
        String sendTo[] = {"boluo_1314@163.com", "yutaoqd@163.com"};//发送到那里
        MailSender.sendmail(title, from, sendTo, warnEvent.getEventDesc() + warnEvent.getWarnMsg() +
                sysAddress + loginMsg, "text/html;charset=gb2312");
        logger.info("发送预警邮件内容: " + warnEvent.getEventDesc() + warnEvent.getWarnMsg());
    }
}
