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
@Table(name="battle_notice")
public class BattleNotice {
	
	public static final Integer MEMBER_TYPE = 0;
	
	public static final Integer ROOM_TYPE = 1;

	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="to_user")
	private String toUser;
	
	@ParamAnnotation
	@Column(name="room_id")
	private String roomId;
	
	
	//0用户答题信息有变化 1房间信息有变化
	@ParamAnnotation
	@Column
	private Integer type;
	
	@ParamAnnotation
	@Column
	private String msg;
	
	@ParamAnnotation
	@Column(name="is_read")
	private Integer isRead;
	
	//数据变化的memberId
	@ParamAnnotation
	@Column(name="member_id")
	private String memberId;
	
	
	//运动达到最终的位置
	@ParamAnnotation
	@Column
	private Integer process;
	
	
	@ParamAnnotation
	@Column(name="love_residule")
	private Integer loveResidule;
	
	@ParamAnnotation
	@Column
	private Integer score;
	
	@ParamAnnotation
	@Column(name="room_status")
	private Integer roomStatus;
	
	@ParamAnnotation
	@Column
	private Integer rank;
	
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

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	public Integer getLoveResidule() {
		return loveResidule;
	}

	public void setLoveResidule(Integer loveResidule) {
		this.loveResidule = loveResidule;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public DateTime getCreateAt() {
		return createAt;
	}
	
	public Integer getRewardBean() {
		return rewardBean;
	}

	public void setRewardBean(Integer rewardBean) {
		this.rewardBean = rewardBean;
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
