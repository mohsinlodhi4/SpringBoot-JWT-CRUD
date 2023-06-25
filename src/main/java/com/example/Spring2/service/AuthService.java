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

public interface AuthService {

    public ResponseEntity login(String email, String password);

    public ResponseEntity register(UserDTO userDTO);
}
