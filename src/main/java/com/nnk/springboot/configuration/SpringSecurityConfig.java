package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    UserDetailsService service;

    /**
     * Configuring password encryption.
     *
     * @return a BCryptPasswordEncoder for password encryption
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Custom Access Denied Handler.
     *
     * @return a CustomAccessDenied as access denied handler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDenied();
    }

    /**
     * Custom authentication success handler.
     *
     * @return a CustomAuthenticationSuccessHandler as authentication success handler
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method customizes the HttpSecurity object, specifying authorization and authentication
     * rules, form login, and various other security settings.
     * </p>
     *
     * @param http the HttpSecurity object to be configured
     * @return the configured SecurityFilterChain
     * @throws Exception if any error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers( "/admin/**", "/app/secure/article-details","/user/**").hasAuthority("ADMIN")
                .requestMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").authenticated()
                .requestMatchers(
                        "/",
                        "/login",
                        "/app/error",
                        "/app/error/connection",
                        "/css/**"
                )
                .permitAll()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .rememberMe()
                .key("secretKey")
                .tokenValiditySeconds(1209600);

        return http.build();
    }

    /**
     * Configures the authentication manager builder.
     *
     * @param auth AuthenticationManagerBuilder object
     * @throws Exception if an error occurs
     */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }
}