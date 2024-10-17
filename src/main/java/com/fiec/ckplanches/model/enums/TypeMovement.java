package com.fiec.ckplanches.model.enums;

import lombok.Getter;

@Getter
public enum TypeMovement {
    
    ENTRADA("ENTRADA"),
    SAIDA("SAIDA");

    private String tipo;

    TypeMovement(String tipo){
        this.tipo = tipo;
    }

}
