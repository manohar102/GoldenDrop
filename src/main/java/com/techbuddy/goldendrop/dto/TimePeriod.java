package com.techbuddy.goldendrop.dto;

import java.sql.Timestamp;

public interface TimePeriod {
    Timestamp getStartTime();

    Timestamp getEndTime();
}
