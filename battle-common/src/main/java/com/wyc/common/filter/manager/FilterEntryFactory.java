package com.wyc.common.filter.manager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wyc.common.filter.Filter;

public class FilterEntryFactory {
	Class<? extends Filter> filterClass;
	FilterEntry filterEntry;
	FilterStep filterStep;
	
	FilterEntryManager filterEntryManager;
	
	AutowireCapableBeanFactory factory;
	

	public FilterEntryFactory(FilterEntryManager filterEntryManager ,Class<? extends Filter> filterClass,AutowireCapableBeanFactory factory)throws Exception {

		
		this.filterEntryManager = filterEntryManager;
		
		this.filterClass = filterClass;
		
		this.factory = factory;
		
	}
	
	public FilterStep instance()throws Exception{
		this.filterEntry = instanceFilterEntry(filterClass);
		
		initCallback(filterClass);
		
		this.filterStep = instanceFilterStep(filterEntryManager,filterEntry);
		
		filterStep.setInit(true);
		filterEntryManager.setInitFilterStep(filterStep);
		
		FilterStep endStep = filterEntryManager.getCurrentFilterStep();
		filterEntryManager.setEndFilterStep(endStep);
		
		endStep.setEnd(true);
		
		filterEntryManager.setCurrentFilterStep(this.filterStep);
		
		
		return this.filterStep;
	}
	
	private void initCallback(Class<? extends Filter> filterClass)throws Exception{
		Method[] methods = filterClass.getDeclaredMethods();
		for(Method method:methods){
			FilterListenerAnnotation filterListenerAnnotation = method.getAnnotation(FilterListenerAnnotation.class);
			if(filterListenerAnnotation!=null){
				String listenerName = filterListenerAnnotation.name();
				FilterListenerCallback filterListenerCallback = new FilterListenerCallback(this.filterEntry.getFilter(), method);
				filterEntryManager.addListenerCallback(listenerName, filterListenerCallback);
			}
		}
	}
	
	private FilterEntry instanceFilterEntry(Class<? extends Filter> filterClass)throws Exception{
		Filter filter = filterClass.newInstance();
		factory.autowireBean(filter);
		filterEntry = new FilterEntry();
		Field field = FilterEntry.class.getDeclaredField("filter");
		field.setAccessible(true);
		field.set(filterEntry, filter);
		return filterEntry;
	}
	
	private FilterStep instanceFilterStep(FilterEntryManager filterEntryManager,FilterEntry filterEntry)throws Exception{
		
		Filter filter = filterEntry.getFilter();
		
		FilterStep filterStep = new FilterStep();
		
		filterStep.setFilterEntry(filterEntry);

		filterEntryManager.setCurrentFilterStep(filterStep);
		
		
		List<Class<? extends Filter>> dependClasses = filter.dependClasses();
		if(dependClasses==null||dependClasses.size()==0){
			filterStep.setLeaf(true);
		}else{
			
			FilterStep prevFilterStep = null;
			List<FilterStep> childrenFilterStep = new ArrayList<>();
			for(Class<? extends Filter> dependClass:dependClasses){
				
				FilterEntry dependFilterEntry = instanceFilterEntry(dependClass);
				
				FilterStep dependFilterStep = instanceFilterStep(filterEntryManager,dependFilterEntry);
				if(prevFilterStep==null){
					dependFilterStep.setFirst(true);
				}
				childrenFilterStep.add(dependFilterStep);
				
				dependFilterStep.setPrevFilterStep(prevFilterStep);
				
				if(prevFilterStep!=null){
					prevFilterStep.setNextFilterStep(dependFilterStep);
				}
				
				dependFilterStep.setParentFilterStep(filterStep);
				
				prevFilterStep = dependFilterStep;
			}
			prevFilterStep.setLast(true);
			
			filterStep.setChildrenFilterStep(childrenFilterStep);
			
			filterStep.setLeaf(false);
		}
		return filterStep;
	}
}
