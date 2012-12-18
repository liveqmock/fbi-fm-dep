package monitor.warn.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.stereotype.Service;
import pub.platform.advance.utils.PropertyManager;

import javax.jms.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-18
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CamelService {

    public void startForShort() throws Exception {
        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(PropertyManager.getProperty("dep.activemq.brokerurl"));
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.addRoutes(new RouteBuilder() {

            public void configure() {
                from("jms:" + PropertyManager.getProperty("dep.queue.warn.mail"))
                        .setHeader("to", constant("boluo_1314@163.com"))
                        .setHeader("from", constant("boluo_1314@163.com"))
                        .setHeader("contentType", constant("text/html;charset=gb2312"))
                        .setHeader("subject", constant("数据交换平台监控系统预警信息"))
                        .to("smtp://smtp.163.com?password=boluo1314&username=boluo_1314");
            }
        });
        context.start();
        Thread.sleep(20000);   // 增加执行时间
        context.stop();
    }
}
