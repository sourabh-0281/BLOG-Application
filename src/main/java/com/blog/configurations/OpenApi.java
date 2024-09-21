package com.blog.configurations;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration 
@OpenAPIDefinition(
info = @Info(
		        title = "My API",
		        version = "v1",
		        description = "This is a sample API",
		        contact = @Contact(name = "John Doe", email = "john.doe@example.com"),
		        license = @License(name = "Apache 2.0", url = "http://springdoc.org")
            ),

security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    scheme = "bearer",
	    bearerFormat = "JWT"
	)
public class OpenApi {

}