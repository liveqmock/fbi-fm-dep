package skyline.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;
import skyline.repository.dao.*;
import skyline.repository.model.Ptenudetail;
import skyline.repository.model.PtenudetailExample;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ƽ̨����
 * User: zhanrui
 * Date: 11-4-5
 * Time: ����7:42
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
            throw new RuntimeException("�û�δ��¼��");
        }
        return om;
    }

    /**
     * ����ָ��ö���嵥
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
     * ��ȡö�ٱ���ĳһ��� ��չ����ֵ
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
     * ���� ö��ֵ����չֵ�Ķ�Ӧ��ϵ MAP
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
