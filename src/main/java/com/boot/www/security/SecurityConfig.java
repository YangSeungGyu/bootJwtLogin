package com.boot.www.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsUtils;

import com.boot.www.auth.bean.UserCustomDetails;
import com.boot.www.token.JwtAuthenticationFilter;
import com.boot.www.token.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	/*
	spring security 필터 및 환경 설정
	*/
	private final UserCustomDetails userCustomDetails;
	
    private final CorsConfig corsConfig;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    
    //UserDetailsService > UserCustomDetails > UsernamePasswordAuthenticationFilter
    

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/","/css/**", "/js/**", "/images/**");

    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();// csrf protection를 해제 jwt는 서버에 인증 정보를 저장하지 않는다
        
        http.authorizeRequests()
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .antMatchers("/","/main","/auth/**","/test/pageTest","/test/selectPageList").permitAll()
		.anyRequest().authenticated();//이건  BasicAuthenticationFilter 에서 에러 캐치는 되는데 그냥 넘김 
        
        http.addFilter(corsConfig.corsFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable() // 기본 httpBasic는 암호화되지 않아 취약하다 jwt에서는 사용하지 않는다. 
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler) //권한이 적절하지 않음
                .authenticationEntryPoint(authenticationEntryPoint); //토큰 없거나 인증되지 않음.
               
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandlerCustom authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerCustom();
    }

    @Bean
    public AuthenticationFailureHandlerCustom authenticationFailureHandler() {
        return new AuthenticationFailureHandlerCustom();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean());
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return jwtAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManagerBean(), userCustomDetails);
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
