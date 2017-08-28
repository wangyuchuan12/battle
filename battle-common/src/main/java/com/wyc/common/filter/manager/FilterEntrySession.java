package com.wyc.common.filter.manager;
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
	
	
	public void pointInit(){
		filterEntryManager.pointInit();
	}
	
	public void pointLeafFilterStep(){
		this.filterEntryManager.pointLeafFilterStep();
	}
	
	public void executeHandler()throws Exception{
		
		FilterEntry filterEntry = filterEntryManager.reverseSeqOut();
		
		executeHandle(filterEntry);
		
		if(!filterEntryManager.getCurrentFilterStep().isInit()){
			executeHandler();
		}else{
			executeHandle(filterEntryManager.getCurrentFilterStep().getFilterEntry());
		}
		
		sessionManager.addNotExecuteFilterClass(filterEntry.getFilter().getClass());
	}
	
	public void exectePreHandler()throws Exception{
		FilterEntry filterEntry = filterEntryManager.posSeqOut();
		
		executePre(filterEntry);
		
		if(!filterEntryManager.getCurrentFilterStep().isEnd()){
			exectePreHandler();
		}else{
			executePre(filterEntryManager.getCurrentFilterStep().getFilterEntry());
		}
	}
	
	public void executeAfterHandler()throws Exception{
		FilterEntry filterEntry = filterEntryManager.posSeqOut();
		
		executeAfter(filterEntry);
		
		if(!filterEntryManager.getCurrentFilterStep().isEnd()){
			executeAfterHandler();
		}else{
			executeAfter(filterEntryManager.getCurrentFilterStep().getFilterEntry());
		}
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
		if(!sessionManager.isEnd()&&!sessionManager.containNotExecuteFilter(filterEntry.getFilter().getClass())&&!sessionManager.isReturn()){
			return true;
		}else{
			return false;
		}
	}
}
