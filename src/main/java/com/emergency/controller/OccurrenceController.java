package com.emergency.controller;

import com.emergency.dto.occurrence.OccurrenceDTo;
import com.emergency.dto.occurrence.OccurrenceDeletDTo;
import com.emergency.dto.occurrence.OccurrenceUpdateDTo;
import com.emergency.model.entity.Occurrence;
import com.emergency.model.entity.Status;
import com.emergency.model.entity.Team;
import com.emergency.model.entity.Usuario;
import com.emergency.repository.OccurrenceRepository;
import com.emergency.repository.StatusRepository;
import com.emergency.repository.TeamRepository;
import com.emergency.repository.UsuariosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/occurrence")
public class OccurrenceController {

    @Autowired
    private OccurrenceRepository occurrenceRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostMapping("/register")
    public ResponseEntity<Occurrence> register(Authentication authentication, @RequestBody @Valid OccurrenceDTo data) {

        Usuario usuario = null;

        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {

            usuario = (Usuario) authentication.getPrincipal();
        }

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Status status = statusRepository.findById(data.statusId()).orElse(null);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Team team = teamRepository.findById(data.teamId()).orElse(null);

        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Usuario citizen = usuariosRepository.findById(data.citizenId()).orElse(null);

        if (citizen == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Occurrence occurrence = new Occurrence();

        occurrence.setType(data.type());
        occurrence.setDescription(data.description());
        occurrence.setLatitude(data.latitude());
        occurrence.setLongitude(data.longitude());
        occurrence.setPriority(data.priority());

        occurrence.setStatus(status);
        occurrence.setTeam(team);
        occurrence.setCitizen(citizen);

        occurrence.setCreatedAt(LocalDateTime.now());
        occurrence.setActive(true);
        occurrence.setCreatedBy(usuario);

        occurrenceRepository.save(occurrence);

        return ResponseEntity.ok(occurrence);
    }

    @GetMapping("/list/enable")
    public ResponseEntity<List<Occurrence>> listEnable(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Occurrence> occurrenceList = occurrenceRepository.findAllByCreatedBy_IdAndActiveTrue(usuario.getId());

        return ResponseEntity.ok(occurrenceList);
    }

    @GetMapping("/list/disabled")
    public ResponseEntity<List<Occurrence>> listDisabled(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<Occurrence> occurrenceList = occurrenceRepository.findAllByCreatedBy_IdAndActiveFalse(usuario.getId());

        return ResponseEntity.ok(occurrenceList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Occurrence> findById(Authentication authentication, @PathVariable Integer id) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Occurrence occurrence = occurrenceRepository.findById(id).orElse(null);

        if (occurrence == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (occurrence.getCreatedBy() == null || !occurrence.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(occurrence);
    }

    @PutMapping("/update")
    public ResponseEntity<Occurrence> update(Authentication authentication, @RequestBody @Valid OccurrenceUpdateDTo data) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Occurrence occurrence = occurrenceRepository.findById(data.id()).orElse(null);

        if (occurrence == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (occurrence.getCreatedBy() == null || !occurrence.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (data.type() != null) {
            occurrence.setType(data.type());
        }

        if (data.description() != null) {
            occurrence.setDescription(data.description());
        }

        if (data.latitude() != null) {
            occurrence.setLatitude(data.latitude());
        }

        if (data.longitude() != null) {
            occurrence.setLongitude(data.longitude());
        }

        if (data.priority() != null) {
            occurrence.setPriority(data.priority());
        }

        if (data.statusId() != null) {

            Status status = statusRepository.findById(data.statusId()).orElse(null);

            if (status == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            occurrence.setStatus(status);
        }

        if (data.teamId() != null) {

            Team team = teamRepository.findById(data.teamId()).orElse(null);

            if (team == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            occurrence.setTeam(team);
        }

        if (data.citizenId() != null) {

            Usuario citizen = usuariosRepository.findById(data.citizenId()).orElse(null);

            if (citizen == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            occurrence.setCitizen(citizen);
        }

        occurrence.setUpdatedAt(LocalDateTime.now());

        occurrenceRepository.save(occurrence);

        return ResponseEntity.ok(occurrence);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Occurrence> delete(Authentication authentication, @RequestBody @Valid OccurrenceDeletDTo data) {

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Occurrence occurrence = occurrenceRepository.findById(data.id()).orElse(null);

        if (occurrence == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (occurrence.getCreatedBy() == null || !occurrence.getCreatedBy().getId().equals(usuario.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        occurrence.setActive(false);

        occurrenceRepository.save(occurrence);

        return ResponseEntity.ok(occurrence);
    }
}