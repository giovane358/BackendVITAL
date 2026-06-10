package com.emergency.repository;

import com.emergency.model.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogsRepository extends JpaRepository<Logs, Integer> {

    List<Logs> findAllByUser_Id(Integer id);
}