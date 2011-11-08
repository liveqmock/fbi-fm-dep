package monitor.view;

import monitor.service.DbStatusMngService;
import org.primefaces.component.commandbutton.CommandButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class DeleteLogDetlAction {

    private static Logger logger = LoggerFactory.getLogger(DeleteLogDetlAction.class);
    private String startDate;
    private String endDate;
    private String tablename;

    @ManagedProperty(value = "#{dbStatusMngService}")
    private DbStatusMngService dbStatusMngService;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        tablename = (String) context.getExternalContext().getRequestParameterMap().get("tablename");
    }

    public void onDelete() {
        try {
            int cnt = dbStatusMngService.deleteLogData(tablename, startDate, endDate);
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:delBtn");
                saveBtn.setDisabled(true);
            MessageUtil.addInfo("删除表" + tablename + " " + cnt + "笔记录！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除表" + tablename + "时发生异常！", e.getMessage());
            MessageUtil.addError("删除表" + tablename + "时发生异常！");
        }
    }

    // ========================================================


    public DbStatusMngService getDbStatusMngService() {
        return dbStatusMngService;
    }

    public void setDbStatusMngService(DbStatusMngService dbStatusMngService) {
        this.dbStatusMngService = dbStatusMngService;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}
