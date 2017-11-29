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
@Table(name="battle_account_result")
public class BattleAccountResult {
	@Id
	@IdAnnotation
	private String id;
	
	//账号id
	@ParamAnnotation
	@Column(name="account_id")
	private String accountId;
	
	//用户UserInfo表id值
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	//经验值
	@ParamAnnotation
	@Column
	private Long exp;
	
	//级别
	@ParamAnnotation
	@Column
	private Integer level;
	
	//赢的次数
	@ParamAnnotation
	@Column(name="win_time")
	private Long winTime;
	
	//输的次数
	@ParamAnnotation
	@Column(name="fail_time")
	private Long failTime;
	
	//战斗次数
	@ParamAnnotation
	@Column(name="fight_time")
	private Long fightTime;
	
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getWinTime() {
		return winTime;
	}

	public void setWinTime(Long winTime) {
		this.winTime = winTime;
	}

	public Long getFailTime() {
		return failTime;
	}

	public void setFailTime(Long failTime) {
		this.failTime = failTime;
	}

	public Long getFightTime() {
		return fightTime;
	}

	public void setFightTime(Long fightTime) {
		this.fightTime = fightTime;
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
