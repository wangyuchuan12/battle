package com.battle.domain;

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

@ParamEntityAnnotation
@Entity
@Table(name="battle_red_packet_type")
public class BattleRedPacketType {
	@Id
	@IdAnnotation
	private String id;
	
	
	//消耗类型
	@ParamAnnotation
	@Column(name="cost_type",nullable=false)
	private Integer costType;
	
	
	//消耗智慧豆
	@ParamAnnotation
	@Column(name="cost_bean",nullable=false)
	private Long costBean;
	
	//消耗砖石
	@ParamAnnotation
	@Column(name="cost_masonry",nullable=false)
	private Long costMasonry;
	
	//消耗金额
	@ParamAnnotation
	@Column(name="cost_amount",nullable=false)
	private BigDecimal costAmount;
	
	//智慧豆数量
	@ParamAnnotation
	@Column(name="bean_num",nullable=false)
	private Long beanNum;
	
	//红包砖石数量
	@ParamAnnotation
	@Column(name="masonry_num",nullable=false)
	private Long masonryNum;
	
	//红包总金额
	@ParamAnnotation
	@Column(nullable=false)
	private BigDecimal amount;
	
	//红包数量
	@ParamAnnotation
	@Column(nullable=false)
	private Integer num;
	
	@ParamAnnotation
	@Column(nullable=false,name="is_del")
	private Integer isDel;
	
	@ParamAnnotation
	@Column(nullable=false)
	private String name;
	
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

	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
	}

	public Long getCostBean() {
		return costBean;
	}

	public void setCostBean(Long costBean) {
		this.costBean = costBean;
	}

	public Long getCostMasonry() {
		return costMasonry;
	}

	public void setCostMasonry(Long costMasonry) {
		this.costMasonry = costMasonry;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public Long getBeanNum() {
		return beanNum;
	}

	public void setBeanNum(Long beanNum) {
		this.beanNum = beanNum;
	}

	public Long getMasonryNum() {
		return masonryNum;
	}

	public void setMasonryNum(Long masonryNum) {
		this.masonryNum = masonryNum;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
