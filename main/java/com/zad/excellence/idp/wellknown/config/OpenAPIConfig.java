package com.zad.excellence.idp.wellknown.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
					.title("API Documentation")
					.version("1.0")
					 .description("This is API Documentation for Well-Known service.")
                );
    }
}

