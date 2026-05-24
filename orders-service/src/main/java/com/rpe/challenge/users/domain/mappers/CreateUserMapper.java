package com.rpe.challenge.users.domain.mappers;

import com.rpe.challenge.users.api.requests.CreateUserRequest;
import com.rpe.challenge.users.application.inputs.CreateUserInput;
import com.rpe.challenge.users.domain.models.User;
import com.rpe.challenge.users.domain.valueobjects.Email;

public class CreateUserMapper {
	public static User toDomain(CreateUserInput input, String hashedPassword) {
		return User.builder()
			.firstName(input.firstName())
			.lastName(input.lastName())
			.email(new Email(input.email()))
			.password(hashedPassword)
			.role(input.role())
			.build();
	}

	public static CreateUserInput toInput(CreateUserRequest request) {
		return new CreateUserInput(
			request.firstName(),
			request.lastName(),
			request.email(),
			request.password(),
			request.role()
		);
	}
}
