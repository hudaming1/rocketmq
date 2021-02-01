package org.hum.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.rocketmq.common.namesrv.NamesrvConfig;
import org.apache.rocketmq.namesrv.NamesrvController;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;

public class NameServerTest {

	public static void main(String[] args) throws Exception {
		NamesrvController nameSrvController = null;
        NettyServerConfig nettyServerConfig = new NettyServerConfig();
        NamesrvConfig namesrvConfig = new NamesrvConfig();
        nettyServerConfig.setListenPort(9876);
        nameSrvController = new NamesrvController(namesrvConfig, nettyServerConfig);
        boolean initResult = nameSrvController.initialize();
        assertThat(initResult).isTrue();
        nameSrvController.start();
	}
}
