package com.boot.www.token;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.boot.www.auth.PrincipalDetailsService;
import com.boot.www.auth.bean.UserCustomDetails;
import com.boot.www.auth.bean.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	//토큰 체크 및 권한 재발행

    private final UserCustomDetails userCustomDetails;
    
    
    @Autowired 
	private PrincipalDetailsService principalDetailsService;
    
    
   //???
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserCustomDetails userCustomDetails) {
        super(authenticationManager);
        this.userCustomDetails = userCustomDetails;
    }
    

    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
        	//토큰 인증url이 아닐경우
            chain.doFilter(request, response);
            return;
        } else if( jwtHeader.replaceAll("Bearer", "").length() < 3){
        	//토큰 형식은 있는데 토큰값이 없을경우.
        	throw new RuntimeException();	
        }
        
        String requestUrl = request.getRequestURL().toString();
        log.debug("["+requestUrl+"] 인증필요 토큰 체크");

        try {
            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
           
            //인증 만료되었을 경우 여기서 ExpiredJwtException 에러
            Claims jwtClaims = getAllClaims(jwtToken);  //{sub=admin, iat=1673944246, exp=1674549046, userId=admin}
            String userId = String.valueOf(jwtClaims.get("userId"));
            String auth = String.valueOf(jwtClaims.get("auth"));
            try {
   			 Jwts.parser().setSigningKey(JwtAuthenticationFilter.JWT_SECRET).parseClaimsJws(jwtToken);
   			// return true;
	   		 } catch(SignatureException e) {
	   			 log.error("Invalid JWT signature",e);
	   		 } catch(MalformedJwtException e) {
	   			 //토큰이 변경됨.
	   			 log.error("Invalid JWT token",e);
	   		 } catch(ExpiredJwtException e) {
	   			 log.error("Expired JWT token",e);
	   		 } catch(UnsupportedJwtException e) {
	   			 log.error("Unsupported JWT token",e);
	   		 } catch(IllegalArgumentException e) {
	   			 log.error("JWT claims string is empty",e);
	   		 }
            
            
            UserVO checkUserCustomDetails = userCustomDetails.getUser();
            if(userCustomDetails.getUser() == null) {
            	throw new RuntimeException();	
            }
            
            //String 권한 재생성.(토큰방식이라 매번 셋팅이 필요)
            UserVO user= new UserVO();
            user.setUserId(userId);
            user.setAuth(auth);
            UserCustomDetails userCustomDetails = new UserCustomDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
            		userCustomDetails, null, userCustomDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        } catch (ExpiredJwtException e) {
        	e.printStackTrace();
            log.warn("[ExpiredJwtException] 토큰 유효기간 만료 : {}", e.getMessage());
        }  catch (RuntimeException e) {
        	e.printStackTrace();
            log.warn("[JwtAuthorizationFilter] token 파싱 실패 : {}", e.getMessage());
        }
        chain.doFilter(request, response);
    }
    
    private Claims getAllClaims(String token) {
        log.info("getAllClaims token = {}", token);
        return Jwts.parser()
                .setSigningKey(JwtAuthenticationFilter.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}