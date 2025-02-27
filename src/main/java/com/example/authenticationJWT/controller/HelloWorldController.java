package com.example.authenticationJWT.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j // esta anotación me permite loggear mensajes
@RestController
@RequestMapping("hello/")
@PreAuthorize("denyAll()")
public class HelloWorldController {

    @GetMapping("security1")
    @PreAuthorize("hasAuthority('READ')")
    public String helloWidthSec(Principal principal){
        log.info("Hola amigo 1: {}", principal.getName());
        return "Hello World width SECURITY 1 " + principal.getName();
    }

    @GetMapping("security2")
    @PreAuthorize("hasAuthority('READ')")
    public String helloWidthSec2(@AuthenticationPrincipal User user){
        log.info("Hola amigo 2: {}", user.getUsername()); // este endpoint explota cuando quiero traer la info del usuario
        return "Hello World width SECURITY 2 " + user.getUsername();
    }

    @GetMapping("security3")
    @PreAuthorize("hasAuthority('READ')")
    public String helloWidthSec3(Principal principal){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Hola amigo 3: {}", authentication.getName());
        return "Hello World width SECURITY 3 " + authentication.getName();
    }

    @GetMapping("free")
    @PreAuthorize("permitAll()")
    public String helloFree(){
        return "Hello World FREE";
    }

    @GetMapping("autenticado")
    @PreAuthorize("isAuthenticated()")
    public String helloFree2(){
        return "Hello World AUTHENTICATED";
    }
}



