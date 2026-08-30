package com.jwt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
   BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
   
   public String encodePassword(String rawPassword) {
	   return  passwordEncoder.encode(rawPassword);
   }
   public Boolean matchPasswords(String rawPassword,String encodedPassword) {
	   return    passwordEncoder.matches(rawPassword, encodedPassword);
   }
}
