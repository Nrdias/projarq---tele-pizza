package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {
    private final JwtAuthInterceptor jwtAuthInterceptor;

    @Autowired
    public JwtInterceptorConfig(JwtAuthInterceptor jwtAuthInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protege endpoints autenticados conforme o enunciado
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/menu/**", "/pedido/**");
    }
}

