package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.BlackListRepository;
import com.example.MyBookShopApp.security.jwt.JWTBlackList;
import com.example.MyBookShopApp.security.jwt.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookstoreUserDetailsService bookstoreUserDetailsService;

    private final BlackListRepository blackListRepository;

    private final JWTRequestFilter filter;

    public SecurityConfig(BookstoreUserDetailsService bookstoreUserDetailsService, BlackListRepository blackListRepository, JWTRequestFilter filter) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.blackListRepository = blackListRepository;
        this.filter = filter;
    }

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(bookstoreUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/my","/profile").authenticated()//.hasRole("USER")
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/signin").failureUrl("/signin")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/signin").deleteCookies("token").addLogoutHandler((((httpServletRequest, httpServletResponse, authentication) -> {
                    for (Cookie cookie: httpServletRequest.getCookies()){
                        if (cookie.getName().equals("token")){
                            JWTBlackList jwtBlackList = new JWTBlackList(cookie.getValue());
                            blackListRepository.save(jwtBlackList);
                        }
                    }
                })))
                .and().oauth2Login()
                .and().oauth2Client();

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
