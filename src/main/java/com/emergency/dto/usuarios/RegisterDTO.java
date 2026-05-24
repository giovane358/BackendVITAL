package com.emergency.dto.usuarios;

public record RegisterDTO(String nome, String cpf, String phone, String email, String senha, int roleID) {
}
