package com.jwt.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.Register;
import com.jwt.service.RegisterService;
import com.jwt_library.LoginCredentials;
import com.jwt_library.Profile;
import com.jwt_library.UserCredentials;

@RestController
public class RegisterController {

	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {

		super();
		this.registerService = registerService;

	}

	@PostMapping("/getRegister")
	public Register getRegister(@RequestBody UserCredentials userCredentials) {

		return registerService.saveUserDetails(userCredentials);

	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> getlogin(@RequestBody LoginCredentials loginCredentials) {

		String token= registerService.login(loginCredentials);
		return ResponseEntity.ok(Map.of("token",token));

	}
	@GetMapping("/getProfile")
	public Profile getlogin() {

		return registerService.getProfile();

	}

}
