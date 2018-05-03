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
@Table(name="battle_wait_user")
public class BattleWaitUser {
	
	public static final Integer FREE_STATUS = 0;
	public static final Integer INTO_STATUS = 1;
	public static final Integer READY_STATUS = 2;
	public static final Integer OUT_STATUS = 3;
	@Id
	@IdAnnotation
	private String id;
	
	@Column(name="wait_id")
	private String waitId;
	
	@Column(name="user_id")
	private String userId;
	
	//0表示游离状态 1表示进入房间 2表示准备状态 3表示离开
	@Column(name="status")
	private Integer status;
	
	@Column(name="img_url")
	private String imgUrl;
	
	@Column
	private String nickname;
	
	@Column(name="dan_user_id")
	private String danUserId;
	
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

	public String getWaitId() {
		return waitId;
	}

	public void setWaitId(String waitId) {
		this.waitId = waitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getDanUserId() {
		return danUserId;
	}

	public void setDanUserId(String danUserId) {
		this.danUserId = danUserId;
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
