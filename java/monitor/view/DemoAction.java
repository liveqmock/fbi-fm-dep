package monitor.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: ÏÂÎç2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class DemoAction {

    private static Logger logger = LoggerFactory.getLogger(DemoAction.class);

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
    }
}
