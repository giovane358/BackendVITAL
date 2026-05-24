package com.emergency.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity( name = "usuario")
@Table (name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome" )
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "senha")
    private String senha;


    @JoinColumn(name = "role_id")
    @ManyToOne
    private Roles rolesId;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDate createdAt;



    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    public Usuario(String nome, String cpf, String email, String phone, String senha, Roles  rolesId) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.senha = senha;
        this.rolesId = rolesId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ("ADMIN".equals(this.rolesId.getNome())) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
