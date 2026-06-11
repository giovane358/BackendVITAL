package com.emergency.controller;

import com.emergency.dto.team.TeamDTo;
import com.emergency.dto.team.TeamDeletDTo;
import com.emergency.dto.team.TeamUpdateDTo;
import com.emergency.model.entity.Team;
import com.emergency.model.entity.Usuario;
import com.emergency.repository.TeamRepository;
import com.emergency.service.LogsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@SecurityRequirement(name = "BearerAuth")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private LogsService logsService;

    @PostMapping("/register")
    public ResponseEntity<Team> register(Authentication authentication, @RequestBody @Valid TeamDTo data) {


        Usuario usuario = null;


        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {

            usuario = (Usuario) authentication.getPrincipal();
            System.out.println(usuario);
        }

        if (this.teamRepository.findByName(data.name())!= null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        Team team = new Team();

        team.setName(data.name());
        team.setVehicle(data.vehicle());
        team.setCurrentLat(data.currentLat());
        team.setCurrentLng(data.currentLng());

        team.setActive(true);

        team.setCreatedBy(usuario);


        teamRepository.save(team);

        logsService.saveLog(usuario, "CREATE_TEAM", "Equipe " + team.getName() + " cadastrada");

        return ResponseEntity.ok(team);
    }

    @GetMapping("/list/enable")
    public ResponseEntity<List<Team>> listEnable(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Team> teams = teamRepository.findAllByCreatedBy_IdAndActiveTrue(usuario.getId());

        return ResponseEntity.ok(teams);
    }

    @GetMapping("/list/disabled")
    public ResponseEntity<List<Team>> listDisabled(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Team> teams = teamRepository.findAllByCreatedBy_IdAndActiveFalse(usuario.getId());

        return ResponseEntity.ok(teams);
    }

    @PutMapping("/update")
    public ResponseEntity<Team> update(Authentication authentication, @RequestBody @Valid TeamUpdateDTo data) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Team team = teamRepository.findById(data.id()).orElse(null);

        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (team.getCreatedBy() == null || !team.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (data.name() != null) {
            team.setName(data.name());
        }

        if (data.vehicle() != null) {
            team.setVehicle(data.vehicle());
        }


        if (data.currentLat() != null) {
            team.setCurrentLat(data.currentLat());
        }

        if (data.currentLng() != null) {
            team.setCurrentLng(data.currentLng());
        }

        teamRepository.save(team);
        logsService.saveLog(usuario, "UPDATE_TEAM", "Equipe ID " + team.getId() + " alterada");
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Team> delete(Authentication authentication, @RequestBody @Valid TeamDeletDTo data) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Team team = teamRepository.findById(data.id()).orElse(null);

        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (team.getCreatedBy() == null || !team.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        team.setActive(false);

        teamRepository.save(team);
        logsService.saveLog(usuario, "DELETE_TEAM", "Equipe ID " + team.getId() + " desativada");
        return ResponseEntity.ok(team);
    }
}