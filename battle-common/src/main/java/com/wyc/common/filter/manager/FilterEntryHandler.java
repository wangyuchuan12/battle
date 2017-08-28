package com.wyc.common.filter.manager;
import java.lang.reflect.Method;

import com.wyc.common.session.SessionManager;

public class FilterEntryHandler {

	private FilterEntry filterEntry;
	
	private SessionManager sessionManager;
	
	private FilterEntryManager filterEntryManager;
	public FilterEntryHandler(FilterEntry filterEntry,SessionManager sessionManager,FilterEntryManager filterEntryManager){
		this.filterEntry = filterEntry;
		this.sessionManager = sessionManager;
		this.filterEntryManager = filterEntryManager;
	}
	
	public void executeListenerCallback(Method method)throws Exception{
		FilterBroadcastAnnotation broadcastAnnotation = method.getAnnotation(FilterBroadcastAnnotation.class);
		if(broadcastAnnotation!=null){
			String listenerName = broadcastAnnotation.name();
			
			filterEntryManager.callback(listenerName, sessionManager);
		}
		
	}
	
	public Object executePre()throws Exception{
		Object  handlePreReturnValue = null;
		Method[] methods = filterEntry.getFilter().getClass().getDeclaredMethods();
		for(Method method:methods){
			if(method.getName().startsWith("handlerPre")){
				Object returnValue = method.invoke(filterEntry.getFilter(), sessionManager);
				executeListenerCallback(method);
				if(method.getName().equals("handlerPre")){
					handlePreReturnValue = returnValue;
				}
			}
		}
		return handlePreReturnValue;
	}
	
	public Object executeHandler()throws Exception{
		Object returnValue = filterEntry.getFilter().handlerFilter(sessionManager);
		
		sessionManager.save(returnValue);
		
		executeListenerCallback(filterEntry.getFilter().getClass().getMethod("handlerFilter", SessionManager.class));
		
		return returnValue;
	}

	public Object executeAfter() throws Exception{
		
		Object handlerAfterReturnValue = null;
		Method[] methods = filterEntry.getFilter().getClass().getDeclaredMethods();
		for(Method method:methods){
			if(method.getName().startsWith("handlerAfter")){
				Object returnValue = method.invoke(filterEntry.getFilter(), sessionManager);
				executeListenerCallback(method);
				if(method.getName().equals("handlerAfter")){
					handlerAfterReturnValue = returnValue;
				}
			}
		}
		return handlerAfterReturnValue;
	}
}
