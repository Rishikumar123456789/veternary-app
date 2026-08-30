package com.jwt.service;


import com.jwt.model.Register;
import com.jwt_library.LoginCredentials;
import com.jwt_library.Profile;
import com.jwt_library.UserCredentials;

public interface RegisterInterface {
	
public Register    saveUserDetails(UserCredentials userCredentials);

public String login(LoginCredentials loginCredentials);
public Profile getProfile();
     
}
