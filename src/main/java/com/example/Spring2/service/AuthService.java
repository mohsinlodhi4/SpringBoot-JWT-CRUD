package com.example.Spring2.service;

import com.example.Spring2.dto.ErrorResponse;
import com.example.Spring2.dto.SuccessResponse;
import com.example.Spring2.dto.UserDTO;
import com.example.Spring2.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// This interface will be implemented in AuthServiceImpl class and service interface will be autowired to controllers
public interface AuthService {

    public ResponseEntity login(String email, String password); // takes in credentials and returns response entity

    public ResponseEntity register(UserDTO userDTO); // takes in user Data transfer object with all required and optional fields 
}
