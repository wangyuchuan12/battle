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
@Table(name="battle_pk")
public class BattlePk {
	
	//离开
	public static final Integer STATUS_LEAVE = 0;
	
	//进入房间
	public static final Integer STATUS_INSIDE = 1;
	
	//准备
	public static final Integer STATUS_READY = 2;
	
	//开始
	public static final Integer STATUS_BATTLE = 3;
	
	
	//未创建房间
	public static final Integer ROOM_STATUS_FREE = 0;
	
	//已经创建房间，人数未满
	public static final Integer ROOM_STATUS_CALL = 1;
	
	//人数已满，战斗中
	public static final Integer ROOM_STATUS_BATTLE = 2;
	
	//结束
	public static final Integer ROOM_STATUS_END = 3;
	
	@Id
	@IdAnnotation
	private String id;
	
	
	@ParamAnnotation
	@Column(name="home_user_id")
	private String homeUserId;
	
	@ParamAnnotation
	@Column(name="home_username")
	private String homeUsername;
	
	@ParamAnnotation
	@Column(name="home_user_imgurl")
	private String homeUserImgurl;
	
	@ParamAnnotation
	@Column(name="beat_user_id")
	private String beatUserId;
	
	@ParamAnnotation
	@Column(name="beat_username")
	private String beatUsername;
	
	@ParamAnnotation
	@Column(name="beat_user_imgurl")
	private String beatUserImgurl;
	
	@ParamAnnotation
	@Column(name="room_id")
	private String roomId;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	
	@ParamAnnotation
	@Column(name="home_status")
	private Integer homeStatus;
	
	@ParamAnnotation
	@Column(name="beat_status")
	private Integer beatStatus;
	
	@ParamAnnotation
	@Column(name="room_status")
	private Integer roomStatus;
	
	@ParamAnnotation
	@Column(name="battle_count")
	private Integer battleCount;
	
	
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


	public String getHomeUserId() {
		return homeUserId;
	}


	public void setHomeUserId(String homeUserId) {
		this.homeUserId = homeUserId;
	}


	public String getHomeUsername() {
		return homeUsername;
	}


	public void setHomeUsername(String homeUsername) {
		this.homeUsername = homeUsername;
	}


	public String getHomeUserImgurl() {
		return homeUserImgurl;
	}


	public void setHomeUserImgurl(String homeUserImgurl) {
		this.homeUserImgurl = homeUserImgurl;
	}


	public String getBeatUserId() {
		return beatUserId;
	}


	public void setBeatUserId(String beatUserId) {
		this.beatUserId = beatUserId;
	}


	public String getBeatUsername() {
		return beatUsername;
	}


	public void setBeatUsername(String beatUsername) {
		this.beatUsername = beatUsername;
	}


	public String getBeatUserImgurl() {
		return beatUserImgurl;
	}


	public void setBeatUserImgurl(String beatUserImgurl) {
		this.beatUserImgurl = beatUserImgurl;
	}


	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public Integer getHomeStatus() {
		return homeStatus;
	}


	public void setHomeStatus(Integer homeStatus) {
		this.homeStatus = homeStatus;
	}


	public Integer getBeatStatus() {
		return beatStatus;
	}


	public void setBeatStatus(Integer beatStatus) {
		this.beatStatus = beatStatus;
	}


	public Integer getBattleCount() {
		return battleCount;
	}


	public void setBattleCount(Integer battleCount) {
		this.battleCount = battleCount;
	}
	
	public Integer getRoomStatus() {
		return roomStatus;
	}


	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
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
