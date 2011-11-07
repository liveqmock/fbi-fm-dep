package monitor.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-7
 * Time: 上午11:48
 * To change this template use File | Settings | File Templates.
 */
// 系统元素
public class SystemElmtBean {

    private String elmtName;    // 元素中文名
    private String picName;     // 图片名
    private String totalSize;   // 总容量
    private String freeSize;    // 可用量（剩余）
    private double usedPart;    // 利用率
    private double warnPart = 0.1;  // 警戒比例
    private boolean isWarn;   // 是否超越警戒
    private String warnMsg;

    public String getElmtName() {
        return elmtName;
    }

    public void setElmtName(String elmtName) {
        this.elmtName = elmtName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(String freeSize) {
        this.freeSize = freeSize;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public double getUsedPart() {
        return usedPart;
    }

    public void setUsedPart(double usedPart) {
        this.usedPart = usedPart;
        this.isWarn = this.usedPart >= (1 - this.warnPart) ? true : false;
        this.warnMsg = this.isWarn ? "是" : "否";
    }

    public double getWarnPart() {
        return warnPart;
    }

    public void setWarnPart(double warnPart) {
        this.warnPart = warnPart;
    }

    public boolean isWarn() {
        return isWarn;
    }

    public void setWarn(boolean warn) {
        isWarn = warn;
    }

    public String getWarnMsg() {
        return warnMsg;
    }

    public void setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
    }
}
