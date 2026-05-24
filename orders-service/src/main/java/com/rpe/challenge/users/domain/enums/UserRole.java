package com.rpe.challenge.users.domain.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum UserRole {
	ADMIN(List.of("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER")),
	MANAGER(List.of("ROLE_MANAGER", "ROLE_USER")),
	USER(List.of("ROLE_USER"));

	@Getter
	private final List<String> authorities;

	UserRole(List<String> authorities) {
		this.authorities = authorities;
	}

	public Collection<? extends GrantedAuthority> asGrantedAuthorities() {
		return authorities.stream()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}
}
