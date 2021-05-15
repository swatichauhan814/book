package com.court.booking.system.helpers;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {};

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Timestamp convertToTimestamp(String dateTime) {

        if(StringUtils.isNotEmpty(dateTime)) {

            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(dateTime));
            return Timestamp.valueOf(localDateTime);
        }

        return null;
    }
}