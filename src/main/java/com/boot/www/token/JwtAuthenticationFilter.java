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
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	//로그인 및 토큰 발생
	
	static final String JWT_SECRET = "mySecretKey!@";
	 private static final int JWT_EXPIRATION_MS = 604800000;

    private final AuthenticationManager authenticationManager;
    
    @Autowired
	private Gson gson;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	log.debug("login-1");
    	// /login 호출 시 실행
    	String userId = null;
        try {
        	//json 타입 바인드
            ObjectMapper om = new ObjectMapper();
            UserVO paramLoginVo = om.readValue(request.getInputStream(), UserVO.class); 
            userId=  paramLoginVo.getUserId();
            
            //UserCustomDetails 내용으로 인증 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                           new UsernamePasswordAuthenticationToken(paramLoginVo.getUserId(), paramLoginVo.getUserPwd());

            Authentication authentication = null;
            //정상 유저 확인
            try {
            	 //UserDetailsService 상속 class 동작 - 패스워드 검증까지 해서 넘어온다
            	//구분하고 싶거나 이슈 처리를 분리하고 싶다면 implements AuthenticationProvider  상속해서 사용해야함. 아니면
            	//아이디,패스워드 틀림 모두 동일하게BadCredentialsException: 자격 증명에 실패하였습니다. 리턴됨
                authentication =  authenticationManager.authenticate(authenticationToken);
                
                UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
                UserVO dbUser = userDetails.getUser();
                
            	if(dbUser.getFailCnt() > 5) {
            		throw new LockedException("패스워드 횟수를 초과 하였습니다.");
            	}else if(dbUser.getState().equals("L")) {        		
            		throw new LockedException("잠김 처리된 사용자 입니다.");
            	}else if(dbUser.getState().equals("N")) {        		
            		throw new DisabledException("미승인 사용자 입니다.");
            	}else if(dbUser.getState().equals("H")) {        		
            		throw new DisabledException("휴면 처리된 사용자 입니다.");
            	} else if(!dbUser.getState().equals("S")) {
            		//정의되지 않은 상태값
            		throw new DisabledException("사용 할 수 없는 사용자 입니다.");
            	}   
            } catch(BadCredentialsException e) {
            	//패스워드 실패 횟수 증가
            	// int comitCnt = userService.updateLoginFailCnt(userId);
            	int comitCnt = 1;
            	if(comitCnt > 0) {
            		log.debug("id :["+userId +"] 패스워드가 다릅니다.");
            	}else {
            		log.debug("["+userId +"] 아이디가 없습니다.");
            	}
            	
            	this.getFailureHandler().onAuthenticationFailure(request, response, e);
            } catch(AuthenticationException e) {
            	e.printStackTrace();
    			this.getFailureHandler().onAuthenticationFailure(request, response, e);
    		} catch(Exception e) {
    			e.printStackTrace();
    			this.getFailureHandler().onAuthenticationFailure(request, response, new BadCredentialsException("에러 발생"));
    		}
            return authentication;

        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	//UserDetailsService 종료 후 성공이면 실행 
    	log.debug("login-success");	
    	
    	UserCustomDetails userCustomDetails = (UserCustomDetails) authResult.getPrincipal();
    	
    	//토큰 생성
        Date now = new Date();
		Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION_MS);
		String jwt_secret =  JWT_SECRET;
        String jwtToken = Jwts.builder()
				 .setSubject(userCustomDetails.getUsername())
				 .setIssuedAt(new Date())
				 .setExpiration(expiryDate)
				 .claim("userId", userCustomDetails.getUser().getUserId())
				 .claim("auth", userCustomDetails.getUser().getAuth())
				 .signWith(SignatureAlgorithm.HS512, jwt_secret)
				 .compact(); 
        
        //db에 넣어야 하나???
        
        response.addHeader("Authorization", "Bearer " + jwtToken);
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

}