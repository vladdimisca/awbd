package com.awbd.project.configuration;

import com.awbd.project.service.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("mysql")
public class SecurityJdbcConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JpaUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/jobs/form/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/jobs", "/jobs/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.POST, "/jobs").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/jobs/*").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/jobs/*").hasAnyRole("ADMIN")
                .antMatchers("/product/new").hasRole("ADMIN")
                .antMatchers("/product/delete/*").hasRole("ADMIN")
                .antMatchers("/product").hasRole("ADMIN")
                .antMatchers("/product/info/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers("/product/list").hasAnyRole("GUEST", "ADMIN")
                .antMatchers("/employees/**").hasAnyRole("ADMIN")
                .and()
                .formLogin().loginPage("/login-form")
                .loginProcessingUrl("/auth")
                .failureUrl("/login-error").permitAll()
                .and()
                .logout().logoutUrl("/perform-logout")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
