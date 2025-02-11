package com.example.authenticationJWT.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserSecDto {

    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private boolean enabled;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;

    private Set<String> roles = new HashSet<>();
}
