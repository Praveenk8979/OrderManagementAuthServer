package com.ordermanagement.OrderManagementAndAuthServer.security;

import com.ordermanagement.OrderManagementAndAuthServer.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    private void authenticate(String token, HttpServletRequest req) {
        String username=jwtUtil.extractUsername(token);
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader=req.getHeader("Authorization");
        String refreshToken=req.getHeader("Refresh-Token");

        if(authHeader !=null && authHeader.startsWith("Bearer ")){
            String accessToken=authHeader.substring(7);

            if(!jwtUtil.isExpired(accessToken)){
                authenticate(accessToken, req);
            }else if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                String username = jwtUtil.extractUsername(refreshToken);
                String newAccessToken = jwtUtil.generateAccessToken(username);
                res.setHeader("New-Access-Token", newAccessToken);
                authenticate(newAccessToken, req);
            }
        }
        filterChain.doFilter(req, res);
        }


    }


