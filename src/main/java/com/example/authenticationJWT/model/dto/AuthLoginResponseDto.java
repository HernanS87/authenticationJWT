package com.example.authenticationJWT.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthLoginResponseDto(String username, String message, String jwt, boolean status) {
}

/*Cuando una clase se declara como un registro, el compilador de Java genera automáticamente
ciertos métodos como el constructor, los métodos equals(), hashCode() y toString(),
basados en los componentes de datos declarados en la clase.*/
