package monitor.common;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.*;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.policy.ConstantPendingMessageLimitStrategy;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.management.ObjectName;
import javax.management.QueryExp;
import java.io.IOException;
import java.util.*;

/**
 * ���ActiveMQ�ĸ�����ϢBroker,Connection,Queue,Topic��������ѹջ�ͳ�ջ
 * @author longgangbai
 *
 */
public class ActiveMQMonitor {
	   private static final transient Logger logger = LoggerFactory.getLogger(ActiveMQMonitor.class);
		
	    protected static final int MESSAGE_COUNT = 2000;
	    protected BrokerService brokerService;
	    protected Connection connection;
	    protected String bindAddress ="tcp://localhost:61616";
	    //ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;
	    protected int topicCount;

        /**
         * ��ȡBroker ��AdminView����
         * @return
         * @throws Exception
         */
    	public BrokerViewMBean getBrokerAdmin() throws Exception {
    		return brokerService.getAdminView();
    	}
    	/**
    	 * ��ȡ���е�QueueViewMBean��
    	 * @return
    	 * @throws Exception
    	 */
    	public Collection<QueueViewMBean> getQueues() throws Exception {
    		BrokerViewMBean broker = getBrokerAdmin();
    		if (broker == null) {
    			return Collections.EMPTY_LIST;
    		}
    		ObjectName[] queues = broker.getQueues();
    		return getManagedObjects(queues, QueueViewMBean.class);
    	}
    	
    	
        /**
         * ��ȡ����TopicViewMBean
         * @return
         * @throws Exception
         */
    	public Collection<TopicViewMBean> getTopics() throws Exception {
    		BrokerViewMBean broker = getBrokerAdmin();
    		if (broker == null) {
    			return Collections.EMPTY_LIST;
    		}
    		ObjectName[] queues = broker.getTopics();
    		return getManagedObjects(queues, TopicViewMBean.class);
    	}
        /**
         * ��ȡ����DurableSubscriptionViewMBean
         * @return
         * @throws Exception
         */
    	public Collection<DurableSubscriptionViewMBean> getDurableTopicSubscribers()
    			throws Exception {
    		BrokerViewMBean broker = getBrokerAdmin();
    		if (broker == null) {
    			return Collections.EMPTY_LIST;
    		}
    		ObjectName[] queues = broker.getDurableTopicSubscribers();
    		return getManagedObjects(queues, DurableSubscriptionViewMBean.class);
    	}
        /**
         * ��ȡ����DurableSubscriptionViewMBean
         * @return
         * @throws Exception
         */
    	public Collection<DurableSubscriptionViewMBean> getInactiveDurableTopicSubscribers()
    			throws Exception {
    		BrokerViewMBean broker = getBrokerAdmin();
    		if (broker == null) {
    			return Collections.EMPTY_LIST;
    		}
    		ObjectName[] queues = broker.getInactiveDurableTopicSubscribers();
    		return getManagedObjects(queues, DurableSubscriptionViewMBean.class);
    	}
        /**
         * ����queueName��ȡqueue��ص���Ϣ
         * @return
         * @throws Exception
         */
    	public QueueViewMBean getQueue(String name) throws Exception {
    		return (QueueViewMBean) getDestinationByName(getQueues(), name);
    	}
        /**
         * ����topicName��ȡTopic��ص���Ϣ
         * @return
         * @throws Exception
         */
    	public TopicViewMBean getTopic(String name) throws Exception {
    		return (TopicViewMBean) getDestinationByName(getTopics(), name);
    	}
        /**
         * ��ȡDestinationViewMBean
         * @return
         * @throws Exception
         */
    	protected DestinationViewMBean getDestinationByName(
    			Collection<? extends DestinationViewMBean> collection, String name) {
    		Iterator<? extends DestinationViewMBean> iter = collection.iterator();
    		while (iter.hasNext()) {
    			DestinationViewMBean destinationViewMBean = iter.next();
    			if (name.equals(destinationViewMBean.getName())) {
    				return destinationViewMBean;
    			}
    		}
    		return null;
    	}
        /**
         * ��ȡ����Mananage
         * @return
         * @throws Exception
         */
    	@SuppressWarnings("unchecked")
    	protected <T> Collection<T> getManagedObjects(ObjectName[] names,
    			Class<T> type) throws Exception {
    		List<T> answer = new ArrayList<T>();
    		for (int i = 0; i < names.length; i++) {
    			ObjectName name = names[i];
    			T value = (T) newProxyInstance(name, type, true);
    			if (value != null) {
    				answer.add(value);
    			}
    		}
    		return answer;
    	}
        /**
         * ��ȡ����ConnectionViewMBean
         * @return
         * @throws Exception
         */
    	@SuppressWarnings("unchecked")
    	public Collection<ConnectionViewMBean> getConnections() throws Exception {
    		String brokerName = getBrokerName();
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=Connection,*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		return getManagedObjects(queryResult.toArray(new ObjectName[queryResult
    				.size()]), ConnectionViewMBean.class);
    	}
        /**
         * ��ȡ����Connections
         * @return
         * @throws Exception
         */
    	@SuppressWarnings("unchecked")
    	public Collection<String> getConnections(String connectorName)
    			throws Exception {
    		String brokerName = getBrokerName();
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=Connection,ConnectorName="
    				+ connectorName + ",*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		Collection<String> result = new ArrayList<String>(queryResult.size());
    		for (ObjectName on : queryResult) {
    			String name = StringUtils.replace(on.getKeyProperty("Connection"),
    					"_", ":");
    			result.add(name);
    		}
    		return result;
    	}
        /**
         * ��ȡ����ConnectionViewMBean
         * @return
         * @throws Exception
         */
    	@SuppressWarnings("unchecked")
    	public ConnectionViewMBean getConnection(String connectionName)
    			throws Exception {
    		connectionName = StringUtils.replace(connectionName, ":", "_");
    		String brokerName = getBrokerName();
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=Connection,*,Connection="
    				+ connectionName);
    		Set<ObjectName> queryResult = queryNames(query, null);
    		if (queryResult.size() == 0)
    			return null;
    		ObjectName objectName = queryResult.iterator().next();
    		return (ConnectionViewMBean) newProxyInstance(objectName,
    				ConnectionViewMBean.class, true);
    	}

    	@SuppressWarnings("unchecked")
    	public Collection<String> getConnectors() throws Exception {
    		String brokerName = getBrokerName();
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=Connector,*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		Collection<String> result = new ArrayList<String>(queryResult.size());
    		for (ObjectName on : queryResult)
    			result.add(on.getKeyProperty("ConnectorName"));
    		return result;
    	}

    	public ConnectorViewMBean getConnector(String name) throws Exception {
    		String brokerName = getBrokerName();
    		ObjectName objectName = new ObjectName(
    				"org.apache.activemq:BrokerName=" + brokerName
    						+ ",Type=Connector,ConnectorName=" + name);
    		return (ConnectorViewMBean) newProxyInstance(objectName,
    				ConnectorViewMBean.class, true);
    	}

    	@SuppressWarnings("unchecked")
    	public Collection<NetworkConnectorViewMBean> getNetworkConnectors()
    			throws Exception {
    		String brokerName = getBrokerName();
    		
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=NetworkConnector,*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		return getManagedObjects(queryResult.toArray(new ObjectName[queryResult
    				.size()]), NetworkConnectorViewMBean.class);
    	}

    	public Collection<NetworkBridgeViewMBean> getNetworkBridges()
    			throws Exception {
    		String brokerName = getBrokerName();
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=NetworkBridge,*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		return getManagedObjects(queryResult.toArray(new ObjectName[queryResult
    				.size()]), NetworkBridgeViewMBean.class);
    	}

    	@SuppressWarnings("unchecked")
    	public Collection<SubscriptionViewMBean> getQueueConsumers(String queueName)
    			throws Exception {
    		String brokerName = getBrokerName();
    		queueName = StringUtils.replace(queueName, "\"", "_");
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName
    				+ ",Type=Subscription,destinationType=Queue,destinationName="
    				+ queueName + ",*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		return getManagedObjects(queryResult.toArray(new ObjectName[queryResult
    				.size()]), SubscriptionViewMBean.class);
    	}

    	@SuppressWarnings("unchecked")
    	public Collection<SubscriptionViewMBean> getConsumersOnConnection(
    			String connectionName) throws Exception {
    		connectionName = StringUtils.replace(connectionName, ":", "_");
    		String brokerName = getBrokerName();
    		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
    				+ brokerName + ",Type=Subscription,clientId=" + connectionName
    				+ ",*");
    		Set<ObjectName> queryResult = queryNames(query, null);
    		return getManagedObjects(queryResult.toArray(new ObjectName[queryResult
    				.size()]), SubscriptionViewMBean.class);
    	}
        /**
         * ��ȡ��ʱִ�еĶ��е���Ϣ
         * @return
         * @throws Exception
         */
    	public JobSchedulerViewMBean getJobScheduler() throws Exception {
    		ObjectName name = getBrokerAdmin().getJMSJobScheduler();
    		return (JobSchedulerViewMBean) newProxyInstance(name,
    				JobSchedulerViewMBean.class, true);
    	}

    	public String getBrokerName() throws Exception {
    		return brokerService.getBrokerName();
    	}
    	/**
    	 * ��ȡBroker����
    	 * @return
    	 * @throws Exception
    	 */
    	public Broker getBroker() throws Exception {
    		return brokerService.getBroker();
    	}
    	public ManagementContext getManagementContext() {
    		return brokerService.getManagementContext();
    	}

    	public ManagedRegionBroker getManagedBroker() throws Exception {
    		BrokerView adminView = brokerService.getAdminView();
    		if (adminView == null) {
    			return null;
    		}
    		return adminView.getBroker();
    	}

        public void purgeQueue(ActiveMQDestination destination) throws Exception {
            Set destinations = getManagedBroker().getQueueRegion().getDestinations(destination);
            for (Iterator i = destinations.iterator(); i.hasNext();) {
                Destination dest = (Destination) i.next();
                if (dest instanceof Queue) {
                    Queue regionQueue = (Queue) dest;
                    regionQueue.purge();
                }
            }
        }
        /**
         * 
         * @param name
         * @param query
         * @return
         * @throws Exception
         */
        public Set queryNames(ObjectName name, QueryExp query) throws Exception {
            return getManagementContext().queryNames(name, query);
        }
        /**
         * ͨ��JMX��ȡActiveMQ������Ϣ
         * @param objectName
         * @param interfaceClass
         * @param notificationBroadcaster
         * @return
         */
        public Object newProxyInstance(ObjectName objectName, Class interfaceClass, boolean notificationBroadcaster) {
            return getManagementContext().newProxyInstance(objectName, interfaceClass, notificationBroadcaster);
        }
        /**
         * ����ڴ���Ϣ
         * @throws Exception
         */
        public void monitorMermeryUsage() throws Exception{
        	SystemUsage proSystemUsage=brokerService.getProducerSystemUsage();
        	printSystemUsage(proSystemUsage);
        	SystemUsage syUage=brokerService.getSystemUsage();
        	printSystemUsage(syUage);
        	SystemUsage consumsyUage=brokerService.getConsumerSystemUsage();
        	printSystemUsage(consumsyUage);
        }
        
        /**
         * ��ӡ�ڴ���Ϣ
         * @param syUage
         */
        public void printSystemUsage(SystemUsage syUage){
        	String name=syUage.getName();
        	logger.info("SystemUsage name ="+name);
        	MemoryUsage memeryUsage =syUage.getMemoryUsage();
        	logger.info("memeryUsage PercentUsage name ="+memeryUsage.getPercentUsage());
        	logger.info("memeryUsage Limit name ="+memeryUsage.getLimit());
        	logger.info("memeryUsage Usage name ="+memeryUsage.getUsage());
        	TempUsage tempUsage =syUage.getTempUsage();
        	logger.info("tempUsage PercentUsage name ="+tempUsage.getPercentUsage());
        	logger.info("tempUsage Limit name ="+tempUsage.getLimit());
        	logger.info("tempUsage Usage name ="+tempUsage.getUsage());
        	StoreUsage storeUsage=syUage.getStoreUsage();
        	logger.info("storeUsage PercentUsage name ="+storeUsage.getPercentUsage());
        	logger.info("storeUsage Limit name ="+storeUsage.getLimit());
        	logger.info("storeUsage Usage name ="+storeUsage.getUsage());
        }
	    /**
	     * �����Ϣ�ķ���
	     * @throws Exception
	     */
	    public void monitorQueueAndTopic() throws Exception{
	    	logger.info("==========Connection =================");
	    	Collection<ConnectionViewMBean> conVBean=getConnections();
	    	for (ConnectionViewMBean bean : conVBean) {
	    		logger.info("remoteAddress:"+bean.getRemoteAddress());
	    		logger.info("isActive:"+bean.isActive());
	    		logger.info("isConnected:"+bean.isConnected());
			}
	    	logger.info("=============Topic =================");
	    	Collection<TopicViewMBean>  topicVBean=getTopics();
	    	for (TopicViewMBean topicbean : topicVBean) {
	    		logger.info("beanName ="+topicbean.getName());
	    		logger.info("ConsumerCount ="+topicbean.getConsumerCount());
	    		logger.info("DequeueCount ="+topicbean.getDequeueCount());
	    		logger.info("EnqueueCount ="+topicbean.getEnqueueCount());
	    		logger.info("DispatchCount ="+topicbean.getDispatchCount());
	    		logger.info("ExpiredCount ="+topicbean.getExpiredCount());
	    		logger.info("MaxEnqueueTime ="+topicbean.getMaxEnqueueTime());
	    		logger.info("ProducerCount ="+topicbean.getProducerCount());
	    		logger.info("MemoryPercentUsage ="+topicbean.getMemoryPercentUsage());
	    		logger.info("MemoryLimit ="+topicbean.getMemoryLimit());
			}
	    	logger.info("============Queue===================");
	    	Collection<QueueViewMBean> queuqVBeanList=getQueues();
	      	for (QueueViewMBean queuebean : queuqVBeanList) {
	    		logger.info(" queue beanName ="+queuebean.getName());
	    		logger.info("ConsumerCount ="+queuebean.getConsumerCount());
	    		logger.info("DequeueCount ="+queuebean.getDequeueCount());
	    		logger.info("EnqueueCount ="+queuebean.getEnqueueCount());
	    		logger.info("DispatchCount ="+queuebean.getDispatchCount());
	    		logger.info("ExpiredCount ="+queuebean.getExpiredCount());
	    		logger.info("MaxEnqueueTime ="+queuebean.getMaxEnqueueTime());
	    		logger.info("ProducerCount ="+queuebean.getProducerCount());
	    		logger.info("MemoryPercentUsage ="+queuebean.getMemoryPercentUsage());
	    		logger.info("MemoryLimit ="+queuebean.getMemoryLimit());
			}
	    }
	    
	    public void test() throws Exception{
	       	//��ȡ��ʼ����Ϣ
	    	init();
	    	for (int i = 0; i < 10; i++) {
	    		sendTopic(i);
			}
	    	for (int i = 0; i < 10; i++) {
	    		sendPS(i);
			}
            monitorQueueAndTopic();	    	
	    	Thread.sleep(5000);
            receiveTopic();
	    	receivePS();
	    }
	    /**
	     * P2P���ͷ�ʽ
	     * @throws JMSException
	     */
	    public void sendTopic(int i) throws JMSException{
	    	Session session=connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
	    	Destination topic=session.createQueue("activemq.queue"+i);
	    	MessageProducer productor=(MessageProducer) session.createProducer(topic);
	    	TextMessage txtMessage =session.createTextMessage();
	    	txtMessage.setText("this is a topic message "+i);
	    	productor.send(txtMessage);
	    }
	    /**
	     * Sub/Pub���ͷ�ʽ
	     * @throws JMSException
	     */
	    public void sendPS(int i) throws JMSException{
	    	Session session=connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
	    	Destination topic=session.createTopic("activemq.topic"+i);
	    	MessageProducer productor=(MessageProducer) session.createProducer(topic);
	    	TextMessage txtMessage =session.createTextMessage();
	    	txtMessage.setText("this is a topic message "+i);
	    	productor.send(txtMessage);
	    }
	    
	    /**
	     * P2P���ܷ�ʽ
	     * @throws JMSException
	     */
	    public void receiveTopic() throws JMSException{
	    	Session session=connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
	    	Destination topic=session.createQueue("activemq.queue");
	    	
	    	MessageConsumer consumer=(MessageConsumer) session.createConsumer(topic);
	    	TextMessage txtMessage =(TextMessage)consumer.receive();
	    	System.out.println("txtMessage ="+txtMessage.getText());
	    }
	    /**
	     * Sub/Pub���ܷ�ʽ
	     * @throws JMSException
	     */
	    public void receivePS() throws JMSException{
	       	Session session=connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
	    	Destination topic=session.createQueue("activemq.topic");
	    	
	    	MessageConsumer consumer=(MessageConsumer) session.createConsumer(topic);
	    	TextMessage txtMessage =(TextMessage)consumer.receive();
	    	System.out.println("txtMessage ="+txtMessage.getText());
	    }
	    
	    
	    /**
	     * ��ʼ����Ϣ�ķ���
	     * @throws Exception
	     */
	    protected void init() throws Exception {
	        if (brokerService == null) {
	            brokerService = createBroker();
	        }
	        ActiveMQConnectionFactory factory = createConnectionFactory();
	        connection = factory.createConnection();
	        //���Connection ��״̬��صķ���
	        monitorConnection(connection);
	        //��������
	        connection.start();
	    }
	    
	    /**
	     * ���̨ActiveMQConnection��״̬�ķ���
	     * @param connection
	     */
	    public void monitorConnection(Connection connection){
	    	ActiveMQConnection activemqconnection =(ActiveMQConnection)connection;
	    	//���ActiveMQConnection�ļ�����
	    	activemqconnection.addTransportListener(new TransportListener(){

				public void onCommand(Object object) {
					logger.info("onCommand  object "+object);
					
				}

				public void onException(IOException ex) {
					logger.info("onException ="+ex.getMessage());
				}

				public void transportInterupted() {
					logger.info("transportInterupted =");
				}

				public void transportResumed() {
					logger.info("transportResumed .........");					
				}
	    		
	    	});
	    }

	    protected void destory() throws Exception {
	        connection.close();
	        if (brokerService != null) {
	            brokerService.stop();
	        }
	    }
        /**
         * ����ActiveMQConnectionFactory
         * @return
         * @throws Exception
         */
	    protected ActiveMQConnectionFactory createConnectionFactory()
	            throws Exception {
	        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
	                bindAddress);
	        return cf;
	    }

	    /***
	     * ����һ��Broker��������
	     * @return
	     * @throws Exception
	     */
	    protected BrokerService createBroker() throws Exception {
	    	//����BrokerService����
	        BrokerService answer = new BrokerService();
	        //���ü�����ص���Ϣ
	        configureBroker(answer);
	        //����Broker������
	        answer.start();
	        
	        return answer;
	    }

	    /**
	     * ����Broker
	     * @param answer
	     * @throws Exception
	     */
	    protected void configureBroker(BrokerService answer) throws Exception {
	        //�����־û���Ϣ
	    	answer.setPersistent(false);
	    	//���ò���JMX����
	    	answer.setUseJmx(true);
	        ConstantPendingMessageLimitStrategy strategy = new ConstantPendingMessageLimitStrategy();
	        strategy.setLimit(10);
	        PolicyEntry tempQueueEntry = createPolicyEntry(strategy);
	        tempQueueEntry.setTempQueue(true);
	        PolicyEntry tempTopicEntry = createPolicyEntry(strategy);
	        tempTopicEntry.setTempTopic(true);
	        PolicyMap pMap = new PolicyMap();
	        final List<PolicyEntry> policyEntries = new ArrayList<PolicyEntry>();
	        policyEntries.add(tempQueueEntry);
	        policyEntries.add(tempTopicEntry);
	        pMap.setPolicyEntries(policyEntries);
	        answer.setDestinationPolicy(pMap);
	        //��url
	        answer.addConnector(bindAddress);
	        answer.setDeleteAllMessagesOnStartup(true);
	    }
        /**
         * ����һ�����ò���
         * @param strategy
         * @return
         */
	    private PolicyEntry createPolicyEntry(ConstantPendingMessageLimitStrategy strategy) {
	        PolicyEntry policy = new PolicyEntry();
	        policy.setAdvisdoryForFastProducers(true);
	        policy.setAdvisoryForConsumed(true);
	        policy.setAdvisoryForDelivery(true);
	        policy.setAdvisoryForDiscardingMessages(true);
	        policy.setAdvisoryForSlowConsumers(true);
	        policy.setAdvisoryWhenFull(true);
	        policy.setProducerFlowControl(false);
	        policy.setPendingMessageLimitStrategy(strategy);
	        return policy;
	    }
	    
	    public void object2string(Object object ){
	    	ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE);
	    }
	    
}