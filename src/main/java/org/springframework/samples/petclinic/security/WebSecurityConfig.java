package org.springframework.samples.petclinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // IGNORA COMPLETAMENTE O SECURITY PARA A API E PARA O LOGIN
        return (web) -> web.ignoring()
            .requestMatchers("/api/v1/**")
            .requestMatchers("/login")
            .requestMatchers("/resources/**", "/static/**", "/webjars/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // LIBERA TUDO
            )
            // COMENTAMOS O FORM LOGIN PARA MATAR O REDIRECIONAMENTO
            // .formLogin(form -> form.permitAll())
            .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
