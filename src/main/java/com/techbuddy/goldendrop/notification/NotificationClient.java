package com.techbuddy.goldendrop.notification;

import org.springframework.stereotype.Component;

@Component
public interface NotificationClient {
    void sendMessage();
}
