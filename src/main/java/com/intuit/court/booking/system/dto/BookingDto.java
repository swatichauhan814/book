package com.intuit.court.booking.system.dto;


import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingDto {

    private String name;

    private String sportName;

    private long amount;

    private Timestamp startTime;

    private Timestamp endTime;
}