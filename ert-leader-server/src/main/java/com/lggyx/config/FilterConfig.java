package com.lggyx.config;

import com.lggyx.filter.JwtFilter;
import com.lggyx.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Resource
    private JwtUtil jwtUtil;
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new JwtFilter(jwtUtil));
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}