package io.github.cristian_eds.libraryapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "LibraryApi",
                version = "1.0",
                contact = @Contact(
                        name = "Cristian Eduardo",
                        email = "cristian.santos0308@gmail.com"
                )
        )
)
public class OpenApiConfiguration {
}
