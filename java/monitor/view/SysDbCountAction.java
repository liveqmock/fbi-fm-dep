package monitor.view;

import dep.common.BoolType;
import monitor.repository.model.DbTableBean;
import monitor.service.DbStatusMngService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class SysDbCountAction {

    private static Logger logger = LoggerFactory.getLogger(SysDbCountAction.class);

    private List<DbTableBean> tableBeanList;
    private List<String> warningList;

    @ManagedProperty(value = "#{dbStatusMngService}")
    private DbStatusMngService dbStatusMngService;
    @PostConstruct
    public void init() {
          tableBeanList = dbStatusMngService.qrySyslogTblStatus();
          initWarnings();
    }

    public void initWarnings() {

        warningList = new ArrayList<String>();
            for (DbTableBean tableBean : tableBeanList) {
                if (BoolType.TRUE.getCode().equals(tableBean.getHuge().getCode())) {
                    warningList.add(tableBean.getTblname() + "���¼�����࣡����ɾ���������ݣ�");
                }
            }
            if (warningList.isEmpty()) {
                warningList.add("���ݿ�����������������Ԥ����Ϣ��");
            }
    }

    // ========================================================


    public List<String> getWarningList() {
        return warningList;
    }

    public void setWarningList(List<String> warningList) {
        this.warningList = warningList;
    }

    public DbStatusMngService getDbStatusMngService() {
        return dbStatusMngService;
    }

    public void setDbStatusMngService(DbStatusMngService dbStatusMngService) {
        this.dbStatusMngService = dbStatusMngService;
    }

    public List<DbTableBean> getTableBeanList() {
        return tableBeanList;
    }

    public void setTableBeanList(List<DbTableBean> tableBeanList) {
        this.tableBeanList = tableBeanList;
    }
}
