package com.auroralibrary.library.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auroralibrary.library.repositories.AdministratorRepository;
import com.auroralibrary.library.service.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter{

  @Autowired
  private TokenService tokenService;

  @Autowired
  private AdministratorRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    try {
        var token = getToken(request);

        if (token != null) {
          var subject = tokenService.getToken(token);
          var admin = repository.findByLogin(subject);
          var authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          log.info("Autenticado com sucesso!! ");
        }

        filterChain.doFilter(request, response);

     } catch (SecurityException | JWTVerificationException e) {
        log.error("Erro de autenticação: {}", e.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"erro\": \"Acesso negado\", \"mensagem\": \"" + e.getMessage() + "\"}");
    }
  }

  private String getToken(HttpServletRequest request) {
    var token = request.getHeader("Authorization");
    if (token != null) {
      return token.replace("Bearer ", "");
    }
    return null;
  }
}
