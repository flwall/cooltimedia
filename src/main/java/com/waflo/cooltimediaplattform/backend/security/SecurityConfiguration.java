package com.waflo.cooltimediaplattform.backend.security;

import com.waflo.cooltimediaplattform.backend.beans.RoleFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.MappedInterceptor;

@EnableWebSecurity()
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LogoutHandler logoutHandler;
    @Autowired
    private UserSession userSession;

    public SecurityConfiguration(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
                .and().
                csrf().disable()
//                .requestCache().requestCache(new CustomRequestCache()).and()          //idt we need this cache

                // Restrict access to our application.
                .authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                .antMatchers("/movie/**", "/audio/**", "/document/**", "/").permitAll()
                .antMatchers("/admin/**").permitAll()       //dont know how to handle RBAC with Spring Security, so doing it manually
                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().oauth2Login()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(logoutHandler);
    }
    @Bean
    public FilterRegistrationBean<RoleFilter> loginFilter() {
        var registration =
                new FilterRegistrationBean<>(new RoleFilter(this.userSession));
        registration.addUrlPatterns("/admin/*");
        return registration;
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(

                // Vaadin Flow static resources
                "/VAADIN/**",
                "/imgs/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}