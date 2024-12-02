package com.online_restaurant.backend.security;

import com.online_restaurant.backend.model.Enum.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LogoutService logoutService;
    @Autowired
    private UserDeatilServiceImp userDeatilServiceImp;
    @Autowired
    private UserDetailPasswordServiceImp userDetailPasswordServiceImp;



//    @Bean("encoder")
//    public PasswordEncoder getEncoder(){
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//        return encoder;
//    }

    @Bean
    public DaoAuthenticationProvider getAuthenticatinProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDeatilServiceImp);
        provider.setUserDetailsPasswordService(userDetailPasswordServiceImp);
        return provider;
    }



    @Bean
    public SecurityFilterChain filterConfig(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(auth ->{
                    auth.requestMatchers(HttpMethod.GET,"/user/get/**").permitAll().
                            requestMatchers(HttpMethod.GET,"/user/getall").hasRole(Role.ADMIN.name()).
                            requestMatchers(HttpMethod.POST,"/user/save").permitAll().
                            requestMatchers(HttpMethod.PATCH,"/user/update").permitAll();

                        auth.requestMatchers(HttpMethod.POST,"/food/save").hasRole(Role.ADMIN.name()).
                                requestMatchers(HttpMethod.GET,"/food/getall").permitAll().
                                requestMatchers(HttpMethod.GET,"/food/get/**").permitAll().
                                requestMatchers(HttpMethod.PATCH,"/food/update").hasRole(Role.ADMIN.name()).
                                requestMatchers(HttpMethod.DELETE,"/food/delete").hasRole(Role.ADMIN.name());

                        auth.requestMatchers(HttpMethod.POST,"/order/save").hasRole(Role.USER.name()).
                                requestMatchers(HttpMethod.GET,"/order/get/**").permitAll().
                                requestMatchers(HttpMethod.GET,"/order/getall").permitAll().
                                requestMatchers(HttpMethod.DELETE,"/order/delete/**").
                                hasAnyAuthority(Role.ADMIN.name(),Role.USER.name()).
                                requestMatchers(HttpMethod.POST,"/auth/**").permitAll();

                        auth.requestMatchers(HttpMethod.GET,"/user/get/**").permitAll().
                                requestMatchers(HttpMethod.POST,"/user/save").permitAll().
                                requestMatchers(HttpMethod.GET,"/user/getall").hasRole(Role.ADMIN.name()).
                                requestMatchers(HttpMethod.PATCH,"/user/update").hasRole(Role.USER.name());

                    try {
                        http.logout(logOut ->{
                            logOut.logoutUrl("/api/logout").
                                    addLogoutHandler(logoutService).
                                    logoutSuccessHandler((requast,respond,authentication) -> SecurityContextHolder.clearContext());
                        });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return http.build();

    }
}
