package com.boot.www.auth.bean;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserVO {
	private Integer rn;
	private String seq;
	private String  userId;
	private String userNm;
	private String userPwd;
	private int failCnt;
	private String state; //L:잠김 N:일반 H:휴먼
	private String auth;
	
	
}
