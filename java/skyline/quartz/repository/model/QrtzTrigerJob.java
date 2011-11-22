package skyline.quartz.repository.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-22
 * Time: ÏÂÎç1:35
 * To change this template use File | Settings | File Templates.
 */
public class QrtzTrigerJob {

    private String trigName;
    private String trigGroup;
    private String jobName;
    private String jobClassName;
    private String jobDescription;
    private String prevFireTime;
    private String nextFireTime;
    private String trigState;

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public String getTrigGroup() {
        return trigGroup;
    }

    public void setTrigGroup(String trigGroup) {
        this.trigGroup = trigGroup;
    }

    public String getTrigName() {
        return trigName;
    }

    public void setTrigName(String trigName) {
        this.trigName = trigName;
    }

    public String getTrigState() {
        return trigState;
    }

    public void setTrigState(String trigState) {
        this.trigState = trigState;
    }
}
