package com.rpe.challenge.users.persistence.repositories;

import com.rpe.challenge.users.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	boolean existsByEmail(String email);
}
