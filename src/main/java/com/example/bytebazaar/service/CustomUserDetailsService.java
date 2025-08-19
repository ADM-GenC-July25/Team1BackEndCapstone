package com.example.bytebazaar.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bytebazaar.model.CustomUserDetails;
import com.example.bytebazaar.model.User;
import com.example.bytebazaar.repository.UserRepository;

@Service
public class  CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	public CustomUserDetailsService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Decide whether to treat 'username' as email or username
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}