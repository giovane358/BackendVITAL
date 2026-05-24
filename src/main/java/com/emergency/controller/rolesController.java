package com.emergency.controller;

import com.emergency.dto.roles.RoleCreateDTO;
import com.emergency.model.entity.Roles;
import com.emergency.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
@Tag(name = "Roles", description = "Controle de Criar")
public class rolesController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    @Operation(summary = "Realizar o cadastro", description = "Cria uma nova role).", method = "POST")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Não foi possível processar a sua solicitação")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "401", description = "Dados incorretos")
    public ResponseEntity<Roles> createRole(@RequestBody @Valid RoleCreateDTO dto) {
        if (this.roleRepository.findByNome(dto.nome()) != null) {
            return ResponseEntity.badRequest().build();
        }

        Roles roles = new Roles(dto.nome());

        this.roleRepository.save(roles);
        return ResponseEntity.ok(roles);
    }

}
