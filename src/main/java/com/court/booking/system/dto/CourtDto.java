package com.court.booking.system.dto;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourtDto {

    private String name;

    private String city;

    private List<SportDto> sportDtoList;
}