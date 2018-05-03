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
@Table(name="battle_wait")
public class BattleWait {
	
	public final static Integer CALL_STATUS = 0;
	public final static Integer START_STATUS = 1;
	@Id
	@IdAnnotation
	private String id;
	
	@Column(name="battle_id")
	private String battleId;
	
	@Column(name="period_id")
	private String periodId;
	
	@ParamAnnotation
	@Column(name="maxinum")
	private Integer maxinum;
	
	@ParamAnnotation
	@Column(name="mininum")
	private Integer mininum;
	
	@ParamAnnotation
	@Column(name="num")
	private Integer num;
	
	@ParamAnnotation
	@Column(name = "create_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
	private DateTime createTime;
	
	//当达到最小人数延迟执行时间
	@ParamAnnotation
	@Column(name="act_delay")
	private Integer actDelay;
	
	//0表示正在召集  1表示已经开始
	@ParamAnnotation
	@Column
	private Integer status;
	
	
	//是否等待执行
	@ParamAnnotation
	@Column(name="isPrepare_start")
	private Integer isPrepareStart;
	
	@ParamAnnotation
	@Column(name="dan_id")
	private String danId;
	
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

	public Integer getActDelay() {
		return actDelay;
	}

	public void setActDelay(Integer actDelay) {
		this.actDelay = actDelay;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsPrepareStart() {
		return isPrepareStart;
	}

	public void setIsPrepareStart(Integer isPrepareStart) {
		this.isPrepareStart = isPrepareStart;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	public String getDanId() {
		return danId;
	}

	public void setDanId(String danId) {
		this.danId = danId;
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
