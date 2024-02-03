package com.techbuddy.goldendrop.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {

    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp createdDate;
}
