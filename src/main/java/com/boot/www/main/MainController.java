package com.boot.www.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

	@Autowired 
	private HttpSession httpSession;
	
	@RequestMapping(value = "", method = {RequestMethod.GET}) 
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res){
		log.debug("test111");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping(value = "main", method = {RequestMethod.GET}) 
	public ModelAndView main(HttpServletRequest req, HttpServletResponse res){
		log.debug("test222");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/main/main");
		return mav;
	}
	
	
	@RequestMapping(value = "user/userMain", method = {RequestMethod.GET}) 
	public ModelAndView userMain(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/userMain");
		return mav;
	}
	
}
