package com.fiec.ckplanches.model.enums;

public enum Category {
    
    BEBIDAS("BEBIDAS"),
    LANCHES("LANCHES");

    private String category;
    
    Category(String category){
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

}
