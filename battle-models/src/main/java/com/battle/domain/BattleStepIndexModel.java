package com.battle.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@ParamEntityAnnotation
@Entity
@Table(name="battle_step_index_model")
public class BattleStepIndexModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@IdAnnotation
	private String id;
	
	@Column(name="reward_type")
	@ParamAnnotation
	private Integer rewardType;
	
	
	@Column(name="bean_num")
	@ParamAnnotation
	private Integer beanNum;
	
	@Column(name="love_num")
	@ParamAnnotation
	private Integer loveNum;
	
	@Column(name="phy_num")
	@ParamAnnotation
	private Integer phyNum;
	
	@Column(name="step_index")
	@ParamAnnotation
	private Integer stepIndex;
	
	@Column(name="img_url")
	@ParamAnnotation
	private String imgUrl;
	
	@Column(name="model_id")
	@ParamAnnotation
	private String modelId;
	
	@Column(name="is_big")
	@ParamAnnotation
	private Integer isBig;
	
	
	@Column(name="is_del")
	@ParamAnnotation
	private Integer isDel;
	
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

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getBeanNum() {
		return beanNum;
	}

	public void setBeanNum(Integer beanNum) {
		this.beanNum = beanNum;
	}

	public Integer getLoveNum() {
		return loveNum;
	}

	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}

	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	

	public Integer getIsBig() {
		return isBig;
	}

	public void setIsBig(Integer isBig) {
		this.isBig = isBig;
	}
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getPhyNum() {
		return phyNum;
	}

	public void setPhyNum(Integer phyNum) {
		this.phyNum = phyNum;
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
