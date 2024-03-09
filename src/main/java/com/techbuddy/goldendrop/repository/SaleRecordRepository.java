package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.dto.TimePeriod;
import com.techbuddy.goldendrop.model.SaleRecord;
import com.techbuddy.goldendrop.model.Store;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface SaleRecordRepository extends BaseRepository<SaleRecord, Long> {

    @Query("SELECT min(sale.createdDate) as startTime, max(sale.createdDate) as endTime FROM SaleRecord sale")
    TimePeriod getSalesPeriods();

    List<SaleRecord> findAllByCreatedDateAndStore(Timestamp timestamp, Store store);

    List<SaleRecord> findByCreatedDateIsGreaterThanEqualAndCreatedDateIsLessThanEqualAndStore(
            Timestamp startTime, Timestamp endTime, Store store);
}
