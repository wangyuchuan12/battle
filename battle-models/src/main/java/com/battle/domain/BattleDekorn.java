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
@Table(name="battle_dekorn")
public class BattleDekorn {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column
	private String name;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="is_del")
	private Integer isDel;
	
	@ParamAnnotation
	@Column
	private Integer level;
	
	@ParamAnnotation
	@Column(name="max_num")
	private Integer maxNum;
	
	@ParamAnnotation
	@Column(name="min_num")
	private Integer minNum;
	
	
	@ParamAnnotation
	@Column(name="score_gogal")
	private Integer scoreGogal;
	
	@ParamAnnotation
	@Column
	private Integer places;
	
	@ParamAnnotation
	@Column(name="cost_bean")
	private Integer costBean;
	
	@ParamAnnotation
	@Column(name="reward_bean_no1")
	private Integer rewardBeanNo1;
	
	@ParamAnnotation
	@Column(name="reward_bean_no2")
	private Integer rewardBeanNo2;
	
	@ParamAnnotation
	@Column(name="reward_bean_no3")
	private Integer rewardBeanNo3;
	
	@ParamAnnotation
	@Column(name="reward_bean_no4")
	private Integer rewardBeanNo4;
	
	@ParamAnnotation
	@Column(name="reward_bean_no5")
	private Integer rewardBeanNo5;
	
	@ParamAnnotation
	@Column(name="reward_bean_no6")
	private Integer rewardBeanNo6;
	
	@ParamAnnotation
	@Column(name="reward_bean_no7")
	private Integer rewardBeanNo7;
	
	@ParamAnnotation
	@Column(name="reward_bean_no8")
	private Integer rewardBeanNo8;
	
	@ParamAnnotation
	@Column(name="reward_bean_no9")
	private Integer rewardBeanNo9;
	
	@ParamAnnotation
	@Column(name="reward_bean_no10")
	private Integer rewardBeanNo10;
	
	@ParamAnnotation
	@Column
	private Integer status;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getRewardBeanNo1() {
		return rewardBeanNo1;
	}

	public void setRewardBeanNo1(Integer rewardBeanNo1) {
		this.rewardBeanNo1 = rewardBeanNo1;
	}

	public Integer getRewardBeanNo2() {
		return rewardBeanNo2;
	}

	public void setRewardBeanNo2(Integer rewardBeanNo2) {
		this.rewardBeanNo2 = rewardBeanNo2;
	}

	public Integer getRewardBeanNo3() {
		return rewardBeanNo3;
	}

	public void setRewardBeanNo3(Integer rewardBeanNo3) {
		this.rewardBeanNo3 = rewardBeanNo3;
	}

	public Integer getRewardBeanNo4() {
		return rewardBeanNo4;
	}

	public void setRewardBeanNo4(Integer rewardBeanNo4) {
		this.rewardBeanNo4 = rewardBeanNo4;
	}

	public Integer getRewardBeanNo5() {
		return rewardBeanNo5;
	}

	public void setRewardBeanNo5(Integer rewardBeanNo5) {
		this.rewardBeanNo5 = rewardBeanNo5;
	}

	public Integer getRewardBeanNo6() {
		return rewardBeanNo6;
	}

	public void setRewardBeanNo6(Integer rewardBeanNo6) {
		this.rewardBeanNo6 = rewardBeanNo6;
	}

	public Integer getRewardBeanNo7() {
		return rewardBeanNo7;
	}

	public void setRewardBeanNo7(Integer rewardBeanNo7) {
		this.rewardBeanNo7 = rewardBeanNo7;
	}

	public Integer getRewardBeanNo8() {
		return rewardBeanNo8;
	}

	public void setRewardBeanNo8(Integer rewardBeanNo8) {
		this.rewardBeanNo8 = rewardBeanNo8;
	}

	public Integer getRewardBeanNo9() {
		return rewardBeanNo9;
	}

	public void setRewardBeanNo9(Integer rewardBeanNo9) {
		this.rewardBeanNo9 = rewardBeanNo9;
	}

	public Integer getRewardBeanNo10() {
		return rewardBeanNo10;
	}

	public void setRewardBeanNo10(Integer rewardBeanNo10) {
		this.rewardBeanNo10 = rewardBeanNo10;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}
	
	public Integer getScoreGogal() {
		return scoreGogal;
	}

	public void setScoreGogal(Integer scoreGogal) {
		this.scoreGogal = scoreGogal;
	}

	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}

	public Integer getCostBean() {
		return costBean;
	}

	public void setCostBean(Integer costBean) {
		this.costBean = costBean;
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
