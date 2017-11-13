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
@Table(name="battle_room_record")
public class BattleRoomRecord {
	@Id
	@IdAnnotation
	private String id;
	
	@Column
	@ParamAnnotation
	private String nickname;
	
	@Column(name="img_url")
	@ParamAnnotation
	private String imgUrl;
	
	@Column(name="room_id")
	@ParamAnnotation
	private String roomId;
	
	@Column
	@ParamAnnotation
	private String log;
	
	@Column(name="happen_time")
	@ParamAnnotation
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime happenTime;
	
	@Column(name="member_id")
	@ParamAnnotation
	private String memberId;
	
	
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public DateTime getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(DateTime happenTime) {
		this.happenTime = happenTime;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
