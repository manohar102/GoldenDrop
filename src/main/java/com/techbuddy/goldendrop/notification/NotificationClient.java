package com.techbuddy.goldendrop.notification;

import com.techbuddy.goldendrop.model.SaleRecord;
import org.springframework.stereotype.Component;

@Component
public interface NotificationClient {
    void sendMessage(SaleRecord saleRecord);
}
