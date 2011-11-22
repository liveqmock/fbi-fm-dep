package skyline.quartz.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired @Qualifier("scheduler")
    private Scheduler scheduler;

    /*@Autowired @Qualifier("jobDetail")
    private JobDetailBean jobDetail;*/

/*
    public void schedule(String name, String cronExpression) {
        if (name == null || name.trim().equals("")) {
            name = UUID.randomUUID().toString();
        }
        try {
            scheduler.addJob(jobDetail, true);
            CronTrigger cronTrigger = new CronTrigger(name, Scheduler.DEFAULT_GROUP, jobDetail.getName(),
                    Scheduler.DEFAULT_GROUP);
            cronTrigger.setCronExpression(new CronExpression(cronExpression));
            scheduler.scheduleJob(cronTrigger);

            scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP, cronTrigger);
            //removeTrigdger(name, Scheduler.DEFAULT_GROUP);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
*/
	public void pauseTrigger(String triggerName,String group){
		try {
			scheduler.pauseTrigger(triggerName, group);//Í£Ö¹´¥·¢Æ÷
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void resumeTrigger(String triggerName,String group){
		try {
			//Trigger trigger = scheduler.getTrigger(triggerName, group);

			scheduler.resumeTrigger(triggerName, group);//ÖØÆô´¥·¢Æ÷
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean removeTrigdger(String triggerName,String group){
		try {
			scheduler.pauseTrigger(triggerName, group);//Í£Ö¹´¥·¢Æ÷
			return scheduler.unscheduleJob(triggerName, group);//ÒÆ³ý´¥·¢Æ÷
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
