package tk.artiquno.warehouse.management.entities;

import lombok.Getter;

public enum OrderStatus {
    CREATED("CREATED"),
    AWAITING_APPROVAL("AWAITING_APPROVAL"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED"),
    UNDER_DELIVERY("UNDER_DELIVERY"),
    FULFILLED("FULFILLED"),
    CANCELLED("CANCELLED");

    @Getter
    private final String status;
    OrderStatus(String status) {
        this.status = status;
    }
}
