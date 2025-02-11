package com.example.authenticationJWT.service;

import com.example.authenticationJWT.dto.RoleDto;
import com.example.authenticationJWT.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findRoleByName(String name);
    Role save(RoleDto roleDto);
    void deleteById(Long id);
    Role update(RoleDto roleDto);

}
