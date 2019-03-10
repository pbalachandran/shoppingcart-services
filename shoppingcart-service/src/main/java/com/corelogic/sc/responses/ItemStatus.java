package com.corelogic.sc.responses;

public enum ItemStatus {

    PRODUCT_ACTIVE,
    PRODUCT_DISCONTINUED;

    public static ItemStatus getItemStatus(String status) {
        switch (status) {
            case "PRODUCT_DISCONTINUED":
                return ItemStatus.PRODUCT_DISCONTINUED;
            default:
                return ItemStatus.PRODUCT_ACTIVE;
        }
    }
}
