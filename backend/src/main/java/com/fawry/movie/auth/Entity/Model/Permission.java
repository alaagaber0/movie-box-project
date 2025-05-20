package com.fawry.movie.auth.Entity.Model;

public enum Permission {
    READ("read"),
    UPDATE("update"),
    CREATE("create"),
    DELETE("delete");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
