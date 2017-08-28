package com.wyc.common.domain;

import java.math.BigDecimal;

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

@Entity
@Table(name="p_account")
@ParamEntityAnnotation
public class Account {
	@Id
	@IdAnnotation
    private String id;

	//智慧豆数量
	@ParamAnnotation
	@Column(name="wisdom_count")
	private Long wisdomCount;
	
	//智慧豆上限
	@ParamAnnotation
	@Column(name="wisdom_limit")
	private Long wisdomLimit;
	
	//经验
	@ParamAnnotation
	@Column(name="empirical_value")
	private BigDecimal empiricalValue;
	
	
	//本月可提现次数
	@ParamAnnotation
	@Column(name="can_take_out_count")
	private Integer canTakeOutCount;
	
	//爱心个数
	@ParamAnnotation
	@Column(name="love_life")
	private Integer loveLife;
	
	//爱心上限
	@ParamAnnotation
	@Column(name="love_life_limit")
	private Integer loveLifeLimit;
	
	//积分数
	@ParamAnnotation
	@Column
	private Integer integral;
	
	//账户余额以元为单位，有小数点
	@ParamAnnotation
	@Column(name="amount_balance")
	private BigDecimal amountBalance;
	
	//砖石数量
	@ParamAnnotation
	@Column(name="masonry")
	private Integer masonry;

	@Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
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
	
	public Long getWisdomCount() {
		return wisdomCount;
	}
	public void setWisdomCount(Long wisdomCount) {
		this.wisdomCount = wisdomCount;
	}
	public BigDecimal getEmpiricalValue() {
		return empiricalValue;
	}
	public void setEmpiricalValue(BigDecimal empiricalValue) {
		this.empiricalValue = empiricalValue;
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
	public Integer getCanTakeOutCount() {
		return canTakeOutCount;
	}
	public void setCanTakeOutCount(Integer canTakeOutCount) {
		this.canTakeOutCount = canTakeOutCount;
	}
	public BigDecimal getAmountBalance() {
		return amountBalance;
	}
	public void setAmountBalance(BigDecimal amountBalance) {
		this.amountBalance = amountBalance;
	}
	public Long getWisdomLimit() {
		return wisdomLimit;
	}
	public void setWisdomLimit(Long wisdomLimit) {
		this.wisdomLimit = wisdomLimit;
	}
	public Integer getLoveLife() {
		return loveLife;
	}
	public void setLoveLife(Integer loveLife) {
		this.loveLife = loveLife;
	}
	public Integer getLoveLifeLimit() {
		return loveLifeLimit;
	}
	public void setLoveLifeLimit(Integer loveLifeLimit) {
		this.loveLifeLimit = loveLifeLimit;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getMasonry() {
		return masonry;
	}
	public void setMasonry(Integer masonry) {
		this.masonry = masonry;
	}
}
