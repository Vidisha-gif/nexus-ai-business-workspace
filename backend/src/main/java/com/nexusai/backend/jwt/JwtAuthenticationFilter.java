package com.nexusai.backend.jwt;

import com.nexusai.backend.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        System.out.println("===== JWT FILTER RUNNING =====");


        String authHeader = request.getHeader("Authorization");

        System.out.println("AUTH HEADER : " + authHeader);


        // No token present
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("NO JWT TOKEN FOUND");

            filterChain.doFilter(request, response);
            return;
        }


        String jwt = authHeader.substring(7);

        System.out.println("JWT TOKEN : " + jwt);


        String email;


        try {

            email = jwtService.extractUsername(jwt);

            System.out.println("EMAIL FROM TOKEN : " + email);


        } catch (Exception e) {

            System.out.println("JWT PARSING FAILED : " + e.getMessage());

            filterChain.doFilter(request, response);
            return;
        }



        if (email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {


            UserDetails userDetails;


            try {

                userDetails =
                        userDetailsService.loadUserByUsername(email);


                System.out.println("USER FOUND : " 
                        + userDetails.getUsername());


            } catch (Exception e) {


                System.out.println("USER NOT FOUND : "
                        + e.getMessage());

                filterChain.doFilter(request, response);
                return;
            }



            if (jwtService.isTokenValid(jwt, userDetails)) {


                System.out.println("JWT TOKEN VALID");


                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );


                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );


                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);


                System.out.println("AUTHENTICATION SET SUCCESSFULLY");


            } else {

                System.out.println("JWT TOKEN INVALID");

            }

        }


        filterChain.doFilter(request, response);
    }
}