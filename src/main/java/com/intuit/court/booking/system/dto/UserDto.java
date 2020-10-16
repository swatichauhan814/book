package com.intuit.court.booking.system.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserDto {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}