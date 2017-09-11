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
@Table(name="battle_period_member")
public class BattlePeriodMember {
	
	//游离状态
	public static final Integer STATUS_FREE = 0;
	
	//进行中
	public static final Integer STATUS_IN = 1;
	
	public static final Integer STATUS_COMPLETE =2;
	
	
	@Id
	@IdAnnotation
	@AttrAnnotation(name = AttrEnum.periodMemberId)
	private String id;
	
	@ParamAnnotation
	@Column(name="period_id")
	@AttrAnnotation(name = AttrEnum.periodId)
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
	
	
	//爱心数量上限
	@ParamAnnotation
	@Column(name="love_count")
	private Integer loveCount;
	
	//爱心剩余
	@ParamAnnotation
	@Column(name="love_residule")
	private Integer loveResidule;
	
	//0游离状态 1进行中 2完成
	@ParamAnnotation
	@Column
	@AttrAnnotation(name = AttrEnum.battlePeriodMemberStatus)
	private Integer status;

	@ParamAnnotation
	@Column
	private String nickname;
	
	@ParamAnnotation
	@Column(name="head_img")
	private String headImg;
	
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
	
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	public Integer getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(Integer loveCount) {
		this.loveCount = loveCount;
	}

	public Integer getLoveResidule() {
		return loveResidule;
	}

	public void setLoveResidule(Integer loveResidule) {
		this.loveResidule = loveResidule;
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
