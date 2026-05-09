package com.davidperez.proyectocomponenteselectronicosback.config;

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
                        .title("Electronic Components API")
                        .version("1.0.0")
                        .description(
                                "API REST para la gestión de componentes electrónicos pasivos y sus fabricantes. " +
                                "Permite realizar operaciones CRUD sobre PassiveComponent y Manufacturer, " +
                                "con almacenamiento en memoria.")
                        .contact(new Contact()
                                .name("DAE — Universidad de Ibagué")
                                .email("carlos.lugo@unibague.edu.co")));
    }
}
