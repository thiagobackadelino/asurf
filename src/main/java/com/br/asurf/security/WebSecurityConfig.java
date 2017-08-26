package com.br.asurf.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Autowired
	 DataSource dataSource;
	 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/","/static/**","/resources/", "/webjars/**").permitAll()
                .antMatchers("/home").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll().defaultSuccessUrl("/home")
                .and()
            .logout()
                .permitAll().
        		logoutUrl("/logout"). 
        		logoutSuccessUrl("/login")
        		.and()
        		.exceptionHandling().accessDeniedPage("/acessdenied");
        
    }
    
	
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
     .usersByUsernameQuery(
      "select nome,senha, ativo from usuarios where nome=?")
     .authoritiesByUsernameQuery(
      "select u.nome, r.nome from usuarios u join usuarios_roles ur on u.id = ur.usuarios_id join roles r on r.id = ur.roles_id where u.nome=?");
    } 
}
