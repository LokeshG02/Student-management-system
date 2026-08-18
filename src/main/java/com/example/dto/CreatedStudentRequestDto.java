package com.example.dto;

import jakarta.validation.constraints.*;

public class CreatedStudentRequestDto {

    @NotBlank(message = "Invalid name format")
    private String name;

    @Min(value = 14 , message = "Min age is 14")
    private int age;

    @Email(message = "This is not a valid Email")
    private String email;

    @Min(value = 101 , message = "Can't be less then 101")
    @Max(value = 199 , message = "Can't be more then 199")
    private int rollNo;

    @NotBlank(message = "Subject can not be null")
    private String subject;

    @NotBlank(message = "Class can not be null")
    private String studentClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
