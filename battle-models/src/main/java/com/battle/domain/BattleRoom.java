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
@Table(name="battle_room")
public class BattleRoom {
	
	public static final Integer STATUS_FREE = 0;
	
	public static final Integer STATUS_IN = 1;
	
	public static final Integer STATUS_FULL = 2;
	
	public static final Integer STATUS_END = 3;
	
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="maxinum")
	private Integer maxinum;
	
	@ParamAnnotation
	@Column(name="mininum")
	private Integer mininum;
	
	@ParamAnnotation
	@Column(name="num")
	private Integer num;
	
	//引用battleUserId
	@ParamAnnotation
	@Column(name="owner")
	private String owner;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="is_search_able")
	private Integer isSearchAble;
	
	@ParamAnnotation
	@Column(name="is_display")
	private Integer isDisplay;
	
	@ParamAnnotation
	@Column
	private String name;
	
	@ParamAnnotation
	@Column(name="img_url")
	private String imgUrl;
	
	@ParamAnnotation
	@Column
	private String instruction;
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	//加速冷却消耗豆子数量
	@ParamAnnotation
	@Column(name="speed_cool_bean")
	private Integer speedCoolBean;
	
	//加速一次多少秒
	@ParamAnnotation
	@Column(name="speed_cool_second")
	private Integer speedCoolSecond;
	
	@ParamAnnotation
	@Column(name="creation_time")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;
	
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

	public Integer getMaxinum() {
		return maxinum;
	}

	public void setMaxinum(Integer maxinum) {
		this.maxinum = maxinum;
	}

	public Integer getMininum() {
		return mininum;
	}

	public void setMininum(Integer mininum) {
		this.mininum = mininum;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public DateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	
	public Integer getIsSearchAble() {
		return isSearchAble;
	}

	public void setIsSearchAble(Integer isSearchAble) {
		this.isSearchAble = isSearchAble;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
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

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSpeedCoolBean() {
		return speedCoolBean;
	}

	public void setSpeedCoolBean(Integer speedCoolBean) {
		this.speedCoolBean = speedCoolBean;
	}

	public Integer getSpeedCoolSecond() {
		return speedCoolSecond;
	}

	public void setSpeedCoolSecond(Integer speedCoolSecond) {
		this.speedCoolSecond = speedCoolSecond;
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
