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
@Table(name="battle_member_paper_answer")
public class BattleMemberPaperAnswer {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="battle_period_member_id")
	private String battlePeriodMemberId;
	
	//扣掉爱心
	@ParamAnnotation
	@Column(name="sub_love")
	private Integer subLove;
	
	@ParamAnnotation
	@Column(name="wrong_sum")
	private Integer wrongSum;
	
	@ParamAnnotation
	@Column(name="right_sum")
	private Integer rightSum;
	
	//前进距离
	@ParamAnnotation
	@Column(name="add_distance")
	private Integer addDistance;
	
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

	public String getBattlePeriodMemberId() {
		return battlePeriodMemberId;
	}

	public void setBattlePeriodMemberId(String battlePeriodMemberId) {
		this.battlePeriodMemberId = battlePeriodMemberId;
	}
	

	public Integer getSubLove() {
		return subLove;
	}

	public void setSubLove(Integer subLove) {
		this.subLove = subLove;
	}

	

	public Integer getWrongSum() {
		return wrongSum;
	}

	public void setWrongSum(Integer wrongSum) {
		this.wrongSum = wrongSum;
	}

	public Integer getRightSum() {
		return rightSum;
	}

	public void setRightSum(Integer rightSum) {
		this.rightSum = rightSum;
	}

	public Integer getAddDistance() {
		return addDistance;
	}

	public void setAddDistance(Integer addDistance) {
		this.addDistance = addDistance;
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
