package com.rpe.challenge.users.application.services;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User execute(CreateUserInput input) {
		if (userRepository.existsByEmail(input.email())) {
			String errorMessage = "Já existe um usuário com este e-mail cadastrado";
			throw new UserAlreadyExistsException(
				"US.CR-001",
				errorMessage,
				List.of(
					new FieldErrorDetail(
						"email",
						List.<String>of(errorMessage)
					)
				)
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
