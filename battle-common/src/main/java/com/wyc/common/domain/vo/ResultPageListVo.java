package com.wyc.common.domain.vo;

import java.util.List;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
public class ResultPageListVo {

	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	private int page;
	
	@ParamAnnotation
	private int size;
	
	@ParamAnnotation
	private Long totalSize;
	
	@ParamAnnotation
	private List<?> datas;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}
	
	
}
