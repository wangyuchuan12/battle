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
@Table(name="battle_gift_model")
public class BattleGiftModel {
	@Id
	@IdAnnotation
	private String id;
	
	
	@ParamAnnotation
	@Column(name="love_num")
	private Integer loveNum;
	
	@ParamAnnotation
	@Column(name="bean_num")
	private Integer beanNum;
	
	@ParamAnnotation
	@Column
	private Integer level;
	
	@ParamAnnotation
	@Column(name="recie_bean_num_condition")
	private Integer recieBeanNumCondition;
	
	@ParamAnnotation
	@Column(name="recie_love_num_condition")
	private Integer recieLoveNumCondition;
	
	@ParamAnnotation
	@Column(name = "receive_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime receiveTime;
	
	@ParamAnnotation
	@Column(name="is_del")
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

	public Integer getLoveNum() {
		return loveNum;
	}

	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}

	public Integer getBeanNum() {
		return beanNum;
	}

	public void setBeanNum(Integer beanNum) {
		this.beanNum = beanNum;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public DateTime getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(DateTime receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	public Integer getRecieBeanNumCondition() {
		return recieBeanNumCondition;
	}

	public void setRecieBeanNumCondition(Integer recieBeanNumCondition) {
		this.recieBeanNumCondition = recieBeanNumCondition;
	}

	public Integer getRecieLoveNumCondition() {
		return recieLoveNumCondition;
	}

	public void setRecieLoveNumCondition(Integer recieLoveNumCondition) {
		this.recieLoveNumCondition = recieLoveNumCondition;
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
