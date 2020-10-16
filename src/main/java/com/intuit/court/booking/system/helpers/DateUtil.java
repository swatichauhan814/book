package com.intuit.court.booking.system.helpers;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {};

    public  static Timestamp convertToTimestamp(String dateTime) {

        if(StringUtils.isNotEmpty(dateTime)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(dateTime));
            return Timestamp.valueOf(localDateTime);
        }

        return null;
    }
}
