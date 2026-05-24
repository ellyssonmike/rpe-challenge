package com.rpe.challenge.users.domain.mappers;

import com.rpe.challenge.users.api.responses.UserResponse;
import com.rpe.challenge.users.domain.models.User;
import com.rpe.challenge.users.domain.valueobjects.Email;
import com.rpe.challenge.users.persistence.entities.UserEntity;

public class UserMapper {
	public static User toDomain(UserEntity entity) {
		return User.builder()
			.id(entity.getId())
			.firstName(entity.getFirstName())
			.lastName(entity.getLastName())
			.email(new Email(entity.getEmail()))
			.password(entity.getPassword())
			.role(entity.getRole())
			.createdAt(entity.getCreatedAt())
			.updatedAt(entity.getUpdatedAt())
			.build();
	}
	
	public static UserEntity toEntity(User user) {
		return UserEntity.builder()
			.id(user.getId())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.email(user.getEmail().value())
			.password(user.getPassword())
			.role(user.getRole())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}

	public static UserResponse toResponse(User user) {
		return new UserResponse(
			user.getId(),
			user.getFirstName(),
			user.getLastName(),
			user.getEmail().value(),
			user.getRole(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
	}
}
