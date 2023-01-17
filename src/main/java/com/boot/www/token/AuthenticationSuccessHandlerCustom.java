package com.boot.www.token;

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

import com.boot.www.auth.bean.Result;
import com.boot.www.auth.bean.UserVO;
import com.boot.www.security.UserCustomDetails;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationSuccessHandlerCustom implements AuthenticationSuccessHandler {
	
	@Autowired
	private Gson gson;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	
    	UserCustomDetails userCustomDetails = (UserCustomDetails) authentication.getPrincipal();
    //---------------------------------------------------------------------------
    UserVO user = userCustomDetails.getUser();
    //newSession.setAttribute("seq", user.getSeq());
    //newSession.setAttribute("id", user.getId());
    //newSession.setAttribute("level", user.getLevel());
    //newSession.setAttribute("fCode", user.getFCode());
    //newSession.setAttribute("sfCode", user.getSfCode());
    //newSession.setAttribute("name", user.getName());
    
    //user.setFailCnt(0);
	//mainService.updateFailCnt(user);
    response.setStatus(HttpServletResponse.SC_OK);
	Result result = new Result();
	
	//User 정보에서 필수 UserInfo로 변경
	UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userCustomDetails.getUsername(), authentication.getCredentials(), authentication.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(newAuth);
    
	 response.setContentType("Application/json; charset=UTF-8");
	 response.setStatus(HttpServletResponse.SC_OK);

	 response.getWriter().print(gson.toJson(result));
	 response.getWriter().flush();
    }
}