package com.wyc.common.domain.vo;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation(type=ParamEntityAnnotation.REQUEST_TYPE)
public class ResultVo {
	
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	private boolean success;
	
	@ParamAnnotation
	private String errorMsg;
	
	@ParamAnnotation
	private String msg;
	
	@ParamAnnotation
	private Object data;


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}
