package com.mepms.rabbitmq.config;

import com.mepms.entity.NotificationRecord;
import com.mepms.repository.NotificationRecordRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationMessageListener {
    @Autowired NotificationRecordRepository repo;
    @Autowired(required = false) SimpMessagingTemplate wsTemplate; // null if websocket not enabled

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void receiveNotification(NotificationRecord notification) {
        System.out.println("Received via RabbitMQ: " + notification.getTitle());
        // (Optional) Save notification if you want to ensure persistence
        if (notification.getId() == null) {
            repo.save(notification);
        }
        // PUSH to WebSocket for real-time
        if (wsTemplate != null) {
            wsTemplate.convertAndSend("/topic/notifications/" + notification.getUserId(), notification);
        }
        // (Optional) broadcast to admin: wsTemplate.convertAndSend("/topic/notifications/admin", notification);
    }
}
