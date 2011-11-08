package skyline.service;

import monitor.pojo.LogFileBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;
import skyline.repository.dao.*;
import skyline.repository.model.Ptenudetail;
import skyline.repository.model.PtenudetailExample;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台表处理
 * User: zhanrui
 * Date: 11-4-5
 * Time: 下午7:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PlatformService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PtoperMapper ptoperMapper;
    @Autowired
    private PtdeptMapper ptdeptMapper;

    @Autowired
    private PtenudetailMapper enudetailMapper;

    @Autowired
    private PtmenuMapper menuMapper;


    @Autowired
    private SysSchedulerLogMapper sysSchedulerLogMapper;

    public static OperatorManager getOperatorManager() {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("用户未登录！");
        }
        return om;
    }

    /*
    dep.logfile.info=D:/dep/log/dep.log
dep.logfile.error=D:/dep/log/error.log
dep.logfile.platform=D:/dep/log/platform.log
     */
    public LogFileBean getLogFolderStatus() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.path");
        File logPath = new File(filePath);
        LogFileBean logFileBean = new LogFileBean(filePath, String.format("%.2fK", getDirSize(logPath) * 1.0 / 1024));
        return logFileBean;
    }

    public LogFileBean getInfoLogFileSize() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.info");
        return getFileSize(filePath);
    }

    public LogFileBean getErrorLogFileSize() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.error");
        return getFileSize(filePath);
    }

    public LogFileBean getPlatformLogFileSize() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.platform");
        return getFileSize(filePath);
    }

    private LogFileBean getFileSize(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            long filesize = fis.available();
            LogFileBean logFileBean = new LogFileBean(filePath, String.format("%.2fK", filesize * 1.0 / 1024));
            fis.close();
            return logFileBean;
        }
        return new LogFileBean("文件不存在", "0M");
    }

    private static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
            }
        }
        return dirSize;
    }

    // skyline.logfile.path=D:/dep/log/
    public List<LogFileBean> getLogFiles() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.path");
        File file = new File(filePath);
        File[] logfiles = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("dep.log.");
            }
        });
        List<LogFileBean> logFileBeans = new ArrayList<LogFileBean>();
        for (File logfile : logfiles) {
            logFileBeans.add(getFileSize(logfile.getPath()));
        }

        return logFileBeans;
    }


    // skyline.logfile.path=D:/dep/log/
    public List<LogFileBean> getErrorLogFiles() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.path");
        File file = new File(filePath);
        File[] logfiles = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("error.log.");
            }
        });
        List<LogFileBean> logFileBeans = new ArrayList<LogFileBean>();
        for (File logfile : logfiles) {
            logFileBeans.add(getFileSize(logfile.getPath()));
        }

        return logFileBeans;
    }

    /**
     * 查找指定枚举清单
     *
     * @param enuid
     * @return
     */
    public List<Ptenudetail> selectEnuDetail(String enuid) {
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuid);
        example.setOrderByClause(" dispno ");
        return enudetailMapper.selectByExample(example);
    }


    /**
     * 获取枚举表中某一项的 扩展定义值
     *
     * @param enuType
     * @param enuItemValue
     * @return
     */
    public String selectEnuExpandValue(String enuType, String enuItemValue) {
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType).andEnuitemvalueEqualTo(enuItemValue);
        return enudetailMapper.selectByExample(example).get(0).getEnuitemexpand();
    }

    /**
     * 检索 枚举值和扩展值的对应关系 MAP
     *
     * @param enuType
     * @return
     */
    public Map<String, String> selectEnuItemValueToVLMap(String enuType) {
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType);
        List<Ptenudetail> records = enudetailMapper.selectByExample(example);
        Map<String, String> enuMap = new HashMap<String, String>();
        for (Ptenudetail record : records) {
            enuMap.put(record.getEnuitemvalue(), record.getEnuitemlabel());
        }
        return enuMap;
    }

}
