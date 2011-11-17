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
 * Time: ����11:17
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
        String sysAddress = "<br><br>���ϵͳ��ַ��" + "10.143.19.179:8080/dep";
        String loginMsg = "<br><a href='http://10.143.19.179:8080/dep'>����˴���¼ϵͳ</a>";
        String title = "���ݽ���ƽ̨���ϵͳԤ����Ϣ";//�������ʼ��ı���
        String from = "boluo_1314@163.com";//�����﷢��
        String sendTo[] = {"boluo_1314@163.com", "yutaoqd@163.com"};//���͵�����
        MailSender.sendmail(title, from, sendTo, warnEvent.getEventDesc() + warnEvent.getWarnMsg() +
                sysAddress + loginMsg, "text/html;charset=gb2312");
        logger.info("����Ԥ���ʼ�����: " + warnEvent.getEventDesc() + warnEvent.getWarnMsg());
    }
}
