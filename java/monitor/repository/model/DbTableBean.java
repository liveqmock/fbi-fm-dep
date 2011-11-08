package monitor.repository.model;

import dep.common.BoolType;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-8
 * Time: ÉÏÎç10:25
 * To change this template use File | Settings | File Templates.
 */
public class DbTableBean {
    private String tblname;
    private String chName;
    private long recordCount;
    private BoolType isHuge;

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public BoolType getHuge() {
        return isHuge;
    }

    public void setHuge(BoolType huge) {
        isHuge = huge;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public String getTblname() {
        return tblname;
    }

    public void setTblname(String tblname) {
        this.tblname = tblname;
    }
}
