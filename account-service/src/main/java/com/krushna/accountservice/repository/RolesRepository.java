package com.krushna.accountservice.repository;

import com.krushna.accountservice.entity.Roles;
import com.krushna.accountservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRole(String role);
}
