package com.retail.store.util;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Retail Store Application")
                        .description("APIs for Retail Store Application")
                        .contact(new Contact()
                                .name("Vishal Soni")
                                .url("https://www.linkedin.com/in/vishal-soni-07/")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/sonivishal189/Retail-Store-Discount")
                );
    }
}