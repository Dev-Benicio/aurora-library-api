package com.auroralibrary.library.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auroralibrary.library.security.SecurityFilter;

/*
* Essa é uma classe de Configuração que muda a Política de Segurança do Spring Security
* Ao iniciar a aplicação, automaticamente ela é chamada e faz essa alteração
*/
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private SecurityFilter securityFilter;

  /*
  * Esse método modifica a politica de segurança da aplicação, desabilitando o csrf
  * definindo a politica de segurança para stateless, e a ordem dos Filtros.
  */
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http
//        .csrf(csrf -> csrf.disable())
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .authorizeHttpRequests(authorize -> authorize
//            .requestMatchers(HttpMethod.POST, "/login").permitAll()
//            .requestMatchers("/swagger-ui/**", "/swagger-ui.html" ,"/api-docs/**").permitAll()
//            .anyRequest().authenticated())
//        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//        .build();
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(handling -> handling
                    .authenticationEntryPoint((request, response, authException) -> {
                      response.setStatus(HttpStatus.UNAUTHORIZED.value());
                      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                      response.getWriter().write("{\"error\": \"Token inválido ou ausente\"}");
                    }))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html" ,"/api-docs/**").permitAll()
                    .anyRequest().authenticated())
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
