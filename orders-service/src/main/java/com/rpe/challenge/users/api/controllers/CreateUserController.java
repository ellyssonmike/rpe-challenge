package com.rpe.challenge.users.api.controllers;

import com.rpe.challenge.shared.exceptions.core.ExceptionModule;
import com.rpe.challenge.shared.exceptions.core.ExceptionModuleType;
import com.rpe.challenge.users.api.requests.CreateUserRequest;
import com.rpe.challenge.users.api.responses.UserResponse;
import com.rpe.challenge.users.application.services.CreateUserService;
import com.rpe.challenge.users.domain.mappers.CreateUserMapper;
import com.rpe.challenge.users.domain.mappers.UserMapper;
import com.rpe.challenge.users.domain.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ExceptionModule(ExceptionModuleType.USERS)
public class CreateUserController {
	private final CreateUserService createUserService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		User user = createUserService.execute(CreateUserMapper.toInput(request));

		return ResponseEntity.ok(UserMapper.toResponse(user));
	}
}
