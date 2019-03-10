package com.corelogic.sc.responses;

public enum ItemStatus {

    ITEM_ACTIVE,
    ITEM_DISCONTINUED,
    ITEM_DELETED;

    public static ItemStatus getItemStatus(String status) {
        switch (status) {
            case "ITEM_DISCONTINUED":
                return ItemStatus.ITEM_DISCONTINUED;
            case "ITEM_DELETED":
                return ItemStatus.ITEM_DELETED;
            default:
                return ItemStatus.ITEM_ACTIVE;
        }
    }
}
