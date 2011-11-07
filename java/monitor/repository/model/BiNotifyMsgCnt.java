package monitor.repository.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-1
 * Time: ÉÏÎç11:44
 * To change this template use File | Settings | File Templates.
 */
public class BiNotifyMsgCnt {

    private String opcode;
    private String bankcode;
    private long count;

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
}
