package monitor.warn.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SysQuartzJobBean extends QuartzJobBean {

    private SimpleService simpleService;

    public void setSimpleService(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        Trigger trigger = jobexecutioncontext.getTrigger();
        simpleService.testMethod("============== µ±Ç°CronExpression £º " + trigger.getPreviousFireTime() + "======" + trigger.getNextFireTime());
    }

}
