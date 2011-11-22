package skyline.quartz.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.quartz.common.JobStatusType;
import skyline.quartz.repository.model.QrtzTrigerJob;
import skyline.quartz.service.QuartzScheduleService;
import skyline.quartz.service.SchedulerService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class QuartzMngAction {

    private static Logger logger = LoggerFactory.getLogger(QuartzMngAction.class);

    @ManagedProperty(value = "#{quartzScheduleService}")
    private QuartzScheduleService quartzScheduleService;
    @ManagedProperty(value = "#{schedulerService}")
    private SchedulerService schedulerService;

    private List<QrtzTrigerJob> qrtzTrigerJobs;
    private QrtzTrigerJob[] selectedRecords;


    @PostConstruct
    public void init() {
        qrtzTrigerJobs = quartzScheduleService.qryAllTrigerJobs();
    }

    public void startQuartz() {
       // schedulerService.schedule(null, "0/10 * * * * ?");
        qrtzTrigerJobs = quartzScheduleService.qryAllTrigerJobs();
    }

    public void pauseRecords() {
        if(selectedRecords == null || selectedRecords.length < 1) {
            MessageUtil.addWarn("至少选择一条记录！");
            return;
        }
        try {
            for(QrtzTrigerJob job : selectedRecords) {
                if(JobStatusType.ACQUIRED.getCode().equals(job.getTrigState())) {
                schedulerService.pauseTrigger(job.getTrigName(), job.getTrigGroup());
                }
            }
            MessageUtil.addInfo("所选运行中作业已暂停！");
            qrtzTrigerJobs = quartzScheduleService.qryAllTrigerJobs();
        } catch (Exception e) {
            MessageUtil.addError("操作失败！");
            logger.info("操作失败！" + e.getMessage());
        }
    }


    public void resumeRecords() {
        if(selectedRecords == null || selectedRecords.length < 1) {
            MessageUtil.addWarn("至少选择一条记录！");
            return;
        }
        try {
            for(QrtzTrigerJob job : selectedRecords) {
                if(!JobStatusType.ACQUIRED.getCode().equals(job.getTrigState())) {
                schedulerService.resumeTrigger(job.getTrigName(), job.getTrigGroup());
                }
            }
            MessageUtil.addInfo("所选未运行中作业已启动！");
            qrtzTrigerJobs = quartzScheduleService.qryAllTrigerJobs();
        } catch (Exception e) {
            MessageUtil.addError("操作失败！");
            logger.info("操作失败！" + e.getMessage());
        }
    }

    public void deleteRecords() {
        if(selectedRecords == null || selectedRecords.length < 1) {
            MessageUtil.addWarn("至少选择一条记录！");
            return;
        }
        try {
            for(QrtzTrigerJob job : selectedRecords) {
                schedulerService.removeTrigdger(job.getTrigName(), job.getTrigGroup());
            }
            MessageUtil.addInfo("所选记录删除成功！");
            qrtzTrigerJobs = quartzScheduleService.qryAllTrigerJobs();
        } catch (Exception e) {
            MessageUtil.addError("操作失败！");
            logger.info("操作失败！" + e.getMessage());
        }
    }

    // ========================================================


    public QrtzTrigerJob[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(QrtzTrigerJob[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public List<QrtzTrigerJob> getQrtzTrigerJobs() {
        return qrtzTrigerJobs;
    }

    public void setQrtzTrigerJobs(List<QrtzTrigerJob> qrtzTrigerJobs) {
        this.qrtzTrigerJobs = qrtzTrigerJobs;
    }

    public QuartzScheduleService getQuartzScheduleService() {
        return quartzScheduleService;
    }

    public void setQuartzScheduleService(QuartzScheduleService quartzScheduleService) {
        this.quartzScheduleService = quartzScheduleService;
    }

    public SchedulerService getSchedulerService() {
        return schedulerService;
    }

    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }
}
