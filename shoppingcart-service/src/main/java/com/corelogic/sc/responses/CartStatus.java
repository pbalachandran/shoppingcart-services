package com.corelogic.sc.responses;

public enum CartStatus {

    ACTIVE,
    COMPLETED;

    public static CartStatus getCartStatus(String status) {
        switch (status) {
            case "COMPLETED":
                return CartStatus.COMPLETED;
            default:
                return CartStatus.ACTIVE;
        }
    }
}
