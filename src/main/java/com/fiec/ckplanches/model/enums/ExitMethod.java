package com.fiec.ckplanches.model.enums;

public enum ExitMethod {

    ENTREGA("ENTREGA"),
    RETIRADA("RETIRADA");

    private String exitMethod;

    ExitMethod(String exitMethod) {
        this.exitMethod = exitMethod;
    }

    @Override
    public String toString() {
        return exitMethod;
    }
}
