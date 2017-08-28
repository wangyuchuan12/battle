package com.wyc.common.filter.manager;

import java.util.List;

public class FilterStep {
	private FilterEntry filterEntry;
	private FilterStep parentFilterStep;
	private List<FilterStep> childrenFilterStep;
	private FilterStep prevFilterStep;
	private FilterStep nextFilterStep;
	
	
	//初始化
	private boolean isInit;
	
	//结束
	private boolean isEnd;
	
	//当前列队最后一个
	private boolean isLast;
	
	//是否有子节点
	private boolean isLeaf;
	
	//当前列队第一个
	private boolean isFirst;

	public FilterEntry getFilterEntry() {
		return filterEntry;
	}

	public void setFilterEntry(FilterEntry filterEntry) {
		this.filterEntry = filterEntry;
	}

	public FilterStep getParentFilterStep() {
		return parentFilterStep;
	}

	public void setParentFilterStep(FilterStep parentFilterStep) {
		this.parentFilterStep = parentFilterStep;
	}

	public List<FilterStep> getChildrenFilterStep() {
		return childrenFilterStep;
	}

	public void setChildrenFilterStep(List<FilterStep> childrenFilterStep) {
		this.childrenFilterStep = childrenFilterStep;
	}

	public FilterStep getPrevFilterStep() {
		return prevFilterStep;
	}

	public void setPrevFilterStep(FilterStep prevFilterStep) {
		this.prevFilterStep = prevFilterStep;
	}

	public FilterStep getNextFilterStep() {
		return nextFilterStep;
	}

	public void setNextFilterStep(FilterStep nextFilterStep) {
		this.nextFilterStep = nextFilterStep;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
}
