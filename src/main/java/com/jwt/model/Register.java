package com.jwt.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Register")
@Table(name = "register")
public class Register {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
	private Long userId;
    
    @Column(name = "full_name")
	private String fullName;
    
    @Column(name = "email")
	private String email;
    
    @Column(name = "phone_number")
	private String phoneNumber;
    
    @Column(name = "password")
	private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
	private Set<Roles> roles=new HashSet<>();
}
