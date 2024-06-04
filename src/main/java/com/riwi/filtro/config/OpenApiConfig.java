package com.riwi.filtro.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "surveys management system", version = "1.0", description = "Api rest to store and manage the survey question bank of a company"))
public class OpenApiConfig {

}