package com.boot.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.boot.www.auth.PrincipalDetailsService;
import com.boot.www.auth.bean.Result;
import com.boot.www.auth.bean.UserCustomDetails;
import com.boot.www.auth.bean.UserVO;
import com.boot.www.token.TokenUtil;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationSuccessHandlerCustom implements AuthenticationSuccessHandler {
	/*
	security 로그인 성공
	*/
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private PrincipalDetailsService principalDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	
    UserCustomDetails userCustomDetails = (UserCustomDetails) authentication.getPrincipal();
    //---------------------------------------------------------------------------
    UserVO user = userCustomDetails.getUser();
    response.setStatus(HttpServletResponse.SC_OK);
	Result result = new Result();
	
	//User 정보에서 필수 UserInfo로 변경
	UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userCustomDetails.getUsername(), authentication.getCredentials(), authentication.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(newAuth);
    
    //재발급 인증용 토큰 생성
    String jwtRefreshToken = "Bearer " + TokenUtil.createRefreshToken(); 
    principalDetailsService.insertRefreshToken(userCustomDetails.getUser(),jwtRefreshToken);// db에 저장
    result.setData("jwtRefreshToken",jwtRefreshToken);
    
	 response.setContentType("Application/json; charset=UTF-8");
	 response.setStatus(HttpServletResponse.SC_OK);

	 response.getWriter().print(gson.toJson(result));
	 response.getWriter().flush();
    }
}