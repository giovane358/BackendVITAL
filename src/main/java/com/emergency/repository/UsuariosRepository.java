package com.emergency.repository;

import com.emergency.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);


}
