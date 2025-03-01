package com.example.authenticationJWT.controller;

import com.example.authenticationJWT.model.dto.AuthLoginRequestDto;
import com.example.authenticationJWT.model.dto.AuthLoginResponseDto;
import com.example.authenticationJWT.service.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Controller for Authentication")
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Operation(
            summary = "Login User",
            description = "Authenticate a user and return the authentication token along with user details.",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication request with username and password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthLoginRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthLoginResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDto> login(@RequestBody @Valid AuthLoginRequestDto userRequest) {
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

}

