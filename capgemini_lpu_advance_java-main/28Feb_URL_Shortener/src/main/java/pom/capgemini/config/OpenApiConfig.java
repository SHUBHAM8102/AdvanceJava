package pom.capgemini.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("URL Shortener Service API")
                        .version("1.0.0")
                        .description("A production-style URL Shortener REST API built with Spring Boot, "
                                + "Spring Data JPA, PostgreSQL, and documented with Swagger/OpenAPI. "
                                + "Submit long URLs and receive short, unique aliases that redirect to the original address.")
                        .contact(new Contact()
                                .name("Shashwat")
                                .email("shashwat@capgemini.com"))
                );
    }
}

