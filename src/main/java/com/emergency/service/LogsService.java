package com.emergency.service;

import com.emergency.model.entity.Logs;
import com.emergency.model.entity.Usuario;
import com.emergency.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogsService {

    @Autowired
    private LogsRepository logsRepository;

    public void saveLog(
            Usuario usuario,
            String action,
            String description) {

        Logs log = new Logs();

        log.setUser(usuario);
        log.setAction(action);
        log.setDescription(description);
        log.setTimestamp(LocalDateTime.now());

        logsRepository.save(log);
    }
}