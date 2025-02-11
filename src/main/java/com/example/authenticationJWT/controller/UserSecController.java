package com.example.authenticationJWT.controller;

import com.example.authenticationJWT.model.dto.UserSecDto;
import com.example.authenticationJWT.model.UserSec;
import com.example.authenticationJWT.service.UserSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserSecController {

    @Autowired
    private UserSecService userSecService;

    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userSecService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userSecService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserSec> getUserByName(@PathVariable String name) {
        Optional<UserSec> user = userSecService.findUserSecByUsername(name);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSecDto user) {
        UserSec newUser = userSecService.save(user);
        return ResponseEntity.ok(newUser);
    }


}