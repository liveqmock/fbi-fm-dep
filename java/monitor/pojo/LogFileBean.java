package monitor.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-7
 * Time: ÏÂÎç4:08
 * To change this template use File | Settings | File Templates.
 */
public class LogFileBean {

    private String fileName;
    private String fileSize;

    public LogFileBean() {}

    public LogFileBean(String fileName) {
        this.fileName = fileName;
    }

    public LogFileBean(String fileName, String fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
