package com.boot.www.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.www.auth.bean.UserVO;
import com.boot.www.security.UserCustomDetails;
import com.boot.www.util.Sha256;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {
    //private final UserRepository userRepository; -jpa interface구현할꺼면 사용
	
	@Autowired    
	private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	log.debug("login-2");
    	UserVO user = null;
    	
    	//mapper에서 데이터 가져와서 확인.
    	if(username.equals("admin")) {
    		user = new UserVO();
    		user.setUserId("admin");
    		//user.setUserPwd(passwordEncoder.encode("1234"));
    		user.setUserPwd(passwordEncoder.encode(Sha256.encoder("1234")));
    		user.setAuth("A");
    		user.setFailCnt(0);
    		user.setState("S");
    	} else {
    		//이게 없으면 사용자가 없을 시 nullpoint exception으로 넘어감
    		throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    	}
    	
    	
    	
    	UserCustomDetails result = new UserCustomDetails(user);
    	
    	//자동 검증로직 실행된다.
    	//spring security AbstractUserDetailsAuthenticationProvider 에있는 authenticate 에서 패스워드 검증 한다.
        return result;
    }
    
    
    
   
}