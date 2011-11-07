package monitor.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: обнГ2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class DemoAction {

    private static Logger logger = LoggerFactory.getLogger(DemoAction.class);

    @PostConstruct
    public void init() {

    }
}
