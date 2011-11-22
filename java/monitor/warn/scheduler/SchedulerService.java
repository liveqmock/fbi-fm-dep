package monitor.warn.scheduler;

import org.quartz.CronExpression;

public interface SchedulerService {
    /**
     * ���� Quartz Cron Expression ��������
     * @param cronExpression  Quartz Cron ���ʽ���� "0/10 * * ? * * *"��
     */
    void schedule(String cronExpression);

    /**
     * ���� Quartz Cron Expression ��������
     * @param name  Quartz CronTrigger����
     * @param cronExpression Quartz Cron ���ʽ���� "0/10 * * ? * * *"��
     */
    void schedule(String name,String cronExpression);

    /**
     * ���� Quartz Cron Expression ��������
     * @param cronExpression Quartz CronExpression
     */
    void schedule(CronExpression cronExpression);

    /**
     * ���� Quartz Cron Expression ��������
     * @param name Quartz CronTrigger����
     * @param cronExpression Quartz CronExpression
     */
    void schedule(String name,CronExpression cronExpression);

}
