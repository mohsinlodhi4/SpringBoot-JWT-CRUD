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
			Optional<User> user = userRepository.findByEmailIgnoreCase(email);
			if ((!user.isPresent())) {
				throw new BadCredentialsException("Invalid username or password");
			}

			authenticate(email, password);

			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(email);
			final String token = jwtTokenUtil.generateToken(userDetails);

			Map<String, Object> data = new HashMap<>();
			data.put("token", token);
			data.put("user", user);

			SuccessResponse<Map<String, Object>> body = new SuccessResponse<>("Login successfull.", data);
			return ResponseEntity.status(HttpStatus.OK).body(body);

		}catch (Exception e){
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
//	private void authenticate(String email, String password) throws Exception {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//		} catch (DisabledException e) {
//			throw new Exception("USER DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID CREDENTIALS", e);
//		}
//	}

	public ResponseEntity register(UserDTO userDTO) {
		try{

			Optional<User> alreadyExists = userRepository.findByEmailIgnoreCase(userDTO.getEmail());
			if(!alreadyExists.isEmpty()){
				throw new Exception("User Already exists");
			}

			User user = userDetailsService.save(userDTO);

			Map<String, Object> data = new HashMap<>();
			data.put("user", user);

			SuccessResponse<Map<String, Object>> body = new SuccessResponse<>("Registration successful.", data);
			return ResponseEntity.status(HttpStatus.OK).body(body);
		}catch(Exception e){
			ErrorResponse<?> error = new ErrorResponse<>(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	}




    
}

