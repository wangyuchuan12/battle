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
@Table(name="battle_dan_task_user")
public class BattleDanTaskUser {
	
	//未开始执行
	public static final Integer STATGUS_FREE = 0;
	
	//执行中
	public static final Integer STATGUS_IN = 1;
	
	//执行完成
	public static final Integer STATGUS_COMPLETE = 2;
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
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="z_index")
	private Integer index;
	
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column(name="room_id")
	private String roomId;
	
	@ParamAnnotation
	@Column(name="room_score")
	private Integer roomScore;
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getRoomScore() {
		return roomScore;
	}

	public void setRoomScore(Integer roomScore) {
		this.roomScore = roomScore;
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
