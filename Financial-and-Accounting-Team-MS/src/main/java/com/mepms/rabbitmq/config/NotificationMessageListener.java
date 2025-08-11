
package com.mepms.rabbitmq.config;

import com.mepms.entity.NotificationRecord;
import com.mepms.repository.NotificationRecordRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationMessageListener {

    @Autowired
    private NotificationRecordRepository notificationRepo;

    @Autowired(required = false)
    private SimpMessagingTemplate wsTemplate;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void receiveNotification(NotificationRecord notification) {
        System.out.println("Received notification via RabbitMQ: " + notification.getTitle());

        // Save to DB if not already present (only if needed)
        if (notification.getId() == null) {
            notificationRepo.save(notification);
        }

        // Real-time push to WebSocket for user
        if (wsTemplate != null) {
            wsTemplate.convertAndSend("/topic/notifications/" + notification.getUserId(), notification);
        }
    }
}
