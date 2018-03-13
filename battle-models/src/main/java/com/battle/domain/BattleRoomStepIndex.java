package com.battle.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="battle_room_step_index")
public class BattleRoomStepIndex implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static Integer BEAN_REWARD_TYPE = 0;
	public final static Integer LOVE_REWARD_TYPE = 1;
	
	@Id
	@IdAnnotation
	private String id;
	
	
	@Column(name="room_id")
	@ParamAnnotation
	private String roomId;
	
	@Column(name="reward_type")
	@ParamAnnotation
	private Integer rewardType;
	
	
	@Column(name="bean_num")
	@ParamAnnotation
	private Integer beanNum;
	
	@Column(name="love_num")
	@ParamAnnotation
	private Integer loveNum;
	
	@Column(name="step_index")
	@ParamAnnotation
	private Integer stepIndex;
	
	@Column(name="img_url")
	@ParamAnnotation
	private String imgUrl;
	
	@Column(name="is_big")
	@ParamAnnotation
	private Integer isBig;
	
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

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getBeanNum() {
		return beanNum;
	}

	public void setBeanNum(Integer beanNum) {
		this.beanNum = beanNum;
	}

	public Integer getLoveNum() {
		return loveNum;
	}

	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}

	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getIsBig() {
		return isBig;
	}

	public void setIsBig(Integer isBig) {
		this.isBig = isBig;
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
