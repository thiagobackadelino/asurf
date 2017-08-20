package com.br.asurf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("/home/index");
        registry.addViewController("/").setViewName("/home/index");
        registry.addViewController("/login").setViewName("/login/index");
        registry.addViewController("/acessdenied").setViewName("/acessdenied/index");
    }
    
   @Bean(name = "dataSource")
   public DriverManagerDataSource dataSource() {
     DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
     driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
     driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/asurf");
     driverManagerDataSource.setUsername("root");
     driverManagerDataSource.setPassword("");
     return driverManagerDataSource;

 }

}
