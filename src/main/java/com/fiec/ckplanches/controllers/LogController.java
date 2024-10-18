package com.fiec.ckplanches.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiec.ckplanches.model.Log.Log;
import com.fiec.ckplanches.repositories.LogRepository;

@RestController
@RequestMapping("/logs")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogRepository logRepository; // Injete o repositório

    @GetMapping("/api/logs")
    public List<Log> getLogs() {
        return logRepository.findAll(); // Retorna todos os logs do banco de dados
    }

    public void logAction(String username, String action, int idObject) {
        Log log = new Log(username, action, idObject);
        logRepository.save(log);
        logger.info("{}: {}", action, idObject); // Log também no console
    }
}
