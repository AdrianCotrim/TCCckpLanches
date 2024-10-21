package com.fiec.ckplanches.model.Log;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_log")
    private Long id;
    @Column(name="nome_usuario")
    private String username; // Nome do usuário que executou a ação
    @Column(name="acao")
    private String action;   // Descrição da ação
    @Column(name="id_objeto")
    private int idObject;  // Detalhes adicionais (ex: ID do pedido)
    @Column(name="timestamp")
    private LocalDateTime timestamp;  // Timestamp da ação


    public Log(String username, String action, int idObject) {
        this.username = username;
        this.action = action;
        this.idObject = idObject;
        this.timestamp = LocalDateTime.now(); // Armazena o timestamp atual
    }

}
