package com.battle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@Entity
@Table(name="question_answer")
@ParamEntityAnnotation
public class QuestionAnswer {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column
	private String questions;
	
	@ParamAnnotation
	@Column
	private Integer type;
	
	@ParamAnnotation
	@Column
	private String targetId;
	
	@ParamAnnotation
	@Column(name="wrong_sum")
	private Integer wrongSum;
	
	@ParamAnnotation
	@Column(name="right_sum")
	private Integer rightSum;
	
	
	@Column(name = "create_at")
	@ParamAnnotation
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
	
    @Column(name = "update_at")
    @ParamAnnotation
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Integer getWrongSum() {
		return wrongSum;
	}

	public void setWrongSum(Integer wrongSum) {
		this.wrongSum = wrongSum;
	}

	public Integer getRightSum() {
		return rightSum;
	}

	public void setRightSum(Integer rightSum) {
		this.rightSum = rightSum;
	}

	public DateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(DateTime createAt) {
		this.createAt = createAt;
	}

	public DateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(DateTime updateAt) {
		this.updateAt = updateAt;
	}
}
