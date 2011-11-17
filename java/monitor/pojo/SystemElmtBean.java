package monitor.pojo;

import monitor.common.BoolType;

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
    private BoolType isWarn;

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
        checkWarnable();
    }

    public double getWarnPart() {
        return warnPart;
    }

    public void setWarnPart(double warnPart) {
        this.warnPart = warnPart;
    }

    public BoolType getWarn() {
        return isWarn;
    }

    public void setWarn(BoolType warn) {
        isWarn = warn;
    }

    public void checkWarnable() {
        isWarn = this.usedPart >= (1 - this.warnPart) ? BoolType.TRUE : BoolType.FALSE;
    }
}
