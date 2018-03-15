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
@Table(name="battle_step_domain")
public class BattleStepDomain {
	@Id
	@IdAnnotation
	private String id;

	@Column(name="room_id")
	@ParamAnnotation
	private String roomId;
	
	@Column(name="flag_id")
	@ParamAnnotation
	private String flagId;
	
	@Column(name="flag_img")
	@ParamAnnotation
	private String flagImg;
	
	@Column(name="member_id")
	@ParamAnnotation
	private String memberId;
	
	@Column(name="cost_bean")
	@ParamAnnotation
	private Integer costBean;
	
	@Column(name="is_del")
	@ParamAnnotation
	private Integer isDel;
	
	@Column(name="step_index")
	@ParamAnnotation
	private Integer stepIndex;
	
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getFlagId() {
		return flagId;
	}

	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}

	public String getFlagImg() {
		return flagImg;
	}

	public void setFlagImg(String flagImg) {
		this.flagImg = flagImg;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getCostBean() {
		return costBean;
	}

	public void setCostBean(Integer costBean) {
		this.costBean = costBean;
	}

	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
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
