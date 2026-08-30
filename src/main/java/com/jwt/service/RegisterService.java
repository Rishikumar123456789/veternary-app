package com.jwt.service;

import java.util.Map;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jwt.configurations.JwtTokenGenerator;
import com.jwt.exceptions.InvalidCredentialsException;
import com.jwt.model.Register;
import com.jwt.model.Roles;
import com.jwt.repository.RegisterRepository;
import com.jwt.util.PasswordEncoder;
import com.jwt_library.LoginCredentials;
import com.jwt_library.Profile;
import com.jwt_library.UserCredentials;

@Service
public class RegisterService implements RegisterInterface {

	private final RegisterRepository registerRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtTokenGenerator jwtTokenGenerator;

	public RegisterService(RegisterRepository registerRepository, PasswordEncoder passwordEncoder,
			JwtTokenGenerator jwtTokenGenerator) {

		super();
		this.registerRepository = registerRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenGenerator = jwtTokenGenerator;

	}

	@Override
	public Register saveUserDetails(UserCredentials userCredentials) {

		Register register = new Register();
		register.setFullName(userCredentials.getFullname());
		register.setEmail(userCredentials.getEmail());
		register.setPhoneNumber(userCredentials.getPhone_number());
		register.setPassword(passwordEncoder.encodePassword(userCredentials.getPassword()));
		register.setRoles(Set.of(Roles.ROLES_USER));
		return registerRepository.save(register);

	}

	@Override
	public String login(LoginCredentials loginCredentials) {

		String email = loginCredentials.getEmail();
		String userEnterdPassword = loginCredentials.getPassword();

		if (email == null || email.isBlank() || userEnterdPassword == null
				|| userEnterdPassword.isBlank()) {

			throw new InvalidCredentialsException("Please Enter Your Credentials");

		} else if (registerRepository.findByEmail(email).isEmpty()) {

			throw new InvalidCredentialsException("Data Not Found");

		} else {

			Register register = registerRepository.findByEmail(email).get();
			String databasePassword = register.getPassword();

			if (passwordEncoder.matchPasswords(userEnterdPassword, databasePassword)) {

				Map<String, Object> claimsMap = Map.of("roles", register.getRoles(), "email",
						register.getEmail());
				return jwtTokenGenerator.generateJwtToken(register.getUserId(), claimsMap);

			}

			throw new InvalidCredentialsException("Invalid username or password");

		}

	}

	@Override
	public Profile getProfile() {

		Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
		Register register = registerRepository.findById(userId).get();
		Profile profile = new Profile();
		profile.setUsername(register.getFullName());
		profile.setEmail(register.getEmail());
		return profile;

	}

}
