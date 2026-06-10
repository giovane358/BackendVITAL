package com.emergency.controller;

import com.emergency.model.entity.Logs;
import com.emergency.model.entity.Usuario;
import com.emergency.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsRepository logsRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Logs>> list(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Logs> logs = logsRepository.findAllByUser_Id(usuario.getId());

        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logs> findById(Authentication authentication, @PathVariable Integer id) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Logs log = logsRepository.findById(id).orElse(null);

        if (log == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (log.getUser() == null || !log.getUser().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(log);
    }
}