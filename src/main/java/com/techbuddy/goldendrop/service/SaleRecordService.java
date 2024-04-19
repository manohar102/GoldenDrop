package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.dto.SaleRecordDTO;
import com.techbuddy.goldendrop.dto.TimePeriod;
import com.techbuddy.goldendrop.mapper.SaleRecordMapper;
import com.techbuddy.goldendrop.model.SaleRecord;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.notification.EmailClient;
import com.techbuddy.goldendrop.notification.WhatsAppClient;
import com.techbuddy.goldendrop.repository.SaleRecordRepository;
import com.techbuddy.goldendrop.request.SaleRecordRequest;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
    private final WhatsAppClient notificationClient;

    public SaleRecordDTO create(SaleRecordRequest saleRecordRequest) {
        User user = userService.fetchUser();
        Store store = storeService.validateAndFetchStoreId(user.getStore().getId());
        SaleRecord saleRecord = saleRecordMapper.map(saleRecordRequest, store, user);
        saleRecordRepository.save(saleRecord);
        notificationClient.sendMessage(saleRecord);
        return saleRecordMapper.map(saleRecord);
    }

    public SaleRecordDTO getSaleRecordInAnInterval(Timestamp from, Timestamp to) {
        User user = userService.fetchUser();
        Store store = storeService.validateAndFetchStoreId(user.getStore().getId());
        if (to == null) {
            to = new Timestamp(System.currentTimeMillis());
        }
        List<SaleRecord> saleRecords =
                saleRecordRepository.findByCreatedDateIsGreaterThanEqualAndCreatedDateIsLessThanEqualAndStore(
                        from, to, store);
        SaleRecordDTO saleRecordDTO = new SaleRecordDTO();
        for (SaleRecord saleRecord : saleRecords) {
            saleRecordDTO.setSaleAmount(
                    ObjectUtils.defaultIfNull(saleRecordDTO.getSaleAmount(), 0.0) + saleRecord.getSaleAmount());
            saleRecordDTO.setDigitalAmount(
                    ObjectUtils.defaultIfNull(saleRecordDTO.getDigitalAmount(), 0.0) + saleRecord.getDigitalAmount());
            saleRecordDTO.setOnlineAmount(
                    ObjectUtils.defaultIfNull(saleRecordDTO.getOnlineAmount(), 0.0) + saleRecord.getOnlineAmount());
            saleRecordDTO.setExpenses(
                    ObjectUtils.defaultIfNull(saleRecordDTO.getExpenses(), 0.0) + saleRecord.getExpenses());
        }
        return saleRecordDTO;
    }

    public TimePeriod getSalesPeriods() {
        User user = userService.fetchUser();
        Store store = storeService.validateAndFetchStoreId(user.getStore().getId());
        return saleRecordRepository.getSalesPeriods(store);
    }
}
