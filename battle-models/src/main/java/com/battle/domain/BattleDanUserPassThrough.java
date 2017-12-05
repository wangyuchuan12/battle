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
@Table(name="battle_dan_user_pass_through")
public class BattleDanUserPassThrough {
	
	//进行中
	public static final Integer STAGTUS_IN = 1;
	
	//失败
	public static final Integer STAGTUS_FAIL = 2;
	
	//成功
	public static final Integer STAGTUS_SUCCESS = 3;
	
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="battle_dan_user_id")
	private String battleDanUserId;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column
	private Integer score;
	
	@ParamAnnotation
	@Column(name="room_id")
	private String roomId;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="project_id")
	private String projectId;
	
	@ParamAnnotation
	@Column(name="goal_score")
	private Integer goalScore;

	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column(name="is_room_takepart")
	private Integer isRoomTakepart;
	
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

	public String getBattleDanUserId() {
		return battleDanUserId;
	}

	public void setBattleDanUserId(String battleDanUserId) {
		this.battleDanUserId = battleDanUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	
	public Integer getIsRoomTakepart() {
		return isRoomTakepart;
	}

	public void setIsRoomTakepart(Integer isRoomTakepart) {
		this.isRoomTakepart = isRoomTakepart;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
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
