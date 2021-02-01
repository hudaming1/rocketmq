package org.hum.test;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

public class ProducerTest {

	private static class OrderStep implements Serializable {
		private static final long serialVersionUID = 1L;
		public OrderStep() {
		}
		public OrderStep(Long orderId) {
			this.orderId = orderId;
		}

		private Long orderId;

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
	}

	private static List<OrderStep> generateMessages() {
		List<OrderStep> orderStep = new ArrayList<ProducerTest.OrderStep>();
		orderStep.add(new OrderStep(1L));
		orderStep.add(new OrderStep(2L));
		orderStep.add(new OrderStep(3L));
		return orderStep;
	}

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("producerGroup1");
		producer.setNamesrvAddr("127.0.0.1:9876");

		producer.start();

		String[] tags = new String[] { "*" };

		// 订单列表
		List<OrderStep> orderList = generateMessages();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		for (int i = 0; i < 10; i++) {
			// 加个时间前缀
			String body = dateStr + " Hello RocketMQ " + orderList.get(i);
			Message msg = new Message("huming0131", tags[i % tags.length], "KEY" + i, body.getBytes());

			SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					Long id = (Long) arg; // 根据订单id选择发送queue
					long index = id % mqs.size();
					return mqs.get((int) index);
				}
			}, orderList.get(i).getOrderId());// 订单id

			System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s", sendResult.getSendStatus(),
					sendResult.getMessageQueue().getQueueId(), body));
		}
	}
}
