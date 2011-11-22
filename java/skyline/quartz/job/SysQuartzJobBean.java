package skyline.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SysQuartzJobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        Trigger trigger = jobexecutioncontext.getTrigger();
        System.out.println("============== ҵ��" + trigger.getJobName() +"ִ�й���====== �ϴ�ִ��ʱ��" + trigger.getPreviousFireTime() + "======�´�ִ��ʱ��" + trigger.getNextFireTime());
    }
}
