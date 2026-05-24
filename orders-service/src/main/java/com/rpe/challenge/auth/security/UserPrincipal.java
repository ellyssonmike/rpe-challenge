package com.rpe.challenge.auth.security;

import com.rpe.challenge.users.domain.enums.UserRole;
import com.rpe.challenge.users.persistence.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
	private final UserEntity userEntity;

	public UserPrincipal(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userEntity.getRole().asGrantedAuthorities();
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getEmail();
	}

	public UserRole getRole() {
		return userEntity.getRole();
	}
}
