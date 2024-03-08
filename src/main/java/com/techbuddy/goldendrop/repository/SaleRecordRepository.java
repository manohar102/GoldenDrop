package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.SaleRecord;
import com.techbuddy.goldendrop.model.Store;
import java.sql.Timestamp;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface SaleRecordRepository extends BaseRepository<SaleRecord, Long> {

//    @Query("SELECT sale_record FROM SaleRecord sale_record WHERE (:fromTimestamp is null OR "
//        + "sale_record.createdDate >= :fromTimestamp) AND sale_record.createdDate <= :toTimestamp")
//    List<SaleRecord> findByStoreAndInterval(Timestamp fromTimestamp, Timestamp toTimestamp);

}
