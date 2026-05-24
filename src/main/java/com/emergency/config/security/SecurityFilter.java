package com.emergency.config.security;

import com.emergency.service.AuthorizationService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@SecurityScheme(name = SecurityFilter.SECURITY,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT", scheme = "Bearer")
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorizationService authorizationService; // UserDetailsService

    public static final String SECURITY = "BearerAuth";

    // rotas que NÃO devem ser filtradas (swagger, docs, auth, webjars...)
    private static final String[] AUTH_WHITE_LIST = {"/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/webjars/**",
            "/auth/**"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            try {
                // valida o token e recebe o subject/username (ex: email)
                String username = tokenService.validateToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // carrega o usuário via AuthorizationService (UserDetailsService)
                    UserDetails userDetails = authorizationService.loadUserByUsername(username);

                    if (userDetails != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception ex) {
                // Não interrompa a cadeia — apenas limpe o contexto para segurança
                SecurityContextHolder.clearContext();
                // opcional: log.warn("Erro ao validar token: {}", ex.getMessage());
            }
        }

        // Sempre continue a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isBlank()) return null;
        if (!header.startsWith("Bearer ")) return null;
        return header.substring(7).trim();
    }


}