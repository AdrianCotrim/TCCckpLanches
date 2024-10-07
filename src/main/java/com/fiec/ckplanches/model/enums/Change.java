package com.fiec.ckplanches.model.enums;

public enum Change {

    SIM("SIM"),
    NAO("NAO");

    private String change;

    Change(String change) {
        this.change = change;
    }

    public String getChange() {
        return this.change;
    }
}