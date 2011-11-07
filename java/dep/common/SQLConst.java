package dep.common;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-6
 * Time: ÉÏÎç11:24
 * To change this template use File | Settings | File Templates.
 */
public class SQLConst {
    public static final String INSERT_BIROUTE_LOG = "insert into DEP_BIROUTE_LOG(" +
            "  pkid, msg_id, msg_from, msg_to, current_addr, is_arrive," +
            "  msg_rtncode, corralation_id, msg_type, down_status," +
            "  bankcode, opcode, logdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_BINOTIFY_MSG = "insert into BI_NOTIFY_MESSAGE(" +
            "  row_id , opcode , bankcode, notifydate, get_flag, created_date, last_upd_date) " +
            "  values(?,?,?,?,?,?,?)";

    public static final String UPDATE_BIROUTE_RTNCODE = "update  DEP_BIROUTE_LOG t set " +
            " t.current_addr = t.msg_to,t.is_arrive = '1',t.msg_rtncode = ?,t.version = t.version+1" +
            " where t.pkid = ?";

    public static final String UPDATE_BINOTIFY_MSG_GETFLAG = "update  BI_NOTIFY_MESSAGE set " +
            " GET_FLAG = '1',MODIFICATION_NUM = MODIFICATION_NUM+1 where ROW_ID = ?";
}
