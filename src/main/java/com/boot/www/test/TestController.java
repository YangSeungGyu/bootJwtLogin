package com.boot.www.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired 
	private HttpSession httpSession;
	
	@RequestMapping(value = "/test1", method = {RequestMethod.GET}) 
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/test/test1");
		
		log.debug("test1");
		return mav;
	}
}
