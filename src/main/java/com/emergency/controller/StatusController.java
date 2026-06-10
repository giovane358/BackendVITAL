package com.emergency.controller;


import com.emergency.dto.status.StatusDTo;
import com.emergency.dto.status.StatusDeletDTo;
import com.emergency.dto.status.StatusUpdateDTo;
import com.emergency.model.entity.Status;
import com.emergency.model.entity.Usuario;
import com.emergency.repository.StatusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @PostMapping("/register")
    public ResponseEntity<Status> register(Authentication authentication, @RequestBody @Valid StatusDTo data) {

        Usuario usuario = null;

        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {

            usuario = (Usuario) authentication.getPrincipal();
        }

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (statusRepository.findByNome(data.nome()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Status status = new Status();

        status.setNome(data.nome());
        status.setCreatedAt(LocalDateTime.now());
        status.setActive(true);
        status.setCreatedBy(usuario);

        statusRepository.save(status);

        return ResponseEntity.ok(status);
    }

    @GetMapping("/list/enable")
    public ResponseEntity<List<Status>> listEnable(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Status> statusList = statusRepository.findAllByCreatedBy_IdAndActiveTrue(usuario.getId());

        return ResponseEntity.ok(statusList);
    }

    @GetMapping("/list/disabled")
    public ResponseEntity<List<Status>> listDisabled(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Status> statusList = statusRepository.findAllByCreatedBy_IdAndActiveFalse(usuario.getId());

        return ResponseEntity.ok(statusList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> findById(Authentication authentication, @PathVariable Integer id) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Status status = statusRepository.findById(id).orElse(null);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (status.getCreatedBy() == null || !status.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(status);
    }

    @PutMapping("/update")
    public ResponseEntity<Status> update(Authentication authentication, @RequestBody @Valid StatusUpdateDTo data) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Status status = statusRepository.findById(data.id()).orElse(null);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (status.getCreatedBy() == null || !status.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (data.nome() != null && !data.nome().isBlank()) {

            Status statusExistente = statusRepository.findByNome(data.nome());

            if (statusExistente != null && !statusExistente.getId().equals(status.getId())) {

                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            status.setNome(data.nome());
        }

        statusRepository.save(status);

        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Status> delete(Authentication authentication, @RequestBody @Valid StatusDeletDTo data) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Status status = statusRepository.findById(data.id()).orElse(null);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (status.getCreatedBy() == null || !status.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        status.setActive(false);

        statusRepository.save(status);

        return ResponseEntity.ok(status);
    }
}