package com.court.booking.system.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserDto {

    private String firstName;

    private String token;

    private String lastName;

    private String email;

    private String password;
}