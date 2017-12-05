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
@Table(name="battle_dan_task")
public class BattleDanTask {
	
	//闯关类型
	public static final Integer PASS_POINT_TYPE = 0;
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column
	private Integer type;

	
	@ParamAnnotation
	@Column(name="dan_id")
	private String danId;
	
	@ParamAnnotation
	@Column(name="goal_score")
	private Integer goalScore;
	
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="z_index")
	private Integer index;
	
	//获取智慧豆
	@ParamAnnotation
	@Column(name="reward_bean")
	private Integer rewardBean;
	
	//获取经验值
	@ParamAnnotation
	@Column(name="reward_exp")
	private Integer rewardExp;
	
	@ParamAnnotation
	@Column(name="button_name")
	private String buttonName;
	
	@ParamAnnotation
	@Column
	private String name;
	
	@ParamAnnotation
	@Column
	private String instruction;
	
	@ParamAnnotation
	@Column(name="project_id")
	private String projectId;
	
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDanId() {
		return danId;
	}

	public void setDanId(String danId) {
		this.danId = danId;
	}

	public Integer getGoalScore() {
		return goalScore;
	}

	public void setGoalScore(Integer goalScore) {
		this.goalScore = goalScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public Integer getRewardBean() {
		return rewardBean;
	}

	public void setRewardBean(Integer rewardBean) {
		this.rewardBean = rewardBean;
	}

	public Integer getRewardExp() {
		return rewardExp;
	}

	public void setRewardExp(Integer rewardExp) {
		this.rewardExp = rewardExp;
	}
	

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
