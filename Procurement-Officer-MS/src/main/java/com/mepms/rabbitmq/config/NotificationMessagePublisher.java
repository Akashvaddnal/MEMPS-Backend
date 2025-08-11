package com.mepms.rabbitmq.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mepms.entity.NotificationRecord;

import com.mepms.repository.NotificationRecordRepository;


@Service
public class NotificationMessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotification(NotificationRecord notification) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.NOTIFICATION_EXCHANGE,
            RabbitMQConfig.NOTIFICATION_ROUTING_KEY,
            notification
        );
    }
    
    @Autowired
    private NotificationMessagePublisher notificationPublisher;
    
    @Autowired
    private NotificationRecordRepository notificationRepo;


    public void createNotification(NotificationRecord record) {
        // Save notification to DB 
    	notificationRepo.save(record);

        // Publish message to RabbitMQ
        notificationPublisher.sendNotification(record);
    }
}
