package monitor.warn.scheduler;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.UUID;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired @Qualifier("scheduler")
    private Scheduler scheduler;

    @Autowired @Qualifier("jobDetail")
    private JobDetail jobDetail;

    private static String nowConExpression;

    @Override
    public void schedule(String cronExpression) {
        schedule(null, cronExpression);
    }

    @Override
    public void schedule(String name, String cronExpression) {
        try {
            schedule(name, new CronExpression(cronExpression));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void schedule(CronExpression cronExpression) {
        schedule(null, cronExpression);
    }

    @Override
    public void schedule(String name, CronExpression cronExpression) {
        if (name == null || name.trim().equals("")) {
            name = UUID.randomUUID().toString();
        }
        System.out.println("=============================== nowCronExpression : " + nowConExpression);
        if(this.nowConExpression != null) {
            try {
                this.scheduler.pauseTrigger(nowConExpression, Scheduler.DEFAULT_GROUP);
                this.scheduler.unscheduleJob(nowConExpression, Scheduler.DEFAULT_GROUP);
            } catch (SchedulerException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        this.nowConExpression = cronExpression.getCronExpression();

        try {
            scheduler.addJob(jobDetail, true);
            CronTrigger cronTrigger = new CronTrigger(name, Scheduler.DEFAULT_GROUP, jobDetail.getName(),
                    Scheduler.DEFAULT_GROUP);
            cronTrigger.setCronExpression(cronExpression);
            scheduler.scheduleJob(cronTrigger);

            scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP, cronTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
