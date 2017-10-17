package com.wyc.common.filter.manager;
import java.util.List;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class FilterEntrySession {

	private SessionManager sessionManager;
	
	private FilterEntryFactory filterEntryFactory;
	
	private FilterEntryManager filterEntryManager;
	
	public FilterEntrySession(SessionManager sessionManager,Class<? extends Filter> filterClass,AutowireCapableBeanFactory factory)throws Exception{
		this.sessionManager = sessionManager;
		
		this.filterEntryManager = new FilterEntryManager();
		
		this.filterEntryFactory = new FilterEntryFactory(filterEntryManager, filterClass,factory);
		
		this.filterEntryFactory.instance();	
	}
	
	public void execute()throws Exception{
		
		FilterStep firstFilterStep = filterEntryManager.getInitFilterStep();
		
		if(firstFilterStep!=null){
			
			executeHandle(firstFilterStep);
		}else{

		}
		
	}
	
	
	
	public void executeHandle(FilterStep filterStep)throws Exception{
		
		executePre(filterStep.getFilterEntry());
		
		if(filterStep.getChildrenFilterStep()!=null){
			for(FilterStep childFilterStep:filterStep.getChildrenFilterStep()){
				executeHandle(childFilterStep);
			}
		}
		
		executeHandle(filterStep.getFilterEntry());
	}

	
	private void executeAfter(FilterEntry filterEntry)throws Exception {
			FilterEntryHandler filterEntryHandler = new FilterEntryHandler(filterEntry, sessionManager,filterEntryManager);
			filterEntryHandler.executeAfter();
	}

	private void executePre(FilterEntry filterEntry)throws Exception{
		if(isExecuteAble(filterEntry)){
			FilterEntryHandler filterEntryHandler = new FilterEntryHandler(filterEntry, sessionManager,filterEntryManager);
			filterEntryHandler.executePre();
		}
	}
	
	
	private void executeHandle(FilterEntry filterEntry)throws Exception{

		if(isExecuteAble(filterEntry)){
			FilterEntryHandler filterEntryHandler = new FilterEntryHandler(filterEntry, sessionManager,filterEntryManager);
			filterEntryHandler.executeHandler();
		}
	}
	
	private boolean isExecuteAble(FilterEntry filterEntry){
		boolean b = false;
		if(!sessionManager.isEnd()&&!sessionManager.containNotExecuteFilter(filterEntry.getFilter().getClass())&&!sessionManager.isReturn()){
			b = true;
		}else{
			b = false;
		}
		return b;
	}
}
