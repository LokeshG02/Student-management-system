package com.example.controller;

import com.example.dto.LoginRequestDto;
import com.example.dto.LoginResponseDto;
import com.example.dto.SignupRequestDto;
import com.example.dto.SignupResponseDto;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")

    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto){

        LoginResponseDto loginResponseDto = userService.loginUser(loginRequestDto);

        return ResponseEntity.ok(loginResponseDto);

    }

    @PostMapping("/signUp")

    public ResponseEntity<SignupResponseDto> signupUser(@Valid @RequestBody SignupRequestDto signupRequestDto){

        SignupResponseDto signupResponseDto = userService.signupUser(signupRequestDto);

        return ResponseEntity.ok(signupResponseDto);
    }


}
