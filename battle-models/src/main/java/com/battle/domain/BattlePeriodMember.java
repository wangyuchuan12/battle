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
import com.wyc.AttrEnum;
import com.wyc.annotation.AttrAnnotation;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;


@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ParamEntityAnnotation
@Entity
@Table(name="battle_period_member",indexes={@Index(columnList="battle_id,period_id,room_id",name="battlePeriodMemberIndex")})
public class BattlePeriodMember implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//游离状态
	public static final Integer STATUS_FREE = 0;
	
	//进行中
	public static final Integer STATUS_IN = 1;
	
	//完成
	public static final Integer STATUS_COMPLETE =2;
	
	//退出
	public static final Integer STATUS_OUT=3;
	
	//结束或者失败了
	public static final Integer STATUS_END = 4;
	
	
	@Id
	@IdAnnotation
	@AttrAnnotation(name = AttrEnum.periodMemberId)
	private String id;
	
	@ParamAnnotation
	@Column(name="period_id")
	@AttrAnnotation(name = AttrEnum.periodId)
	private String periodId;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	@ParamAnnotation
	@Column(name="battle_user_id")
	private String battleUserId;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column
	private Integer process;
	
	
	//爱心数量上限
	@ParamAnnotation
	@Column(name="love_count")
	private Integer loveCount;
	
	//爱心剩余
	@ParamAnnotation
	@Column(name="love_residule")
	private Integer loveResidule;
	
	//0游离状态 1进行中 2完成
	@ParamAnnotation
	@Column
	@AttrAnnotation(name = AttrEnum.battlePeriodMemberStatus)
	private Integer status;

	@ParamAnnotation
	@Column
	private String nickname;
	
	@ParamAnnotation
	@Column(name="head_img")
	private String headImg;
	
	@ParamAnnotation
	@Column(name="room_id")
	private String roomId;
	
	@ParamAnnotation
	@Column(name="stage_index")
	@AttrAnnotation(name = AttrEnum.periodStageIndex)
	private Integer stageIndex;
	
	@ParamAnnotation
	@Column(name="stage_count")
	private Integer stageCount;
	
	
	@ParamAnnotation
	@Column(name="is_del")
	private Integer isDel;
	
	@ParamAnnotation
	@Column(name="dan_id")
	private String danId;
	
	@ParamAnnotation
	@Column(name="z_index")
	private Integer index;
	
	@ParamAnnotation
	@Column(name="flag_img")
	private String flagImg;
	
	@ParamAnnotation
	@Column(name="flag_id")
	private String flagId;
	
	@ParamAnnotation
	@Column(name = "takepart_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime takepartAt;
	
	@ParamAnnotation
	@Column
	private Integer score;
	
	
	@ParamAnnotation
	@Column(name="scroll_gogal")
	private Integer scrollGogal;
	
	//分享次数
	@ParamAnnotation
	@Column(name="share_time")
	private Integer shareTime;
	
	@ParamAnnotation
	@Column(name="reward_bean")
	private Integer rewardBean;
	
	
	@ParamAnnotation
	@Column(name="reward_love")
	private Integer rewardLove;
	
	@ParamAnnotation
	@Column(name="is_increase")
	private Integer isIncrease;
	
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
	
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getBattleUserId() {
		return battleUserId;
	}

	public void setBattleUserId(String battleUserId) {
		this.battleUserId = battleUserId;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	public Integer getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(Integer loveCount) {
		this.loveCount = loveCount;
	}

	public Integer getLoveResidule() {
		return loveResidule;
	}

	public void setLoveResidule(Integer loveResidule) {
		this.loveResidule = loveResidule;
	}
	

	public Integer getStageIndex() {
		return stageIndex;
	}

	public void setStageIndex(Integer stageIndex) {
		this.stageIndex = stageIndex;
	}
	
	

	public Integer getStageCount() {
		return stageCount;
	}

	public void setStageCount(Integer stageCount) {
		this.stageCount = stageCount;
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public DateTime getTakepartAt() {
		return takepartAt;
	}

	public void setTakepartAt(DateTime takepartAt) {
		this.takepartAt = takepartAt;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getShareTime() {
		return shareTime;
	}

	public void setShareTime(Integer shareTime) {
		this.shareTime = shareTime;
	}

	public Integer getRewardBean() {
		return rewardBean;
	}

	public void setRewardBean(Integer rewardBean) {
		this.rewardBean = rewardBean;
	}
	
	public Integer getScrollGogal() {
		return scrollGogal;
	}

	public void setScrollGogal(Integer scrollGogal) {
		this.scrollGogal = scrollGogal;
	}

	public Integer getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(Integer isIncrease) {
		this.isIncrease = isIncrease;
	}
	
	public String getDanId() {
		return danId;
	}

	public void setDanId(String danId) {
		this.danId = danId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public String getFlagImg() {
		return flagImg;
	}

	public void setFlagImg(String flagImg) {
		this.flagImg = flagImg;
	}
	
	public String getFlagId() {
		return flagId;
	}

	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}
	
	public Integer getRewardLove() {
		return rewardLove;
	}

	public void setRewardLove(Integer rewardLove) {
		this.rewardLove = rewardLove;
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
