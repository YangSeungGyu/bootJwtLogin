package com.boot.www.auth;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.boot.www.auth.bean.Result;
import com.boot.www.common.CommonCode;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;







@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired 
	private HttpSession httpSession;
	
	@Autowired
	private Gson gson;
	
	@Autowired 
	private AuthService authService;
	

	@RequestMapping(value = "/loginView", method = {RequestMethod.GET}) 
	public ModelAndView loginView(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/auth/loginView");
		return mav;
	}
	/*
	@RequestMapping("/login")
	public String login(@RequestParam String userId) {
		log.debug("test----------------------------");
		
		if(userId.equals("admin")) {
			Authentication authentication = new UserAuthentication(userId,null,null);
			String token = JwtTokenProvider.generateToken(authentication);
			return token;
		}
		return "error";
	}
	*/
	
	/*
	@RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = "application/json") 
	public ResponseEntity<String> login(@RequestParam Map<String, Object> param,HttpServletRequest req, HttpServletResponse res){
		Result result = new Result();
		try {
			String userId =  (String)param.get("userId");
			String userPwd =  (String)param.get("userPwd");
			log.debug("userId : ",userId);
			log.debug("userPwd : ",userPwd);
			
			Map<String, Object> user = authService.getUser(param);
			
			if(user != null) {
				Authentication authentication = new UserAuthentication(user.get("userId"),null,null);
				String token = JwtTokenProvider.generateToken(authentication);
				
				result.setData("token",token);
			}else {
				result = new Result(CommonCode.ERROR_OTHER);	
			}
			
		} catch (Exception e) {
			log.debug("error : " + e.getMessage());
			e.printStackTrace();
			result = new Result(CommonCode.ERROR_OTHER);
		}
		return ResponseEntity.ok(gson.toJson(result));
	}
	*/
	
}
