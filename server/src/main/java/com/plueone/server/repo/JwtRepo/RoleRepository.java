package com.plueone.server.repo.JwtRepo;

import java.util.Optional;

import com.plueone.server.models.JwtAuth.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByAuthority(String authority);

}
