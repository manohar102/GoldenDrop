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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class SaleRecordService {
    private final UserService userService;
    private final StoreService storeService;
    private final SaleRecordMapper saleRecordMapper;
    private final SaleRecordRepository saleRecordRepository;
    private final NotificationClient notificationClient = new WhatsAppClient();

    public SaleRecordDTO create(SaleRecordRequest saleRecordRequest) {
        User user = userService.fetchUser();
        Store store = storeService.validateAndFetchStoreId(user.getStore().getId());
        SaleRecord saleRecord = saleRecordMapper.map(saleRecordRequest, store, user);
        saleRecordRepository.save(saleRecord);
        notificationClient.sendMessage();
        return saleRecordMapper.map(saleRecord);
    }

    public SaleRecordDTO getSaleRecordInAnInterval(Timestamp from, Timestamp to) {
        User user = userService.fetchUser();
        Store store = storeService.validateAndFetchStoreId(user.getStore().getId());
        if(to == null) {
            to = new Timestamp(System.currentTimeMillis());
        }
        List<SaleRecord> saleRecords = new ArrayList<>();
//            saleRecordRepository.findByStoreAndInterval(from, to);
        SaleRecordDTO saleRecordDTO = new SaleRecordDTO();
        for(SaleRecord saleRecord : saleRecords) {
            saleRecordDTO.setSaleAmount(saleRecordDTO.getSaleAmount() + saleRecord.getSaleAmount());
            saleRecordDTO.setDigitalAmount(saleRecordDTO.getDigitalAmount() + saleRecord.getDigitalAmount());
            saleRecordDTO.setOnlineAmount(saleRecordDTO.getOnlineAmount() + saleRecord.getOnlineAmount());
            saleRecordDTO.setExpenses(saleRecordDTO.getExpenses() + saleRecord.getExpenses());
        }
    return saleRecordDTO;
    }
}
