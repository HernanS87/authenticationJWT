package com.example.authenticationJWT.model.dto;

import jakarta.validation.constraints.NotBlank;


public record AuthLoginRequestDto(@NotBlank String username, @NotBlank String password) {
}


/*Cuando una clase se declara como un registro, el compilador de Java genera automáticamente
ciertos métodos como el constructor, getters, los métodos equals(), hashCode() y toString(),
basados en los componentes de datos declarados en la clase.*/