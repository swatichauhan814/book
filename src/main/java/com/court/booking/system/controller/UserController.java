package com.court.booking.system.controller;


import com.court.booking.system.dto.UserDto;
import com.court.booking.system.dto.UserCreateResponseDto;
import com.court.booking.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserCreateResponseDto register(@RequestBody UserDto userDto) {

        UserCreateResponseDto userCreateResponseDto = new UserCreateResponseDto();
        log.info("Received request to create new user at time={} and email={}", LocalDateTime.now(), userDto.getEmail());

        try {
            userService.registerUser(userDto);
            userCreateResponseDto.setResult(true);
        } catch (Exception e) {

            log.error("Failed, registering of new User for given email={} in DB", userDto.getEmail());
            userCreateResponseDto.setErrorMessage(e.getMessage());
        }

        return userCreateResponseDto;
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Boolean userExist = userService.login(email, password);

        if (userExist)
            return "Logged";

        else
            return "Not Logged";
    }

//    @PostMapping("user")
//    public UserDto login(@RequestParam("user") String email, @RequestParam("password") String pwd) {
//        String token = getJWTToken(email);
//        UserDto user = new UserDto();
//        user.setEmail(email);
//        user.setToken(token);
//        return user;
//    }

}