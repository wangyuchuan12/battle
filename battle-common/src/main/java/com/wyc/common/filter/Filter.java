package com.wyc.common.filter;
import java.util.List;
import java.util.Map;

import com.wyc.common.session.SessionManager;


public abstract class Filter {
	
	private List<Filter> depends;
	
	private SessionManager filterManager;
	
	

	public List<Filter> getDepends() {
		return depends;
	}

	public void setDepends(List<Filter> depends) {
		this.depends = depends;
	}


	

	abstract public Object handlerFilter(SessionManager sessionManager)throws Exception;
	
	abstract public Object handlerPre(SessionManager sessionManager)throws Exception;
	
	public String toUrl(String url,Map<String, Object> attribute){
		this.filterManager.setEnd(true);
		this.filterManager.setReturnValue(url);
		this.filterManager.setReturnAttribute(attribute);
		
		return url;
	}
	
	
	
	abstract public List<Class<? extends Filter>> dependClasses();

	public abstract Object handlerAfter(SessionManager sessionManager);
}
