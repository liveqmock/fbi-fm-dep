package monitor.warn.scheduler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
        SchedulerService schedulerService = (SchedulerService)springContext.getBean("schedulerService");
        //ִ��ҵ���߼�...

        //���õ�������
        //ÿ10����ִ�е���һ��
        schedulerService.schedule("0/5 * * ? * * *");

       // Date startTime = parse("2011-11-21 16:00:00");

        //��ʼִ�е���
       // schedulerService.schedule(startTime);

         schedulerService.schedule("0/15 * * ? * * *");
        //startTime = parse("2011-11-22 10:00:00");
        // schedulerService.schedule(startTime);

        //2009-06-01 21:50:00��ʼִ�е��ȣ�2009-06-01 21:55:00����ִ�е���
        //schedulerService.schedule(startTime,endTime);

        //2009-06-01 21:50:00��ʼִ�е��ȣ�ִ��5�ν���
        //schedulerService.schedule(startTime,null,5);

        //2009-06-01 21:50:00��ʼִ�е��ȣ�ÿ��20��ִ��һ�Σ�ִ��5�ν���
        //schedulerService.schedule(startTime,null,5,20);
    }

    private static Date parse(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
