package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.dto.SaleRecordDTO;
import com.techbuddy.goldendrop.exception.UserNotFoundException;
import com.techbuddy.goldendrop.mapper.SaleRecordMapper;
import com.techbuddy.goldendrop.model.SaleRecord;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.notification.NotificationClient;
import com.techbuddy.goldendrop.notification.WhatsAppClient;
import com.techbuddy.goldendrop.repository.SaleRecordRepository;
import com.techbuddy.goldendrop.request.SaleRecordRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired, @Lazy }))
public class SaleRecordService {
    private final UserService userService;
    private final StoreService storeService;
    private final SaleRecordMapper saleRecordMapper;
    private final SaleRecordRepository saleRecordRepository;
    private final NotificationClient notificationClient = new WhatsAppClient();
    public SaleRecordDTO create(SaleRecordRequest saleRecordRequest) {
        User user = userService.fetchUser().orElseThrow(() -> new UserNotFoundException("User " + "Not found"));
        Store store = storeService.validateAndFetchStoreId(saleRecordRequest.getStoreId());
        SaleRecord saleRecord = saleRecordMapper.map(saleRecordRequest, store, user);
        saleRecordRepository.save(saleRecord);
        notificationClient.sendMessage();
        return saleRecordMapper.map(saleRecord);
    }
}
