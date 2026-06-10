package com.emergency.repository;

import com.emergency.model.entity.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Integer> {

    List<Occurrence> findAllByCreatedBy_IdAndActiveTrue(Integer id);

    List<Occurrence> findAllByCreatedBy_IdAndActiveFalse(Integer id);
}