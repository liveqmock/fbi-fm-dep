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
import java.io.IOException;
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
    public LogFileBean getInfoLogFileSize() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.info");
        return getFileMSize(filePath);
    }

    public LogFileBean getErrorLogFileSize() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.error");
        return getFileMSize(filePath);
    }

    public LogFileBean getPlatformLogFileSize() throws IOException {
        String filePath = PropertyManager.getProperty("skyline.logfile.platform");
        return getFileMSize(filePath);
    }

    private LogFileBean getFileMSize(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            return new LogFileBean(filePath, fis.available() / 1024 / 1024 + "M");
        }
        return new LogFileBean("文件不存在", "0M");
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
