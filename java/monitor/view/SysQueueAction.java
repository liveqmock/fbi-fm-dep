package monitor.view;

import monitor.common.BoolType;
import monitor.common.QueueMessage;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.ConnectionViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.web.RemoteJMXBrokerFacade;
import org.apache.activemq.web.config.SystemPropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.management.openmbean.CompositeDataSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: ÏÂÎç2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class SysQueueAction {

    private static Logger logger = LoggerFactory.getLogger(SysQueueAction.class);
    private BrokerViewMBean brokerViewMBean;
    private Collection<ConnectionViewMBean> connViewList;
    private Collection<QueueViewMBean> queueViewList;

    private BoolType boolType = BoolType.TRUE;
    private String queue;
    private List<QueueMessage> queueMsgList;

    @PostConstruct
    public void init() {

        RemoteJMXBrokerFacade createConnector = new RemoteJMXBrokerFacade();
        System.setProperty("webconsole.jmx.url", "service:jmx:rmi:///jndi/rmi://127.0.0.1:1099/jmxrmi");
        //System.setProperty("webconsole.jmx.user", "admin");
        //System.setProperty("webconsole.jmx.password", "activemq");
        SystemPropertiesConfiguration configuration = new SystemPropertiesConfiguration();
        createConnector.setConfiguration(configuration);
        try {
            brokerViewMBean = createConnector.getBrokerAdmin();
            connViewList = createConnector.getConnections();
            queueViewList = createConnector.getQueues();
            FacesContext context = FacesContext.getCurrentInstance();
            queue = (String) context.getExternalContext().getRequestParameterMap().get("queue");
            if (!StringUtils.isEmpty(queue)) {
                 queueMsgList = new ArrayList<QueueMessage>();
                 List<CompositeDataSupport> cdsList = (List<CompositeDataSupport>)createConnector.getQueue(queue.trim()).browseMessages();
                QueueMessage qm = null;
                for (CompositeDataSupport cds : cdsList) {
                     qm = new QueueMessage();
                     qm.setMessageId((String)cds.get("JMSMessageID"));
                     String text = (String)cds.get("Text");
                     qm.setSendTime(text.substring(0, 19));
                     qm.setText(text.substring(19, 40));
                     queueMsgList.add(qm);
                }
            }

        } catch (Exception e) {
        }
    }


    // =============================================================


    public BrokerViewMBean getBrokerViewMBean() {
        return brokerViewMBean;
    }

    public void setBrokerViewMBean(BrokerViewMBean brokerViewMBean) {
        this.brokerViewMBean = brokerViewMBean;
    }

    public Collection<ConnectionViewMBean> getConnViewList() {
        return connViewList;
    }

    public void setConnViewList(Collection<ConnectionViewMBean> connViewList) {
        this.connViewList = connViewList;
    }

    public Collection<QueueViewMBean> getQueueViewList() {
        return queueViewList;
    }

    public void setQueueViewList(Collection<QueueViewMBean> queueViewList) {
        this.queueViewList = queueViewList;
    }

    public BoolType getBoolType() {
        return boolType;
    }

    public void setBoolType(BoolType boolType) {
        this.boolType = boolType;
    }

    public String getBoolStatus(boolean flag) {
        return flag ? "ÊÇ" : "·ñ";
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public List<QueueMessage> getQueueMsgList() {
        return queueMsgList;
    }

    public void setQueueMsgList(List<QueueMessage> queueMsgList) {
        this.queueMsgList = queueMsgList;
    }
}
