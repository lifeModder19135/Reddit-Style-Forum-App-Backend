package com.ntolb.RedditCloneBackend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ntolb.RedditCloneBackend.dto.RegisterRequest;
import com.ntolb.RedditCloneBackend.model.User;
import com.ntolb.RedditCloneBackend.model.VerificationToken;
import com.ntolb.RedditCloneBackend.repository.UserRepository;
import com.ntolb.RedditCloneBackend.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder encoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	
	public void signup(RegisterRequest request) {
		
		User newUser = new User();
		newUser.setUsername(request.getUsername());
		newUser.setFName(request.getFName());
		newUser.setLName(request.getLName());
		newUser.setEmail(request.getEmail());
		newUser.setPassword(encoder.encode(request.getPassword()));
		newUser.setCreatedDate(Instant.now()) ;
		newUser.setEnabled(false);
		userRepository.save(newUser);
		String token = generateVerificationToken(newUser);
		
	}
	
	private String generateVerificationToken(User user) {
		
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
		
	}
	
}
