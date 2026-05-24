package com.emergency.repository;

import com.emergency.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles findByNome(String nome);
}
