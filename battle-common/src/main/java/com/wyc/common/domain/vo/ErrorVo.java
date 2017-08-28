package com.wyc.common.domain.vo;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;
import com.wyc.common.filter.Filter;

@ParamEntityAnnotation
public class ErrorVo {
	
	@IdAnnotation
	private String id;
	@ParamAnnotation
	private String errorMsg;
	
	private Class<? extends Filter> errorFilter;
	
	private Object errorData;
	
	
	

	public Object getErrorData() {
		return errorData;
	}

	public void setErrorData(Object errorData) {
		this.errorData = errorData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Class<? extends Filter> getErrorFilter() {
		return errorFilter;
	}

	public void setErrorFilter(Class<? extends Filter> errorFilter) {
		this.errorFilter = errorFilter;
	}
	
	
	
	
}
