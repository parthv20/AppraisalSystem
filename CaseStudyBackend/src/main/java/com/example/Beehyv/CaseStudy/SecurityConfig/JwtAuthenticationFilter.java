package com.example.Beehyv.CaseStudy.SecurityConfig;

import com.example.Beehyv.CaseStudy.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // fetch token from request
        var jwtTokenOptional = getTokenFromRequest(request);

        //validate jwt token ->jwt Utils
        jwtTokenOptional.ifPresent(jwtToken->{
            if(JwtUtils.validate(jwtToken)){
                // get username from jwt token
                var usernameOptional = JwtUtils.getUserNameFromToken(jwtToken);
                usernameOptional.ifPresent(username ->{
                    //fetch userdetails
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    //create auth token
                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // set auth token to security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });


            }
        });

        //pass request and respond to next filter
        filterChain.doFilter(request,response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {

        //extract auth header
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        if(StringUtils.hasText(authHeader)&& authHeader.startsWith("Bearer "))
        {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
