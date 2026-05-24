package com.emergency.service;

import com.emergency.model.entity.Usuario;
import com.emergency.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario user = usuariosRepository.findByEmail(usuario);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + usuario);
        }
        return user;
    }

    public int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {
            throw new RuntimeException("Usuário não autenticado");
        }
        Usuario user = (Usuario) authentication.getPrincipal();
        return user.getId();
    }
}
