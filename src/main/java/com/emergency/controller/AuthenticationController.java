package com.emergency.controller;


import com.emergency.config.security.TokenService;
import com.emergency.dto.usuarios.LoginDTO;
import com.emergency.dto.usuarios.RegisterDTO;
import com.emergency.dto.usuarios.TokenDTO;
import com.emergency.model.entity.Roles;
import com.emergency.model.entity.Usuario;
import com.emergency.repository.RoleRepository;
import com.emergency.repository.UsuariosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Usuario", description = "Controle de Criar e Login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleRepository rolesRepository;

    @PostMapping("/register")
    @Operation(summary = "Realizar o cadastro", description = "Cria um novo usuário (registro). Recebe os dados necessários para cadastro e retorna informações do usuário criado (sem a senha).", method = "POST")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Não foi possível processar a sua solicitação")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "401", description = "Dados incorretos")
    public ResponseEntity<Usuario> registerUser(@RequestBody @Valid RegisterDTO dto) {
        if (this.usuariosRepository.findByEmail(dto.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encriptedPassword = new BCryptPasswordEncoder().encode(dto.senha());

        Roles rolesId = rolesRepository.findById(dto.roleID()).orElseThrow(() -> new RuntimeException("Role não encontrada"));

        Usuario usuario = new Usuario(dto.nome(), dto.cpf(), dto.email(), dto.phone(), encriptedPassword, rolesId);

        this.usuariosRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar o login", description = "Autentica o usuário com email e senha, retornando um token JWT para uso nos endpoints protegidos.", method = "POST")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Não foi possível processar a sua solicitação")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "401", description = "Dados incorretos")
    public ResponseEntity login(@RequestBody @Valid LoginDTO dto) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var user = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((Usuario) user.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
