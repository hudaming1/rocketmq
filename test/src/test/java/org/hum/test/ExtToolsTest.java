package org.hum.test;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExtImpl;

public class ExtToolsTest {

	public static void main(String[] args) throws MQClientException, InterruptedException, MQBrokerException, RemotingException {
		DefaultMQAdminExt adminExt = new DefaultMQAdminExt();
		adminExt.setNamesrvAddr("localhost:9876");
		DefaultMQAdminExtImpl defaultMQAdminExtImpl = new DefaultMQAdminExtImpl(adminExt, 1000);
		defaultMQAdminExtImpl.start();
		defaultMQAdminExtImpl.createTopic("", "huming0131", 4);
		
		System.out.println(defaultMQAdminExtImpl.getTopicClusterList("huming0131"));
	}
}
