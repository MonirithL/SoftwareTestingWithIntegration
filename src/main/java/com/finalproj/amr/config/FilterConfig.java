package com.finalproj.amr.config;

import com.finalproj.amr.middleware.JwtAuthFilter;
import com.finalproj.amr.utils.JwtUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> loggingFilter() {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(new JwtUtils()));
        registrationBean.addUrlPatterns("/api/*"); // Apply filter to API routes
        return registrationBean;
    }
}
