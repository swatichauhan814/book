package com.court.booking.system.controller;

import com.court.booking.system.dto.UserDto;
import com.court.booking.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController controller = new UserController(userService);

    @Test
    public void registerUser() {

       controller.register(UserDto.builder().build());

    }

}