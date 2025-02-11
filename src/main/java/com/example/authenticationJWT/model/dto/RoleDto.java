package com.example.authenticationJWT.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RoleDto {

    private Long id;
    private String name;
    private Set<String> permissions = new HashSet<>();
}
