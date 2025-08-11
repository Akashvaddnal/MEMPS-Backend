package com.mepms.service.impl;

import javax.management.Notification;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    public NotificationProducer(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }
    public void sendNotification(Notification notification) {
        rabbitTemplate.convertAndSend("notificationQueue", notification);
    }
}
