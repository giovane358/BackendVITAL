package com.emergency.repository;

import com.emergency.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team findByName(String name);

    List<Team> findAllByCreatedBy_IdAndActiveTrue(Integer id);

    List<Team> findAllByCreatedBy_IdAndActiveFalse(Integer id);
}