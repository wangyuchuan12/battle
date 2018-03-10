package com.battle.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
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
@ParamEntityAnnotation
@Entity
@Table(name="battle_member_paper_answer",indexes={@Index(columnList="question_answer_id,battle_period_member_id",name="battleMemberPaperAnswerIndex")})
public class BattleMemberPaperAnswer  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	
	//通过需要答对数量
	@ParamAnnotation
	@Column(name="pass_count")
	private Integer passCount;
	
	//答题数量
	@ParamAnnotation
	@Column(name="answer_count")
	private Integer answerCount;
	
	@ParamAnnotation
	@Column
	private Integer process;
	
	@ParamAnnotation
	@Column(name="start_index")
	private Integer startIndex;
	
	@ParamAnnotation
	@Column(name="end_index")
	private Integer endIndex;
	
	//该答题是否通过
	@ParamAnnotation
	@Column(name="is_pass")
	private Integer isPass;
	
	//通过奖励豆子数量
	@ParamAnnotation
	@Column(name="pass_reward_bean")
	private Integer passRewardBean;
	
	//本次答题获取了多少智慧豆
	@ParamAnnotation
	@Column(name="this_reward_bean")
	private Long thisRewardBean;
	
	
	//是否已经同步数据
	@ParamAnnotation
	@Column(name="is_sync_data")
	private Integer isSyncData;
	
	
	@ParamAnnotation
	@Column(name="right_add_score")
	private Integer rightAddScore;
	
	@ParamAnnotation
	@Column(name="wrong_sub_score")
	private Integer wrongSubScore;
	
	@ParamAnnotation
	@Column(name="full_right_add_score")
	private Integer fullRightAddScore;
	
	@ParamAnnotation
	@Column
	private Integer score;
	
	@ParamAnnotation
	@Column
	private Integer exp;
	
	@ParamAnnotation
	@Column(name="is_receive")
	private Integer isReceive;
	
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
	
	public Integer getPassCount() {
		return passCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}
	
	

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	
	public Integer getPassRewardBean() {
		return passRewardBean;
	}

	public void setPassRewardBean(Integer passRewardBean) {
		this.passRewardBean = passRewardBean;
	}
	
	public Long getThisRewardBean() {
		return thisRewardBean;
	}

	public void setThisRewardBean(Long thisRewardBean) {
		this.thisRewardBean = thisRewardBean;
	}

	public Integer getIsSyncData() {
		return isSyncData;
	}

	public void setIsSyncData(Integer isSyncData) {
		this.isSyncData = isSyncData;
	}

	public Integer getRightAddScore() {
		return rightAddScore;
	}

	public void setRightAddScore(Integer rightAddScore) {
		this.rightAddScore = rightAddScore;
	}

	public Integer getWrongSubScore() {
		return wrongSubScore;
	}

	public void setWrongSubScore(Integer wrongSubScore) {
		this.wrongSubScore = wrongSubScore;
	}

	public Integer getFullRightAddScore() {
		return fullRightAddScore;
	}

	public void setFullRightAddScore(Integer fullRightAddScore) {
		this.fullRightAddScore = fullRightAddScore;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}
	
	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public Integer getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
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
