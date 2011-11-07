package monitor.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-7
 * Time: ����11:48
 * To change this template use File | Settings | File Templates.
 */
// ϵͳԪ��
public class SystemElmtBean {

    private String elmtName;    // Ԫ��������
    private String picName;     // ͼƬ��
    private String totalSize;   // ������
    private String freeSize;    // ��������ʣ�ࣩ
    private double usedPart;    // ������
    private double warnPart = 0.1;  // �������
    private boolean isWarn;   // �Ƿ�Խ����
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
        this.warnMsg = this.isWarn ? "��" : "��";
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
