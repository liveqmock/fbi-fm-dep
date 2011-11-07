package dep;

import dep.service.FrameworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DEPLauncher {

    private static final Logger logger = LoggerFactory.getLogger(DEPLauncher.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        FrameworkService.init();
        logger.info("====== DEP系统初始化成功==========");
    }
}