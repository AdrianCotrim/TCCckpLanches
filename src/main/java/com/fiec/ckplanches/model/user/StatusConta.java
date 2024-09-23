package com.fiec.ckplanches.model.user;

public enum StatusConta {
    
    ATIVO("ATIVO"),
    INATIVO("INATIVO");

    private String status;

    StatusConta(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
