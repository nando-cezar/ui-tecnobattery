package com.tecnobattery.tbsystem.auth.enumerated;

public enum ApplicationUserPermission {

  GLOBAL_READ("global:read"), GLOBAL_WRITE("global:write");

  private final String permission;

  ApplicationUserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
