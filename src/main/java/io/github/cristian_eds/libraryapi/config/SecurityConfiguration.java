package io.github.cristian_eds.libraryapi.config;

import io.github.cristian_eds.libraryapi.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler loginSocialSuccessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer ->
                        configurer.loginPage("/login").permitAll())
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(oauth2 -> {
                    oauth2.successHandler(loginSocialSuccessHandler).loginPage("/login");
                })
                .oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    //CONFIGURA PREFIX ROLE
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    // Cofigura no JWT prefix SCOPE
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return jwtConverter;
    }
}
