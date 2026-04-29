package com.inmohub.fsbo.ingestor.service.infrastructure.configs;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // se recupera el contexto de la petición actual
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                // se propaga el token original
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    template.header("Authorization", authHeader);
                }

                // se propagan las cabeceras inyectadas por el gateway
                String userId = request.getHeader("X-User-Id");
                if (userId != null) {
                    template.header("X-User-Id", userId);
                }

                String userRole = request.getHeader("X-User-Role");
                if (userRole != null) {
                    template.header("X-User-Role", userRole);
                }
            }
        };
    }
}