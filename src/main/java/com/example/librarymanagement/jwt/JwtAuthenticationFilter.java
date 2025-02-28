package com.example.librarymanagement.jwt;

import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("User Authorities: " + auth.getAuthorities());
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        System.out.println("Received JWT Token: " + jwtToken);
        username = jwtService.extractUsername(jwtToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("🔹 Extracted Username from Token: " + username);

            User userDetails = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                System.out.println("✅ JWT Token is valid for user: " + username);

                List<SimpleGrantedAuthority> authorities = userDetails.getRoles()
                        .stream().map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());

                System.out.println("🔹 Extracted Authorities: " + authorities);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("🚀 Authentication Set in SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());
            } else {
                System.out.println("❌ JWT Token is invalid for user: " + username);
            }
        }


        filterChain.doFilter(request, response);
    }
}
