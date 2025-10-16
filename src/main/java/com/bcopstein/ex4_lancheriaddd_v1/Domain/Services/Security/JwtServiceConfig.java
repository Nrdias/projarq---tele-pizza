package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtServiceConfig {
    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}

