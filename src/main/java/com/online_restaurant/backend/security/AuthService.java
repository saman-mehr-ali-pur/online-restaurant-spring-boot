package com.online_restaurant.backend.security;

import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.UserRepo;
import com.online_restaurant.backend.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthService extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authentication");

        if(token == null  || jwtService.isExpired(token)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        token = token.substring(8).trim();

        String username = jwtService.getClaims(token,"issue",String.class);
        String password = jwtService.getClaims(token,"pass",String.class);
        User user = new User();
        user.setUsername(username);
        user = userRepo.findByUsername(user);
        if(user.getPassword().equals(password)){
            UserDetailImp userDetail = new UserDetailImp();
            userDetail.setUsername(user.getUsername());
            userDetail.setPassword(user.getPassword());
            userDetail.setRoles(List.of(new SimpleGrantedAuthority(user.getRole().name())));

            UsernamePasswordAuthenticationToken authToken = new
                    UsernamePasswordAuthenticationToken(userDetail,password,userDetail.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request,response);


        }

    }
}
