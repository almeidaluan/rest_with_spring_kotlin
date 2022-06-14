package com.restwithspringbootandkotlin.config


import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenAPIConfig {

    @Bean
    fun customOpenAPI(): OpenAPI{
        return OpenAPI()
            .info(
                Info().title("Rest API with kotlin and Spring boot 3.0.0")
                    .version("v1")
                    .description("Api Sample com kotlin")
                    .termsOfService("")
                    .license(
                        License().name("Apache 2.0")
                    )


        )
    }
}