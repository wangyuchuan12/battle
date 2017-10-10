package com.battle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.AttrEnum;
import com.wyc.annotation.AttrAnnotation;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;


@ParamEntityAnnotation
@Entity
@Table(name="battle_period")
public class BattlePeriod {
	@Id
	@IdAnnotation
	@AttrAnnotation(name=AttrEnum.periodId)
	private String id;
	
	@ParamAnnotation
	@Column(name="z_index")
	@AttrAnnotation(name=AttrEnum.periodIndex)
	private Integer index;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	//总距离
	@ParamAnnotation
	@Column(name="total_distance")
	private Integer totalDistance;
	
	@ParamAnnotation
	@Column(name="min_members")
	private Integer minMembers;
	
	@ParamAnnotation
	@Column(name="max_members")
	private Integer maxMembers;
	
	
	//每道题的进度
	@ParamAnnotation
	@Column(name="average_process")
	private Integer averageProcess;
	
	@ParamAnnotation
	@Column(name="stage_count")
	private Integer stageCount;
	
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}
	
	

	public Integer getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Integer totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Integer getMinMembers() {
		return minMembers;
	}

	public void setMinMembers(Integer minMembers) {
		this.minMembers = minMembers;
	}

	public Integer getMaxMembers() {
		return maxMembers;
	}

	public void setMaxMembers(Integer maxMembers) {
		this.maxMembers = maxMembers;
	}
	

	public Integer getStageCount() {
		return stageCount;
	}

	public void setStageCount(Integer stageCount) {
		this.stageCount = stageCount;
	}

	public Integer getAverageProcess() {
		return averageProcess;
	}

	public void setAverageProcess(Integer averageProcess) {
		this.averageProcess = averageProcess;
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
