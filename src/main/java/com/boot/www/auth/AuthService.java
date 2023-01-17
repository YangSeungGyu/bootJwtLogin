package com.boot.www.auth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.www.auth.bean.UserVO;
import com.boot.www.util.Sha256;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequestMapping("/auth")
public class AuthService {
	
	
	private Map<String,Object> getUserFromDb(Map<String, Object> param){
		Set<Map<String, Object>> userList = new HashSet<Map<String,Object>>();
		Map<String,Object> user01 = new HashMap<String,Object>();
		user01.put("userId", "admin");
		user01.put("userPwd", "1234");
		user01.put("userNm", "관리자");
		user01.put("level", "S");
		Map<String,Object> user02 = new HashMap<String,Object>();
		user02.put("userId", "test");
		user02.put("userPwd", "1234");
		user02.put("userNm", "테스터");
		user02.put("level", "U");
		Map<String,Object> user03 = new HashMap<String,Object>();
		user03.put("userId", "user");
		user03.put("userPwd", "1234");
		user03.put("userNm", "홍길동");
		user03.put("level", "U");
		
		userList.add(user01);
		userList.add(user02);
		userList.add(user03);
		
		Map<String,Object> getUser = null;
		
		for(Map<String, Object> dbUser : userList){
			if(((String)dbUser.get("userId")).equals(((String)param.get("userId")))){
				getUser = dbUser;
			}
		};
		return getUser;
	}
	//=====================샘플 코드=====================
}
