package monitor.repository.sql;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÏÂÎç3:35
 * To change this template use File | Settings | File Templates.
 */
public class ConstSQL {
    public static final String QRY_LAST_NOTIFY_MSG_LIST = "select * from bi_notify_message bnm1 " +
            " join (select t.bankcode as tbankcode,max(t.notifydate) maxdate from BI_NOTIFY_MESSAGE t " +
            " group by t.bankcode) bnm2 on bnm1.bankcode = bnm2.tbankcode" +
            " and bnm1.notifydate = bnm2.maxdate order by bnm1.bankcode";

    public static final String QRY_NOTIFY_MSG_LIST = "select * from bi_notify_message ";

    public static final String QRY_BI_NOTIFYMSG_CNT = " select opcode,bankcode,count(*) as count  from BI_NOTIFY_MESSAGE t ";
}
