package com.boot.www.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.boot.www.auth.bean.UserVO;
import com.boot.www.security.UserCustomDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserCustomDetails userCustomDetails;
    
    
   //???
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserCustomDetails userCustomDetails) {
        super(authenticationManager);
        this.userCustomDetails = userCustomDetails;
    }
    

    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	log.debug("로그인 후 인증!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
            //DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("secretKey")).build().verify(jwtToken);
            //Long id = decodedJWT.getClaim("id").asLong();
            try {
   			 Jwts.parser().setSigningKey(JwtAuthenticationFilter.JWT_SECRET).parseClaimsJws(jwtToken);
   			// return true;
	   		 } catch(SignatureException e) {
	   			 log.error("Invalid JWT signature",e);
	   		 } catch(MalformedJwtException e) {
	   			 log.error("Invalid JWT token",e);
	   		 } catch(ExpiredJwtException e) {
	   			 log.error("Expired JWT token",e);
	   		 } catch(UnsupportedJwtException e) {
	   			 log.error("Unsupported JWT token",e);
	   		 } catch(IllegalArgumentException e) {
	   			 log.error("JWT claims string is empty",e);
	   		 }
            
            /*
            UserVO user = userRepository.findById(id).
                    orElseThrow(()->new JWTVerificationException("유효하지 않은 토큰입니다."));
			*/
            UserVO user = userCustomDetails.getUser();
            if(userCustomDetails.getUser() == null) {
            	throw new RuntimeException();	
            }
            
            
            UserCustomDetails userCustomDetails = new UserCustomDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
            		userCustomDetails, null, userCustomDetails.getAuthorities());


            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (RuntimeException e) {
            log.warn("[JwtAuthorizationFilter] token 파싱 실패 : {}", e.getMessage());
        }
        chain.doFilter(request, response);
    }
}