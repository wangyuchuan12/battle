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
@Table(name="battle_dan")
public class BattleDan {
	
	public static Integer STATUS_FREE = 0;
	
	public static Integer STATUS_IN = 1;
	
	public static Integer STATUS_SUCCESS = 2;
	
	public static Integer STATUS_FAIL = 3;
	@Id
	@IdAnnotation
	private String id;
	
	
	@ParamAnnotation
	@Column
	private String name;
	
	@ParamAnnotation
	@Column
	private Integer level;
	
	@ParamAnnotation
	@Column(name="point_id")
	private String pointId;
	
	@ParamAnnotation
	@Column(name="img_url")
	private String imgUrl;
	
	
	@ParamAnnotation
	@Column(name="score_gogal")
	private Integer scoreGogal;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="period_id")
	private String periodId;
	
	//名额数量
	@ParamAnnotation
	@Column
	private Integer places;
	
	@ParamAnnotation
	@Column(name="sign_1_bean_cost")
	private Integer sign1BeanCost;
	
	@ParamAnnotation
	@Column(name="sign_2_bean_cost")
	private Integer sign2BeanCost;
	
	@ParamAnnotation
	@Column(name="sign_3_bean_cost")
	private Integer sign3BeanCost;
	
	@ParamAnnotation
	@Column(name="sign_4_bean_cost")
	private Integer sign4BeanCost;
	
	@ParamAnnotation
	@Column(name="max_num")
	private Integer maxNum;
	
	@ParamAnnotation
	@Column(name="min_num")
	private Integer minNum;
	
	@ParamAnnotation
	@Column(name="time_long")
	private Integer timeLong;
	
	
	@ParamAnnotation
	@Column(name="love_count")
	private Integer loveCount;
	
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getScoreGogal() {
		return scoreGogal;
	}

	public void setScoreGogal(Integer scoreGogal) {
		this.scoreGogal = scoreGogal;
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

	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}
	
	public Integer getSign1BeanCost() {
		return sign1BeanCost;
	}

	public void setSign1BeanCost(Integer sign1BeanCost) {
		this.sign1BeanCost = sign1BeanCost;
	}

	public Integer getSign2BeanCost() {
		return sign2BeanCost;
	}

	public void setSign2BeanCost(Integer sign2BeanCost) {
		this.sign2BeanCost = sign2BeanCost;
	}

	public Integer getSign3BeanCost() {
		return sign3BeanCost;
	}

	public void setSign3BeanCost(Integer sign3BeanCost) {
		this.sign3BeanCost = sign3BeanCost;
	}

	public Integer getSign4BeanCost() {
		return sign4BeanCost;
	}

	public void setSign4BeanCost(Integer sign4BeanCost) {
		this.sign4BeanCost = sign4BeanCost;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	
	public Integer getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Integer timeLong) {
		this.timeLong = timeLong;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}
	
	public Integer getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(Integer loveCount) {
		this.loveCount = loveCount;
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
