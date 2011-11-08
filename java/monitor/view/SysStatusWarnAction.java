package monitor.view;

import dep.common.BoolType;
import monitor.pojo.SystemElmtBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.OperatingSystemService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: ����2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class SysStatusWarnAction {

    private static Logger logger = LoggerFactory.getLogger(SysStatusWarnAction.class);

    private List<SystemElmtBean> elmtBeans;
    private List<String> warningList;

    @PostConstruct
    public void init() {
        try {
            elmtBeans = OperatingSystemService.getAllSystemElmts();
            initWarnings();
        } catch (Exception e) {
            logger.error("ϵͳ״̬����쳣��", e.getMessage());
            MessageUtil.addError("ϵͳ״̬����쳣��");
            elmtBeans = new ArrayList<SystemElmtBean>();
        }
    }

    public void initWarnings() {

        warningList = new ArrayList<String>();
            for (SystemElmtBean elmt : elmtBeans) {
                if (BoolType.TRUE.getCode().equals(elmt.getWarn().getCode())) {
                    warningList.add(elmt.getElmtName() + "�����ʹ��ߣ����ñ�����" + String.format("%.2f", elmt.getUsedPart() * 100) + "%");
                }
            }
            if (warningList.isEmpty()) {
                warningList.add("����Ԥ����Ϣ������������������");
            }
    }
    //============================================================

    public List<SystemElmtBean> getElmtBeans
            () {
        return elmtBeans;
    }

    public void setElmtBeans(List<SystemElmtBean> elmtBeans) {
        this.elmtBeans = elmtBeans;
    }

    public List<String> getWarningList() {
        return warningList;
    }

    public void setWarningList(List<String> warningList) {
        this.warningList = warningList;
    }
}
