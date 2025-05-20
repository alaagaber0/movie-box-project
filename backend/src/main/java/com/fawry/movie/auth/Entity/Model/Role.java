package com.fawry.movie.auth.Entity.Model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.fawry.movie.auth.Entity.Model.Permission.*;

public enum Role {
    USER(
            Set.of(
                    READ
            )
    ),

    ADMIN(
            Set.of(
                    READ,
                    UPDATE,
                    CREATE,
                    DELETE
            )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions)
    {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions()
    {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
