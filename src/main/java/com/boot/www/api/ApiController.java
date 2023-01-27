package com.boot.www.api;

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
@RequestMapping("/api")
public class ApiController {

	@Autowired 
	private HttpSession httpSession;
	
	@Autowired
	private Gson gson;
	
	@RequestMapping(value = "/testApi1", method = {RequestMethod.POST}, produces = "application/json") 
	public ResponseEntity<String> testApi1(@RequestParam Map<String, Object> param,HttpServletRequest req, HttpServletResponse res){
		Result result = new Result();
		result.setData("test","123123");
		return ResponseEntity.ok(gson.toJson(result));
	}
	
	@RequestMapping(value = "/testApi2", method = {RequestMethod.POST}, produces = "application/json") 
	public ResponseEntity<String> testApi2(@RequestParam Map<String, Object> param,HttpServletRequest req, HttpServletResponse res){
		Result result = new Result();
		result.setData("test","54321");
		return ResponseEntity.ok(gson.toJson(result));
	}
}
