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
@Table(name="battle")
public class Battle {
	
	//游离状态
	public static Integer FREE_STATUS = 0;
		
	//进行中状态
	public static Integer IN_STATUS = 1;
	
	@Id
	@IdAnnotation
	@AttrAnnotation(name=AttrEnum.battleId)
	private String id;
	
	//名称
	@ParamAnnotation
	@Column
	private String name;
	
	//简介
	@ParamAnnotation
	@Column
	private String instruction;
	
	//图片
	@ParamAnnotation
	@Column(name="head_img")
	private String headImg;
	
	//是否显示在主页面中
	@ParamAnnotation
	@Column(name="is_display")
	private Integer isDisplay;
	
	//是否激活状态
	@ParamAnnotation
	@Column(name="is_activation")
	private Integer isActivation;
	
	//距离
	@ParamAnnotation
	@Column
	private Integer distance;
	
	//当前期 序号
	@ParamAnnotation
	@Column(name="current_period_id")
	@AttrAnnotation(name=AttrEnum.periodId)
	private String currentPeriodId;
	
	//最大期数序号，为了可以一次递增
	@ParamAnnotation
	@Column(name="max_period_index")
	private Integer maxPeriodIndex;
	
	@ParamAnnotation
	@Column(name="default_room_id")
	private String defaultRoomId;
	
	//状态 0表示 游离 1表示进行中
	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column(name="z_index")
	private Integer index;
	
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

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getIsActivation() {
		return isActivation;
	}

	public void setIsActivation(Integer isActivation) {
		this.isActivation = isActivation;
	}


	public String getCurrentPeriodId() {
		return currentPeriodId;
	}

	public void setCurrentPeriodId(String currentPeriodId) {
		this.currentPeriodId = currentPeriodId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getMaxPeriodIndex() {
		return maxPeriodIndex;
	}

	public void setMaxPeriodIndex(Integer maxPeriodIndex) {
		this.maxPeriodIndex = maxPeriodIndex;
	}
	
	public String getDefaultRoomId() {
		return defaultRoomId;
	}

	public void setDefaultRoomId(String defaultRoomId) {
		this.defaultRoomId = defaultRoomId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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
