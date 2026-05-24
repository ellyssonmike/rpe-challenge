package com.rpe.challenge.auth.application.services;

import com.rpe.challenge.auth.security.UserPrincipal;
import com.rpe.challenge.users.persistence.entities.UserEntity;
import com.rpe.challenge.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		return new UserPrincipal(user);
	}
}
