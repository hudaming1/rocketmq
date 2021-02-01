package org.hum.test;

import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.apache.rocketmq.store.config.MessageStoreConfig;

public class BrokerTest {

	public static void main(String[] args) throws Exception {
		BrokerConfig brokerConfig = new BrokerConfig();
//		brokerConfig.setAutoCreateTopicEnable(true);
//		brokerConfig.setAutoCreateSubscriptionGroup(true);
		brokerConfig.setNamesrvAddr("localhost:9876");
//		brokerConfig.setClusterTopicEnable(true);
//		brokerConfig.setBrokerClusterName("cluster1");
//		brokerConfig.setBrokerName("broker1");
//		brokerConfig.setBrokerId(0L);
		BrokerController brokerController = new BrokerController(brokerConfig, new NettyServerConfig(), new NettyClientConfig(), new MessageStoreConfig());
		brokerController.initialize();
		brokerController.start();
		brokerController.registerBrokerAll(true, false, true);
		System.out.println(brokerController.getBrokerConfig().isAutoCreateTopicEnable());
		System.out.println("start");
	}
}
