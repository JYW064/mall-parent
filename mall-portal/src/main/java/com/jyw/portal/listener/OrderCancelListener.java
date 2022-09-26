package com.jyw.portal.listener;


import com.jyw.portal.service.impl.OrderServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RabbitListener(queues = "xmall.order.cancel.queue")//监听的队列名称
public class OrderCancelListener {
    @Autowired
    OrderServiceImpl orderService;


    @RabbitHandler
    public void cancelOrder(String orderId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        channel.basicAck(tag, false);
        log.info("延迟队列收到消息");
        orderService.updateOrderStatus(orderId,0);
    }
}
