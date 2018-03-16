package com.battle.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;


//爱心冷却
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@ParamEntityAnnotation
@Entity
@Table(name="battle_member_love_cooling",indexes={@Index(columnList="battle_member_id",name="battleMemberLoveCoolingIndex")})
public class BattleMemberLoveCooling  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//游离状态
	public static final Integer STATUS_FREE = 0;
	//冷却中
	public static final Integer STATUS_IN = 1;
	//完成冷却
	public static final Integer STATUS_COMPLETE=2;
	@Id
	@IdAnnotation
	private String id;
	
	//指向BattlePeriodMember
	@ParamAnnotation
	@Column(name="battle_member_id",unique=true)
	private String battleMemberId;
	
	@ParamAnnotation
	@Column
	private Integer unit;
	
	@ParamAnnotation
	@Column(name="upper_limit")
	private Long upperLimit;
	
	@ParamAnnotation
	@Column
	private Long millisec;
	
	//进度
	@ParamAnnotation
	@Column
	private Long schedule;
	
	//状态 0表示游离状态 1表示冷却中 2表示完成冷却
	@ParamAnnotation
	@Column
	private Integer status;
	
	//冷却爱心序号
	@ParamAnnotation
	@Column(name="cool_love_seq")
	private Integer coolLoveSeq;
	
	@ParamAnnotation
	@Column(name="start_datetime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startDatetime;
	
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

	public String getBattleMemberId() {
		return battleMemberId;
	}

	public void setBattleMemberId(String battleMemberId) {
		this.battleMemberId = battleMemberId;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Long getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Long upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Long getMillisec() {
		return millisec;
	}

	public void setMillisec(Long millisec) {
		this.millisec = millisec;
	}

	public Long getSchedule() {
		return schedule;
	}

	public void setSchedule(Long schedule) {
		this.schedule = schedule;
	}
	
	public DateTime getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(DateTime startDatetime) {
		this.startDatetime = startDatetime;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getCoolLoveSeq() {
		return coolLoveSeq;
	}

	public void setCoolLoveSeq(Integer coolLoveSeq) {
		this.coolLoveSeq = coolLoveSeq;
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
