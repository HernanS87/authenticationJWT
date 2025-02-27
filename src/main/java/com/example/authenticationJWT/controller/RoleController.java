package com.example.authenticationJWT.controller;

import com.example.authenticationJWT.model.dto.RoleDto;
import com.example.authenticationJWT.model.Role;
import com.example.authenticationJWT.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllUsers() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getUserById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rolname/{value}")
    public ResponseEntity<Role> getUserByName(@PathVariable String value) {
        Optional<Role> role = roleService.findRoleByName(value);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createUser(@RequestBody RoleDto role) {
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(newRole);
    }


}