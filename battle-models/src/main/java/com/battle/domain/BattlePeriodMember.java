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
@Table(name="battle_period_member")
public class BattlePeriodMember {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="battle_user_id")
	private String battleUserId;
	
	@ParamAnnotation
	@Column
	private Integer process;
	
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

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public String getBattleUserId() {
		return battleUserId;
	}

	public void setBattleUserId(String battleUserId) {
		this.battleUserId = battleUserId;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
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
