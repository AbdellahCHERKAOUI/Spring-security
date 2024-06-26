package com.example.springsecurity.config;

import com.example.springsecurity.dao.UserDao;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAthFilter extends OncePerRequestFilter
{
    private final UserDao userDao;
    private final JwtUtils jwtUtils;


    public JwtAthFilter(UserDao userDao, JwtUtils jwtUtils) {
        this.userDao = userDao;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwtToken;
        if (authHeader==null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken =authHeader.substring(7);
        userEmail=jwtUtils.extractUsername(jwtToken);

        if (userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDao.findUserByEmail(userEmail);

            if(jwtUtils.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
