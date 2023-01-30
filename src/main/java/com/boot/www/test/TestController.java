package com.boot.www.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.boot.www.auth.bean.Result;
import com.boot.www.common.CommonCode;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private Gson gson;

	@Autowired 
	private HttpSession httpSession;
	
	@GetMapping("/test1") 
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/test/test1");
		
		log.debug("test1");
		return mav;
	}
	
	
	//@PreAuthorize("hasRole(ROLE_A)")
	@GetMapping("/pageTest") 
	public ModelAndView pageTest(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/test/pageTest");
		
		return mav;
	}
	
	@PostMapping("/selectPageList") 
	public ResponseEntity<String> selectPageList(@RequestParam Map<String, Object> param,HttpServletRequest req, HttpServletResponse res){
		Result result = new Result();
		try {
			int pageNum = Integer.parseInt(param.get("pageNum").toString());
			int perNum = Integer.parseInt(param.get("perNum").toString());
			if(pageNum == 0) pageNum = 1;
			if(perNum == 0) perNum = 10;
			int startNum = (pageNum-1)*perNum;
			
			param.put("startNum", startNum);
			param.put("perNum", perNum);
			
			
			int totalCnt = 100;//db
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			
			Map<String,String> map1= new HashMap<String,String>();
			map1.put("title", "1223232323sfw");
			list.add(map1);
			
			 result.setData("list", list);
			 result.setData("startNum", startNum);
			 result.setData("perNum", perNum);
			 result.setData("pageNum", pageNum);
			 result.setData("totalCnt", totalCnt);
		} catch (Exception e) {
			log.debug("error : " + e.getMessage());
			e.printStackTrace();
			result = new Result(CommonCode.ERROR_OTHER);
		}
		return ResponseEntity.ok(gson.toJson(result));
	}
	
	
	
}
