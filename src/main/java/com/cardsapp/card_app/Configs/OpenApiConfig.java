package com.cardsapp.card_app.Configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    security = {
        @SecurityRequirement(
            name="bearerAuth"
        )
    },
    servers = {
        @io.swagger.v3.oas.annotations.servers.Server(
            description = "Contabo",
            url = "http://161.97.69.205:4444/"
        ),
        @io.swagger.v3.oas.annotations.servers.Server(
            description = "Localhost",
            url = "http://localhost:4444/"
        )
    }
)

@SecurityScheme(
    name="bearerAuth",
    description = "Bearer token used for authentication",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
    
}
