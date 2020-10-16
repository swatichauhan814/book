package com.intuit.court.booking.system.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class SportDto {

    private String name;

    private long price;

}