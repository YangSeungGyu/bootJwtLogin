package exception;



import com.boot.www.common.CommonCode;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String code;
	private String msg;
	
	
	public CustomException(CommonCode cc) {
		super(cc.toString());
		this.code = cc.getCode();
		this.msg = cc.getMessage();
		this.name = cc.name();
		
	}
}
