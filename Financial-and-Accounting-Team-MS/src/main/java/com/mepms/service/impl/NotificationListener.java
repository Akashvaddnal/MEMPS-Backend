package com.mepms.service.impl;

import javax.management.Notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {
    @RabbitListener(queues = "notificationQueue")
    public void handle(Notification notification) {
        // process notification (save to DB, send email, etc.)
    }
}
