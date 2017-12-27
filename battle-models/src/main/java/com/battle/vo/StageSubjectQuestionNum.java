package com.battle.vo;

import java.io.Serializable;

public class StageSubjectQuestionNum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String stageId;
	
	private String subjectId;
	
	private Integer num;

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
