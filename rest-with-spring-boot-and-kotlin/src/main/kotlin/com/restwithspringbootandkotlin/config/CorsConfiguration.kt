package com.restwithspringbootandkotlin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration : WebMvcConfigurer{

    @Value("\${cors.origins}")
    private val corsOriginPatterns: String = ""

    
    override fun addCorsMappings(registry: CorsRegistry) {
        val allowedOrigins = corsOriginPatterns.split(",").toTypedArray()
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(*allowedOrigins)
            .allowCredentials(true)
    }

}