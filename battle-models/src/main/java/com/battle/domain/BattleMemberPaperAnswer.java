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
@Table(name="battle_member_paper_answer")
public class BattleMemberPaperAnswer {
	
	//游离状态
	public static Integer FREE_STATUS=0;
	
	//进行中
	public static Integer IN_STATUS=1;
	
	//结束
	public static Integer END_STATUS=2;
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="battle_period_member_id")
	private String battlePeriodMemberId;
	
	//扣掉爱心
	@ParamAnnotation
	@Column(name="sub_love")
	private Integer subLove;
	
	@ParamAnnotation
	@Column(name="wrong_sum")
	private Integer wrongSum;
	
	@ParamAnnotation
	@Column(name="right_sum")
	private Integer rightSum;
	
	//前进距离
	@ParamAnnotation
	@Column(name="add_distance")
	private Integer addDistance;
	
	
	//第几阶段
	@ParamAnnotation
	@Column(name="stage_index")
	private Integer stageIndex;
	
	@ParamAnnotation
	@Column(name="question_answer_id")
	private String questionAnswerId;
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	//问题总数
	@ParamAnnotation
	@Column(name="question_count")
	private Integer questionCount;
	
	//答题数量
	@ParamAnnotation
	@Column(name="answer_count")
	private Integer answerCount;
	
	@ParamAnnotation
	@Column
	private Integer process;
	
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

	public String getBattlePeriodMemberId() {
		return battlePeriodMemberId;
	}

	public void setBattlePeriodMemberId(String battlePeriodMemberId) {
		this.battlePeriodMemberId = battlePeriodMemberId;
	}
	

	public Integer getSubLove() {
		return subLove;
	}

	public void setSubLove(Integer subLove) {
		this.subLove = subLove;
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

	public Integer getAddDistance() {
		return addDistance;
	}

	public void setAddDistance(Integer addDistance) {
		this.addDistance = addDistance;
	}

	public Integer getStageIndex() {
		return stageIndex;
	}

	public void setStageIndex(Integer stageIndex) {
		this.stageIndex = stageIndex;
	}
	
	

	public String getQuestionAnswerId() {
		return questionAnswerId;
	}

	public void setQuestionAnswerId(String questionAnswerId) {
		this.questionAnswerId = questionAnswerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	
	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
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