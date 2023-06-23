package com.example.Spring2.dto;

import lombok.NonNull;

import javax.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message = "name field is required")
    private String name;


    private int age;
    @NotBlank(message = "email field is required")
    private String email;

    @NotBlank(message = "password field is required")
    private String password;

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
    public String getUsername(){
        return this.email;
    }

    public String getPassword() {
        return password;
    }

}