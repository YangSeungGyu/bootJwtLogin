package com.boot.www.auth;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
}
