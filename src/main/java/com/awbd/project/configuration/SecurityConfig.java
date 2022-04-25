package com.awbd.project.configuration;

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
@Profile("h2")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin@awbd.com")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN")
                .and()
                .withUser("guest@awbd.com")
                .password(passwordEncoder().encode("12345"))
                .roles("GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/login.do*").permitAll()
                // jobs
                .antMatchers("/jobs/form/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/jobs", "/jobs/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.POST, "/jobs").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/jobs/*").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/jobs/*").hasAnyRole("ADMIN")
                // cars
                .antMatchers("/cars/form/**").hasAnyRole("GUEST")
                .antMatchers(HttpMethod.GET, "/cars", "/cars/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.POST, "/cars").hasAnyRole("GUEST")
                .antMatchers(HttpMethod.DELETE, "/cars/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/cars/*").hasAnyRole("GUEST")
                // employees
                .antMatchers("/employees/**").hasAnyRole("ADMIN")
                // appointments
                .antMatchers("/appointments/form/**").hasAnyRole("GUEST")
                .antMatchers(HttpMethod.GET, "/appointments", "/appointments/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.POST, "/appointments").hasAnyRole("GUEST")
                .antMatchers(HttpMethod.DELETE, "/appointments/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/appointments/*").hasAnyRole("GUEST")
                // users
                .antMatchers(HttpMethod.GET, "/users/form").permitAll()
                .antMatchers(HttpMethod.GET, "/users/*", "/users/current").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/form/*").hasAnyRole("GUEST", "ADMIN")
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
