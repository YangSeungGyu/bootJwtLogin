package com.boot.www.common;

public enum CommonCode {
	SUCCESS_NORMAL("S001", "처리되었습니다."),
	
	ERROR_DUPLICATE("E011", "중복 에러"),
	ERROR_OTHER("E099", "기타에러");

	private String code;
	private String msg;

	private CommonCode(final String code, final String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.msg;
	}

	@Override
	public String toString() {
		return this.name() + "(\"" + this.code + "\", \"" + this.msg + "\");";
	}
}
