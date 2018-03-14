package com.battle.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.AttrEnum;
import com.wyc.annotation.AttrAnnotation;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@ParamEntityAnnotation
@Entity
@Table(name="battle_user",indexes={@Index(columnList="battle_id,open_id,user_id,current_room_id",name="battleUserIndex")})
public class BattleUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@IdAnnotation
	@AttrAnnotation(name=AttrEnum.battleUserId)
	private String id;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="open_id")
	private String openId;
	
	@ParamAnnotation
	@Column(name="is_manager")
	private Integer isManager;
	
	@ParamAnnotation
	@Column(name="is_creater")
	private Integer isCreater;
	
	@ParamAnnotation
	@Column(name="current_room_id")
	private String currentRoomId;
	
	@ParamAnnotation
	@Column(name="battle_expert_id")
	private String battleExpertId;

	
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

	public Integer getIsCreater() {
		return isCreater;
	}

	public void setIsCreater(Integer isCreater) {
		this.isCreater = isCreater;
	}


	public String getCurrentRoomId() {
		return currentRoomId;
	}

	public void setCurrentRoomId(String currentRoomId) {
		this.currentRoomId = currentRoomId;
	}

	public String getBattleExpertId() {
		return battleExpertId;
	}

	public void setBattleExpertId(String battleExpertId) {
		this.battleExpertId = battleExpertId;
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
