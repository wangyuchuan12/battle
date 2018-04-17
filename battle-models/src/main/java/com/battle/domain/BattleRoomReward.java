package com.battle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
@Entity
@Table(name="battle_room_reward",indexes={@Index(columnList="room_id",name="battleRoomRewardIndex")})
public class BattleRoomReward {
	
	public static final Integer REMIND_STATUS_FREE = 0;
	
	public static final Integer REMIND_STATUS_IN = 1;
	
	public static final Integer REMIND_STATUS_END = 2;

	@Id
	@IdAnnotation
	private String id;
	
	@Column(name="reward_bean")
	@ParamAnnotation
	private Integer rewardBean;
	
	
	@Column(name="reward_love")
	@ParamAnnotation
	private Integer rewardLove;
	
	@Column
	@ParamAnnotation
	private Integer rank;
	
	@Column(name="is_receive")
	@ParamAnnotation
	private Integer isReceive;
	
	//提醒状态
	@Column(name="remind_status")
	@ParamAnnotation
	private Integer remindStatus;
	
	@Column(name="receive_member_id")
	@ParamAnnotation
	private String receiveMemberId;
	
	@Column(name="room_id")
	@ParamAnnotation
	private String roomId;
	
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
	
	public Integer getRemindStatus() {
		return remindStatus;
	}

	public void setRemindStatus(Integer remindStatus) {
		this.remindStatus = remindStatus;
	}

	public Integer getRewardBean() {
		return rewardBean;
	}

	public void setRewardBean(Integer rewardBean) {
		this.rewardBean = rewardBean;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
	}

	public String getReceiveMemberId() {
		return receiveMemberId;
	}

	public void setReceiveMemberId(String receiveMemberId) {
		this.receiveMemberId = receiveMemberId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getRewardLove() {
		return rewardLove;
	}

	public void setRewardLove(Integer rewardLove) {
		this.rewardLove = rewardLove;
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
