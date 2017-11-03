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
@Table(name="g_good")
@ParamEntityAnnotation
public class Good {
	
	//下架
	public static final Integer STATUS_OFF_SHELVES = 0;
	//上架
	public static final Integer STATUS_ON_SHELVES = 1;
	
	//现金支付
	public static final Integer AMOUNT_COST_TYPE = 0;
	
	//智慧豆支付类型
	public static final Integer BEAN_COST_TYPE = 1;
	
	//砖石支付类型
	public static final Integer MASONRY_COST_TYPE =2;
	
	//道具类型
	public static final Integer PROP_TYPE = 0;
	
	//智慧豆类型
	public static final Integer BEAN_TYPE = 1;
	
	//砖石类型
	public static final Integer MASONRY_TYPE = 2;
	
	//爱心
	public static final Integer LOVE_TYPE=3;
	@Id
	@IdAnnotation
	private String id;
	
	//商品名称
	@Column
	@ParamAnnotation
	private String name;
	
	//商品规格
	@Column
	@ParamAnnotation
	private String spec;
	
	//商品简介
	@Column
	@ParamAnnotation
	private String detail;
	
	//图片
	@Column(name="img_url")
	@ParamAnnotation
	private String imgUrl;
	
	//爱心数量
	@Column(name="love_num")
	@ParamAnnotation
	private Long loveNum;
	
	//智慧豆数量
	@Column(name="bean_num")
	@ParamAnnotation
	private Long beanNum;
	
	//充钱
	@Column(name="amount_num")
	@ParamAnnotation
	private BigDecimal amountNum;
	
	//砖石数量
	@Column(name="masonry_num")
	@ParamAnnotation
	private Long masonryNum;
	
	
	//花费金额
	@Column(name="cost_money")
	@ParamAnnotation
	private BigDecimal costMoney;
	
	//花费智慧豆
	@Column(name="cost_bean")
	@ParamAnnotation
	private Integer costBean;
	
	//花费砖石
	@Column(name="cost_masonry")
	@ParamAnnotation
	private Integer costMasonry;
	
	//0表示下架 1表示有效
	@Column
	@ParamAnnotation
	private Integer status;
	
	//支付方式 0金额支付 1智慧豆支付 2砖石支付
	@Column(name="cost_type")
	@ParamAnnotation
	private Integer costType;
	
	@Column
	@ParamAnnotation
	private Integer type;
	
	@Column(name="z_index")
	@ParamAnnotation
	private Integer index;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Long getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(Long loveNum) {
		this.loveNum = loveNum;
	}
	public Long getBeanNum() {
		return beanNum;
	}
	public void setBeanNum(Long beanNum) {
		this.beanNum = beanNum;
	}
	public BigDecimal getAmountNum() {
		return amountNum;
	}
	public void setAmountNum(BigDecimal amountNum) {
		this.amountNum = amountNum;
	}
	public Long getMasonryNum() {
		return masonryNum;
	}
	public void setMasonryNum(Long masonryNum) {
		this.masonryNum = masonryNum;
	}
	public BigDecimal getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(BigDecimal costMoney) {
		this.costMoney = costMoney;
	}
	public Integer getCostBean() {
		return costBean;
	}
	public void setCostBean(Integer costBean) {
		this.costBean = costBean;
	}
	public Integer getCostMasonry() {
		return costMasonry;
	}
	public void setCostMasonry(Integer costMasonry) {
		this.costMasonry = costMasonry;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
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
