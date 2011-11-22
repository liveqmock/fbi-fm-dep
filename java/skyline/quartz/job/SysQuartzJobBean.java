package skyline.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SysQuartzJobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        Trigger trigger = jobexecutioncontext.getTrigger();
        System.out.println("============== 业务" + trigger.getJobName() +"执行过程====== 上次执行时间" + trigger.getPreviousFireTime() + "======下次执行时间" + trigger.getNextFireTime());
    }
}
