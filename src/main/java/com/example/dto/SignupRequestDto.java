package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequestDto {

    @NotBlank(message = "Name can not be blank")
    @Size(min = 3 , message = "Name can not be less than 3 Character")
    private String name;

    @NotBlank(message = "Email can not be blank")
    @Email
    private String email;

    @NotBlank(message = "Enter username!")
    @Size( min=3 , message = "UserName minimum length is 3 character!")
    private String userName;

    @NotBlank(message = "Set Password!")
    @Size(min = 8 , message = "Password minimum length 8 character")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
