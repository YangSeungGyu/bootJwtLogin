package com.boot.www.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.boot.www.auth.bean.UserCustomDetails;
import com.boot.www.auth.bean.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenUtil {
	/*
	 security 로그인 필터 + JWT토큰 발생
	*/
	
	static final String JWT_SECRET = "mySecretKey!@";
	 private static final int JWT_ACESS_EXPIRATION_MS = 10*1000; //1분
	 private static final int JWT_REFRESH_EXPIRATION_MS = 604800000;

   
	//ACESS JWT - 요청용 짧은 주기 Refresh JWT를 이용하여 재발급 1시간
    public static String createAccessToken(UserCustomDetails userCustomDetails){
    	  Date now = new Date();
  		Date expiryDate = new Date(now.getTime()+JWT_ACESS_EXPIRATION_MS);
  		String jwt_secret =  JWT_SECRET;
          String jwtToken = Jwts.builder()
  				 .setSubject(userCustomDetails.getUsername())
  				 .setIssuedAt(new Date())
  				 .setExpiration(expiryDate)
  				 .claim("userId", userCustomDetails.getUser().getUserId())
  				 .claim("auth", userCustomDetails.getUser().getAuth())
  				 .signWith(SignatureAlgorithm.HS512, jwt_secret) //SignatureAlgorithm.HS256
  				 .compact(); 
    	return jwtToken;
    }
    
    //Refresh JWT 재발급용 긴 주기, DB저장 실패시 재로그인 필요 2주
    public static String createRefreshToken(){
  	  Date now = new Date();
  	  Date expiryDate = new Date(now.getTime()+JWT_REFRESH_EXPIRATION_MS);
        String jwtToken =  Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // SignatureAlgorithm.HS256
        .compact();
  	return jwtToken;
  }

}