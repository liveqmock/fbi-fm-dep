package skyline.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class CmdExecuteThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(CmdExecuteThread.class);
    private String cmd;
    private boolean isOk;

    public CmdExecuteThread(String cmd) {
        this.cmd = cmd;
        isOk = false;
    }

    @Override
    public void run() {
        try {
            executeCmd(cmd);
            isOk = true;
        } catch (Exception e) {
            logger.error("脚本执行失败！", "脚本：" + cmd + e.getMessage());
            isOk = false;
        }
    }

    // 执行bat脚本
    public void executeCmd(String cmd) throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec(cmd);
        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        while ((line = input.readLine()) != null)
            logger.info(line);
        process.waitFor();
    }

    public boolean isOk() {
        return isOk;
    }
}
