package monitor.warn.scheduler;

import org.quartz.CronExpression;

public interface SchedulerService {
    /**
     * 根据 Quartz Cron Expression 调试任务
     * @param cronExpression  Quartz Cron 表达式，如 "0/10 * * ? * * *"等
     */
    void schedule(String cronExpression);

    /**
     * 根据 Quartz Cron Expression 调试任务
     * @param name  Quartz CronTrigger名称
     * @param cronExpression Quartz Cron 表达式，如 "0/10 * * ? * * *"等
     */
    void schedule(String name,String cronExpression);

    /**
     * 根据 Quartz Cron Expression 调试任务
     * @param cronExpression Quartz CronExpression
     */
    void schedule(CronExpression cronExpression);

    /**
     * 根据 Quartz Cron Expression 调试任务
     * @param name Quartz CronTrigger名称
     * @param cronExpression Quartz CronExpression
     */
    void schedule(String name,CronExpression cronExpression);

}
