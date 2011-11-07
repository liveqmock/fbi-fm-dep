package dep.gateway.domain.T000;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import dep.gateway.domain.BaseBean;
import dep.gateway.domain.ReqHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T0004Req extends BaseBean {
    @XStreamAlias("Head")
    public ReqHead head = new ReqHead();
    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {

        public String Acct = "";
        public String AcctName = "";
        public String BankSerial = "";
        public String Reason = "";
        public String Date = "";
        public String Time = "";
    }
}
