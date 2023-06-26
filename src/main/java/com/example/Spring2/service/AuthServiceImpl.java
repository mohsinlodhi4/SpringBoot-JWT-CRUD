package com.example.Spring2.service;

import com.example.Spring2.config.JwtTokenUtil;
import com.example.Spring2.dto.ErrorResponse;
import com.example.Spring2.dto.SuccessResponse;
import com.example.Spring2.dto.UserDTO;
import com.example.Spring2.repository.UserRepository;

import com.example.Spring2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	 @Autowired
	 private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;



    public ResponseEntity login(String email, String password){

		try{
			// check if user with specified email exists
			Optional<User> user = userRepository.findByEmailIgnoreCase(email);
			if ((!user.isPresent())) {
				throw new BadCredentialsException("Invalid username or password");
			}

			authenticate(email, password); //verify if password is correct

			final UserDetails userDetails = userDetailsService.loadUserByUsername(email);  // load userDetails object
			final String token = jwtTokenUtil.generateToken(userDetails); // generate jwt token to use in auth routes

			// generate response data object 
			Map<String, Object> data = new HashMap<>();
			data.put("token", token);
			data.put("user", user);

			// return response with respponse template in SuccessResponse class 
			SuccessResponse<Map<String, Object>> body = new SuccessResponse<>("Login successfull.", data);
			return ResponseEntity.status(HttpStatus.OK).body(body);

		}catch (Exception e){
			// In case of error or if user enters invalid credentials, return message with response template in ErrorResponse class
			ErrorResponse<?> error = new ErrorResponse<>(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
    }
	private void authenticate(String username, String password) throws Exception {
		Optional<User> user = userRepository.findByEmailIgnoreCase(username);
		if(user.isEmpty()){
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		if(!passwordEncoder.matches(password, user.get().getPassword())){
			throw new Exception("INVALID_CREDENTIALS");
		}
	}

	public ResponseEntity register(UserDTO userDTO) {
		try{

			// Firstly, check if user with same email already exists 
			Optional<User> alreadyExists = userRepository.findByEmailIgnoreCase(userDTO.getEmail());
			if(!alreadyExists.isEmpty()){
				throw new Exception("User Already exists");
			}

			//save user in db 
			User user = userDetailsService.save(userDTO);

			// generate response data  
			Map<String, Object> data = new HashMap<>();
			data.put("user", user);
			
			// return response with response template in SuccessResponse class
			SuccessResponse<Map<String, Object>> body = new SuccessResponse<>("Registration successful.", data);
			return ResponseEntity.status(HttpStatus.OK).body(body);
		}catch(Exception e){
			// In case of error or if user already exists, return message with response template in ErrorResponse class
			ErrorResponse<?> error = new ErrorResponse<>(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	}




    
}

