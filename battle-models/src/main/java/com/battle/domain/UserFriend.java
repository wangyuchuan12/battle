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
@Table(name="user_friend",indexes={@Index(columnList="user_id,friend_user_id",name="userFriendIndex")})
public class UserFriend {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column(name="friend_user_id")
	private String friendUserId;
	
	@ParamAnnotation
	@Column(name="user_name")
	private String userName;
	
	@ParamAnnotation
	@Column(name="frend_user_name")
	private String frendUserName;
	
	@ParamAnnotation
	@Column(name="user_img")
	private String userImg;
	
	@ParamAnnotation
	@Column(name="frend_user_img")
	private String frendUserImg;
	
	@ParamAnnotation
	@Column(name = "meet_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
	private DateTime meetTime;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendUserId() {
		return friendUserId;
	}

	public void setFriendUserId(String friendUserId) {
		this.friendUserId = friendUserId;
	}

	public DateTime getMeetTime() {
		return meetTime;
	}

	public void setMeetTime(DateTime meetTime) {
		this.meetTime = meetTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFrendUserName() {
		return frendUserName;
	}

	public void setFrendUserName(String frendUserName) {
		this.frendUserName = frendUserName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getFrendUserImg() {
		return frendUserImg;
	}

	public void setFrendUserImg(String frendUserImg) {
		this.frendUserImg = frendUserImg;
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
