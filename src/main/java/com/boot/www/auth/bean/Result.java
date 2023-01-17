package com.boot.www.auth.bean;

import java.util.HashMap;
import java.util.Map;

import com.boot.www.common.CommonCode;

import lombok.Data;

@Data
public class Result {
	private String resultCode;
	private String resultMsg;
	private  Map<String,Object> data;
	

	public void setData(String key,Object value) {
		data.put(key, value);
	}
	
	//디폴트 생성
	public Result() {
		CommonCode success = CommonCode.SUCCESS_NORMAL;
		this.resultCode = success.getCode();
		this.resultMsg = success.getMessage();
		this.data = new HashMap<String,Object>();
	}
	
	//커스텀 생성
	public Result(String code, String message) {
		this.resultCode = code;
		this.resultMsg = message;
		this.data = new HashMap<String,Object>();
	}
	
	//정의된 코드 생성
	public Result(CommonCode cc) {
		this.resultCode = cc.getCode();
		this.resultMsg = cc.getMessage();
		this.data = new HashMap<String,Object>();
	}
}
