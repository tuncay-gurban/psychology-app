package com.example.psychology.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
      ) throws ServletException, IOException {

        // 1. Authorization header-i al
        final String authHeader = request.getHeader("Authorization");

        // 2. Header yoxdur ve ya "Bearer' ile baslamir -> sorgunu otur, filtir isini bitir
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer " sozunu kes, yalniz token qalsin
        final String token = authHeader.substring(7);
        final String email = jwtService.extractEmail(token);

        // 4. Email var ve hele authenticate olunmayibsa
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            // 5. DB-dan useri tap
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 6. Token kecerlidirse useri SecurityContexte yerlesdir
            if(jwtService.isTokenValid(token,userDetails.getUsername())){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
            // 7. Sorgunu novbeti filtere otur
        filterChain.doFilter(request,response);

    }
}
