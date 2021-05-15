package com.court.booking.system.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserCreateResponseDto {

    private boolean result;

    private String errorMessage;
}