package com.emergency.repository;

import com.emergency.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByNome(String nome);

    List<Status> findAllByCreatedBy_IdAndActiveTrue(Integer id);

    List<Status> findAllByCreatedBy_IdAndActiveFalse(Integer id);
}