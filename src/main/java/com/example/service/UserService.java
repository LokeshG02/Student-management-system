package com.example.service;

import com.example.dto.LoginRequestDto;
import com.example.dto.LoginResponseDto;
import com.example.dto.SignupRequestDto;
import com.example.dto.SignupResponseDto;
import com.example.entity.User;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.InvalidCredentialsException;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto){

        User user = userRepository
                    .findByEmail(loginRequestDto.getEmail())
                    .orElseThrow(()->
                            new ResourceNotFoundException("No account find with this Email : "
                                    + loginRequestDto.getEmail()));



        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");
        }

        LoginResponseDto loginResponseDto = mapToLoginDto(user);

        return loginResponseDto;
    }

    public SignupResponseDto signupUser(SignupRequestDto signupRequestDto){

        if(emailExists(signupRequestDto) ){
            throw new DuplicateResourceException("Student with email "+ signupRequestDto.getEmail() + " already exits.");

        }

        User user = mapToUser(signupRequestDto);
        userRepository.save(user);

        SignupResponseDto signupResponseDto = mapToSignup(user);

        return signupResponseDto;
    }


    private SignupResponseDto mapToSignup(User user){

        SignupResponseDto createResponse = new SignupResponseDto();

        createResponse.setName(user.getName());
        createResponse.setUserName(user.getUserName());
        createResponse.setEmail((user.getEmail()));
        createResponse.setPassword((user.getPassword()));
        createResponse.setLoginTime((LocalDateTime.now()));
        createResponse.setActive(Boolean.TRUE);

        return createResponse;
    }


    private Boolean emailExists(SignupRequestDto signupRequestDto){

        Boolean check = userRepository.existsByEmail(signupRequestDto.getEmail());

        return check;
    }
    private User mapToUser(SignupRequestDto signupRequestDto){
        User createUser = new User();

        createUser.setName(signupRequestDto.getName());
        createUser.setEmail(signupRequestDto.getEmail());
        createUser.setUserName(signupRequestDto.getUserName());
        createUser.setPassword(signupRequestDto.getPassword());
        createUser.setCreatedTime(LocalDateTime.now());
        createUser.setLoginTime(LocalDateTime.now());
        createUser.setActive(Boolean.FALSE);

        return createUser;

    }
    private LoginResponseDto mapToLoginDto(User user){

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        loginResponseDto.setEmail(user.getEmail());
        loginResponseDto.setPassword(user.getPassword());
        user.setActive(true);
        loginResponseDto.setMessage("Login Successful");

        return loginResponseDto;
    }

}
