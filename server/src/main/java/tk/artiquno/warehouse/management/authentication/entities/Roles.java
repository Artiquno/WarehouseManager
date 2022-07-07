package tk.artiquno.warehouse.management.authentication.entities;

import lombok.Getter;

public enum Roles {
    CLIENT("ROLE_CLIENT"),
    WAREHOUSE_MANAGER("ROLE_WAREHOUSE_MANAGER"),
    SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN");

    @Getter
    private final String value;

    Roles(String value) {
        this.value = value;
    }
}
