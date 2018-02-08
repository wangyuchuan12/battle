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
@Table(name="battle_question_factory_item")
public class BattleQuestionFactoryItem {
	
	//审核中状态
	public final static Integer STATUS_AUDIT = 0;
	
	//审核通过状态
	public final static Integer STATUS_PASS = 1;
	
	//拒绝状态
	public final static Integer STATUS_REJECT = 2;
	
	@Id
	@IdAnnotation
	private String id;
	
	
	@ParamAnnotation
	@Column(name="factory_id")
	private String factoryId;
	
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	
	@ParamAnnotation
	@Column(name="battle_subject_id")
	private String battleSubjectId;
	
	
	@ParamAnnotation
	@Column(name="question_id")
	private String questionId;
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column
	private String question;
	
	@ParamAnnotation
	@Column(name="img_url")
	private String imgUrl;
	
	@ParamAnnotation
	@Column(name="is_img")
	private Integer isImg;
	
	@ParamAnnotation
	@Column
	private String answer;
	
	@ParamAnnotation
	@Column(name="fill_words")
	private String fillWords;
	
	@ParamAnnotation
	@Column(name="is_del")
	private Integer isDel;
	
	@ParamAnnotation
	@Column
	private String options;
	
	@ParamAnnotation
	@Column
	private Integer type;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="stage_id")
	private String stageId;
	
	@ParamAnnotation
	@Column(name="audit_user_id")
	private String auditUserId;
	
	@ParamAnnotation
	@Column(name="reward_bean")
	private Integer rewardBean;
	
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

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public String getBattleSubjectId() {
		return battleSubjectId;
	}

	public void setBattleSubjectId(String battleSubjectId) {
		this.battleSubjectId = battleSubjectId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getIsImg() {
		return isImg;
	}

	public void setIsImg(Integer isImg) {
		this.isImg = isImg;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getFillWords() {
		return fillWords;
	}

	public void setFillWords(String fillWords) {
		this.fillWords = fillWords;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
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
