package com.thebongcoder.blog.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    // inject required dependencies
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get Jwt(TOKEN) from HTTP request
        String token = getJwtFromToken(request);

        // validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // get Username from token
            String userName = jwtTokenProvider.getUserNameFromJwt(token);

            //load user associate with token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // set Spring security
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }


    // Bearer <access token>
    private String getJwtFromToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
