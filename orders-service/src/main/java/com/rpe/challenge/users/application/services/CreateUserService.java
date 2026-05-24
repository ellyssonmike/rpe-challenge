package com.rpe.challenge.users.application.services;

import com.rpe.challenge.users.application.inputs.CreateUserInput;
import com.rpe.challenge.users.domain.exceptions.UserAlreadyExistsException;
import com.rpe.challenge.users.domain.mappers.CreateUserMapper;
import com.rpe.challenge.users.domain.mappers.UserMapper;
import com.rpe.challenge.users.domain.models.User;
import com.rpe.challenge.users.persistence.entities.UserEntity;
import com.rpe.challenge.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User execute(CreateUserInput input) {
		if (userRepository.existsByEmail(input.email())) {
			throw new UserAlreadyExistsException(
				"US.CR-001",
				"Já existe um usuário com este e-mail cadastrado"
			);
		}

		String passwordHash = passwordEncoder.encode(input.password());
		User user = CreateUserMapper.toDomain(input, passwordHash);

		UserEntity createdUser = userRepository.save(
			UserMapper.toEntity(user)
		);

		return UserMapper.toDomain(createdUser);
	}
}
