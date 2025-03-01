package com.example.authenticationJWT.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API HELLO",
                description = "This app says hello jajajaj",
                termsOfService = "www.sayhello.com/terms_and_conditions",
                version = "1.0.0",
                contact = @Contact(
                        name = "Hernán Santarelli",
                        url = "https://sayhello.com",
                        email = "hernansantarelli@gmail.com"
                ),
                license = @License(
                        name = "Standard Software Use License for sayHello",
                        url = "www.sayhello.com/licence"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "http://sayhello:8080"
                )
        }
)
public class SwaggerConfig {
}
