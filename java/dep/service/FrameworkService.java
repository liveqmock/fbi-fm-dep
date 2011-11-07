package dep.service;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class FrameworkService {

    private static final Logger logger = LoggerFactory.getLogger(FrameworkService.class);
    private static ApplicationContext context;

    private FrameworkService() {
    }

    public static void init() {

        logger.info("...DEP系统初始化开始.......");
        context = new ClassPathXmlApplicationContext(new String[]{"dep/resources/applicationContext.xml"});
    }

    public static Object getBean(String key) {
        if (context == null) {
            init();
        }
        return context.getBean(key);
    }

}
