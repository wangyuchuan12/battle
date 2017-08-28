package com.wyc.common.filter.manager;


import java.lang.reflect.Method;

import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class FilterListenerCallback {

	private Filter filter;
	private Method method;
	public FilterListenerCallback(Filter filter,Method method) {
		this.filter = filter;
		this.method = method;
	}
	public void callback(SessionManager sessionManager)throws Exception{
		method.invoke(filter, sessionManager);
	}
}
