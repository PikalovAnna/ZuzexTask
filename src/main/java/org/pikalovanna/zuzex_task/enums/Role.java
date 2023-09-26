package org.pikalovanna.zuzex_task.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    OWNER,
    ROOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
