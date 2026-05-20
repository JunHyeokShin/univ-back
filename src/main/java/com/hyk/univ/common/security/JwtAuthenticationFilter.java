package com.hyk.univ.common.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      try {
        AuthUser authUser = this.jwtProvider.parse(token);
        AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            authUser, null,
            List.of(new SimpleGrantedAuthority("ROLE_" + authUser.role().name()))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
      catch (Exception ignored) {
        // Invalid token -> 그대로 통과. 보호된 엔드포인트에서 401 발생
      }
    }
    filterChain.doFilter(request, response);
  }

}
