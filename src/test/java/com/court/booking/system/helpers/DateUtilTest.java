package com.court.booking.system.helpers;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class DateUtilTest {

    @Test
    public void shouldConvertToTimestampCorrectly() {

        Timestamp timestamp = DateUtil.convertToTimestamp("09-10-2020 06:00:00");

        System.out.println(timestamp);

    }

}