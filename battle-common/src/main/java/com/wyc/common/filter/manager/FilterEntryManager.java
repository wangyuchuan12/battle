package com.wyc.common.filter.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyc.common.session.SessionManager;

public class FilterEntryManager {

	private FilterStep currentFilterStep;
	
	private FilterStep initFilterStep;
	
	private FilterStep endFilterStep;

	
	private FilterStep branchFilterStep;
	
	
	private Map<String, List<FilterListenerCallback>> callbacks = new HashMap<>();
	
	public void addListenerCallback(String name , FilterListenerCallback filterListenerCallback){
		List<FilterListenerCallback> filterListenerCallbacks = callbacks.get(name);
		if(filterListenerCallbacks==null){
			filterListenerCallbacks = new ArrayList<>();
			filterListenerCallbacks.add(filterListenerCallback);
			callbacks.put(name, filterListenerCallbacks);
		}else{
			filterListenerCallbacks.add(filterListenerCallback);
		}
	}
	
	public void callback(String name,SessionManager sessionManager)throws Exception{
		List<FilterListenerCallback> filterListenerCallbacks = callbacks.get(name);
		if(filterListenerCallbacks!=null&&filterListenerCallbacks.size()>0){
			for(FilterListenerCallback filterListenerCallback:filterListenerCallbacks){
				filterListenerCallback.callback(sessionManager);
			}
		}
	}
	

	public FilterStep getCurrentFilterStep() {
		return currentFilterStep;
	}

	public void setCurrentFilterStep(FilterStep currentFilterStep) {
		this.currentFilterStep = currentFilterStep;
	}

	public FilterEntry currentFilterEntry(){
		
		return currentFilterStep.getFilterEntry();
	}
	
	public FilterEntry nextFilterEntry(){
		
		FilterStep nextFilterStep = currentFilterStep.getNextFilterStep();
		if(nextFilterStep!=null){
			setCurrentFilterStep(nextFilterStep);
			return nextFilterStep.getFilterEntry();
		}else{
			return null;
		}
	}
	
	public FilterEntry prevFilterEntry(){
		FilterStep preFilterStep = currentFilterStep.getPrevFilterStep();
		if(preFilterStep!=null){
			setCurrentFilterStep(preFilterStep);
			return preFilterStep.getFilterEntry();
		}else{
			return null;
		}
	}
	
	public FilterEntry intoFilterEntry(){
		
		List<FilterStep> childrenFilterStep = currentFilterStep.getChildrenFilterStep();
		
		if(childrenFilterStep!=null&&childrenFilterStep.size()>0){
			FilterStep childFilterStep = childrenFilterStep.get(0);
			setCurrentFilterStep(childFilterStep);
			return childFilterStep.getFilterEntry();
		}
		return null;
	}
	
	public FilterEntry returnFilterEntry(){
		
		FilterStep parentFilterStep = currentFilterStep.getParentFilterStep();
		if(parentFilterStep!=null){
			setCurrentFilterStep(parentFilterStep);
			return parentFilterStep.getFilterEntry();
		}
		return null;
	}
	
	
	
	public FilterStep getInitFilterStep() {
		return initFilterStep;
	}

	public void setInitFilterStep(FilterStep initFilterStep) {
		this.initFilterStep = initFilterStep;
	}

	public FilterStep getEndFilterStep() {
		return endFilterStep;
	}

	public void setEndFilterStep(FilterStep endFilterStep) {
		this.endFilterStep = endFilterStep;
	}

	public FilterEntry pointInit(){
		this.currentFilterStep = initFilterStep;
		return currentFilterStep.getFilterEntry();
	}
	
	public FilterEntry pointEnd(){
		this.currentFilterStep = endFilterStep;
		return currentFilterStep.getFilterEntry();
	}
}
