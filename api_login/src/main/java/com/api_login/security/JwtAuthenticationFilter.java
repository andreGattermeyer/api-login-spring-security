package com.api_login.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.api_login.service.JwtService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth Header: " + authHeader);
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            System.out.println("Token extraído: " + jwt);
            
            try {
                if (jwtService.validateToken(jwt)) {
                    String username = jwtService.getUsernameFromToken(jwt);
                    System.out.println("Username do token: " + username);
                    
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("Autoridades do usuário: " + userDetails.getAuthorities());
                    
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                    
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Set authentication before continuing with filter chain
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Autenticação configurada com sucesso: " + 
                        SecurityContextHolder.getContext().getAuthentication());
                } else {
                    System.out.println("Token inválido");
                }
            } catch (Exception e) {
                System.out.println("Erro na validação do token: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("Nenhum token JWT encontrado no header");
        }
        
        filterChain.doFilter(request, response);
    }
}