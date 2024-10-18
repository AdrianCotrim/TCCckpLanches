package com.fiec.ckplanches.model.Log;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; // Nome do usuário que executou a ação
    private String action;   // Descrição da ação
    private int idObject;  // Detalhes adicionais (ex: ID do pedido)
    private LocalDateTime timestamp;  // Timestamp da ação


    public Log(String username, String action, int idObject) {
        this.username = username;
        this.action = action;
        this.idObject = idObject;
        this.timestamp = LocalDateTime.now(); // Armazena o timestamp atual
    }

}
