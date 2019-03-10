package com.corelogic.sc.responses;

public enum ItemStatus {

    ITEM_ACTIVE,
    ITEM_DISCONTINUED,
    ITEM_REMOVED;

    public static ItemStatus getItemStatus(String status) {
        switch (status) {
            case "ITEM_DISCONTINUED":
                return ItemStatus.ITEM_DISCONTINUED;
            case "ITEM_REMOVED":
                return ItemStatus.ITEM_REMOVED;
            default:
                return ItemStatus.ITEM_ACTIVE;
        }
    }
}
