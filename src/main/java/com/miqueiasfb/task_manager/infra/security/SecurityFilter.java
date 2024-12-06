package com.miqueiasfb.task_manager.infra.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  @Autowired
  TokenService tokenService;

  @Autowired
  UserRepository userRepository;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    var id = tokenService.validateToken(token);

    if (id != null) {
      User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
      var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
      var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("token")) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
