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

@ParamEntityAnnotation
@Entity
@Table(name="battle_question_review")
public class BattleQuestionReview {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="question_factory_item_id")
	private String questionFactoryItemId;
	
	@ParamAnnotation
	@Column(name="battle_question_id")
	private String battleQuestionId;
	
	@ParamAnnotation
	@Column(name="battle_subject_id")
	private String battleSubjectId;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column(name="reward_bean")
	private Integer rewardBean;
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
	
	@ParamAnnotation
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionFactoryItemId() {
		return questionFactoryItemId;
	}

	public void setQuestionFactoryItemId(String questionFactoryItemId) {
		this.questionFactoryItemId = questionFactoryItemId;
	}

	public String getBattleQuestionId() {
		return battleQuestionId;
	}

	public void setBattleQuestionId(String battleQuestionId) {
		this.battleQuestionId = battleQuestionId;
	}

	public String getBattleSubjectId() {
		return battleSubjectId;
	}

	public void setBattleSubjectId(String battleSubjectId) {
		this.battleSubjectId = battleSubjectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getRewardBean() {
		return rewardBean;
	}

	public void setRewardBean(Integer rewardBean) {
		this.rewardBean = rewardBean;
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
