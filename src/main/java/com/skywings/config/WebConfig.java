package com.skywings.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Applichiamo il controllo solo alle rotte dell'amministratore
        // Escludiamo eventuali risorse statiche se necessario, ma /admin/** di solito basta
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }
}