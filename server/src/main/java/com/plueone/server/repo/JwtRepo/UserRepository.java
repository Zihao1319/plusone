package com.plueone.server.repo.JwtRepo;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.plueone.server.models.JwtAuth.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);

    Boolean existsByEmail(String email);

}
