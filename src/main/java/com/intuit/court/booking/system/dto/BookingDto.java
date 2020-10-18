package com.intuit.court.booking.system.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingDto {

    private String sportName;

    private long amount;

    private String startTime;

    private String endTime;
}