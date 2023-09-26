package org.pikalovanna.zuzex_task.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_OWNER,
    ROLE_ROOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
