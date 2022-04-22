package com.awbd.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        resolver.setDefaultErrorView("error_default");
        resolver.setExceptionAttribute("exception");

        return resolver;
    }

}
