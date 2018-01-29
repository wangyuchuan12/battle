package com.battle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="question_answer_item")
@ParamEntityAnnotation
public class QuestionAnswerItem {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="question_answer_id")
	private String questionAnswerId;
	
	@ParamAnnotation
	@Column(name="question_id")
	private String questionId;
	
	@ParamAnnotation
	@Column(name="z_index")
	private Integer index;
	
	@ParamAnnotation
	@Column(name="my_answer")
	private String myAnswer;
	
	@ParamAnnotation
	@Column(name="right_answer")
	private String rightAnswer;
	
	@ParamAnnotation
	@Column(name="my_option_id")
	private String myOptionId;
	
	@ParamAnnotation
	@Column(name="right_option_id")
	private String rightOptionId;
	
	@ParamAnnotation
	@Column(name="is_right")
	private Integer isRight;
	
	@ParamAnnotation
	@Column
	private Integer type;
	
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

	public String getQuestionAnswerId() {
		return questionAnswerId;
	}

	public void setQuestionAnswerId(String questionAnswerId) {
		this.questionAnswerId = questionAnswerId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(String myAnswer) {
		this.myAnswer = myAnswer;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}


	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getMyOptionId() {
		return myOptionId;
	}

	public void setMyOptionId(String myOptionId) {
		this.myOptionId = myOptionId;
	}

	public String getRightOptionId() {
		return rightOptionId;
	}

	public void setRightOptionId(String rightOptionId) {
		this.rightOptionId = rightOptionId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
