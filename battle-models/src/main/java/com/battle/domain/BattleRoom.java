package com.battle.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name="battle_room")
public class BattleRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_FREE = 0;
	
	public static final Integer STATUS_IN = 1;
	
	public static final Integer STATUS_FULL = 2;
	
	public static final Integer STATUS_END = 3;
	
	public static final Integer CLEARANCE_END_TYPE = 0;
	
	public static final Integer TIMEOUT_END_TYPE = 1;
	
	public static final Integer SCROLL_GOGAL_END_TYPE = 2;
	
	public static final Integer SYSTEM_END_TYPE = 3;
	
	//强制退出
	public static final Integer FORCE_END_TYPE=4;
	
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
	@Column(name="small_img_url")
	private String smallImgUrl;
	
	@ParamAnnotation
	@Column
	private String instruction;
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column(name="is_redpack")
	private Integer isRedpack;
	
	@ParamAnnotation
	@Column(name="is_dan_room")
	private Integer isDanRoom;
	
	@ParamAnnotation
	@Column(name="dan_id")
	private String danId;
	
	@ParamAnnotation
	@Column(name="redpack_amount")
	private BigDecimal redpackAmount;
	
	@ParamAnnotation
	@Column(name="redpack_masonry")
	private Integer redpackMasonry;
	
	@ParamAnnotation
	@Column(name="redpack_bean")
	private Integer redpackBean;
	
	//加速冷却消耗豆子数量
	@ParamAnnotation
	@Column(name="speed_cool_bean")
	private Integer speedCoolBean;
	
	//加速一次多少秒
	@ParamAnnotation
	@Column(name="speed_cool_second")
	private Integer speedCoolSecond;
	
	//红包数量
	@ParamAnnotation
	@Column(name="red_pack_num")
	private Integer redPackNum;
	
	@ParamAnnotation
	@Column(name="room_score")
	private Integer roomScore;
	
	@ParamAnnotation
	@Column(name="room_process")
	private Integer roomProcess;
	
	@ParamAnnotation
	@Column(name="right_add_score")
	private Integer rightAddScore;
	
	@ParamAnnotation
	@Column(name="wrong_sub_score")
	private Integer wrongSubScore;
	
	@ParamAnnotation
	@Column(name="right1_add_process")
	private Integer right1AddProcess;
	
	@ParamAnnotation
	@Column(name="right2_add_process")
	private Integer right2AddProcess;
	
	@ParamAnnotation
	@Column(name="right3_add_process")
	private Integer right3AddProcess;
	
	@ParamAnnotation
	@Column(name="right4_add_process")
	private Integer right4AddProcess;
	
	@ParamAnnotation
	@Column(name="right5_add_process")
	private Integer right5AddProcess;
	
	@ParamAnnotation
	@Column(name="right6_add_process")
	private Integer right6AddProcess;
	
	@ParamAnnotation
	@Column(name="full_right_add_score")
	private Integer fullRightAddScore;
	
	//花费智慧豆
	@ParamAnnotation
	@Column(name="cost_bean")
	private Integer costBean;
	
	//花费砖石
	@ParamAnnotation
	@Column(name="cost_masonry")
	private Integer costMasonry;
	
	@ParamAnnotation
	@Column
	private Integer hot;
	
	@ParamAnnotation
	@Column(name="is_del")
	private Integer isDel;
	
	@ParamAnnotation
	@Column(name="z_key")
	private String key;
	
	@ParamAnnotation
	@Column(name="room_share_img")
	private String roomShareImg;
	
	@ParamAnnotation
	@Column(name="is_pk")
	private Integer isPk;
	
	//目标分数
	@ParamAnnotation
	@Column(name="scroll_gogal")
	private Integer scrollGogal;
	
	//结束方式
	@ParamAnnotation
	@Column(name="end_type")
	private Integer endType;
	
	//是否是闯关
	@ParamAnnotation
	@Column(name="is_pass_through")
	private Integer isPassThrough;
	
	//名额数量
	@ParamAnnotation
	@Column
	private Integer places;
	
	
	//0是分数 1是米数
	@ParamAnnotation
	@Column(name="achievement_type")
	private Integer achievementType;
	
	//是否增长
	@ParamAnnotation
	@Column(name="is_increase")
	private Integer isIncrease;
	
	@ParamAnnotation
	@Column(name="is_frend_group")
	private Integer isFrendGroup;
	
	@ParamAnnotation
	@Column(name="end_enable")
	private Integer endEnable;
	
	@ParamAnnotation
	@Column(name="is_dekorn")
	private Integer isDekorn;
	
	@ParamAnnotation
	@Column(name="dekorn_id")
	private String dekornId;
	
	@ParamAnnotation
	@Column(name="love_count")
	private Integer loveCount;
	
	@ParamAnnotation
	@Column(name="is_init")
	private Integer isInit;
	
	@ParamAnnotation
	@Column(name="max_index")
	private Integer maxIndex;
	
	@ParamAnnotation
	@Column(name="is_end_handle")
	private Integer isEndHandle;
	
	@ParamAnnotation
	@Column(name="is_start")
	private Integer isStart;
	
	@ParamAnnotation
	@Column(name="start_time")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startTime;
	
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


	public String getSmallImgUrl() {
		return smallImgUrl;
	}

	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	
	public Integer getRedPackNum() {
		return redPackNum;
	}

	public void setRedPackNum(Integer redPackNum) {
		this.redPackNum = redPackNum;
	}
	

	public Integer getRoomScore() {
		return roomScore;
	}

	public void setRoomScore(Integer roomScore) {
		this.roomScore = roomScore;
	}

	public Integer getRoomProcess() {
		return roomProcess;
	}

	public void setRoomProcess(Integer roomProcess) {
		this.roomProcess = roomProcess;
	}

	public Integer getRightAddScore() {
		return rightAddScore;
	}

	public void setRightAddScore(Integer rightAddScore) {
		this.rightAddScore = rightAddScore;
	}

	public Integer getWrongSubScore() {
		return wrongSubScore;
	}

	public void setWrongSubScore(Integer wrongSubScore) {
		this.wrongSubScore = wrongSubScore;
	}

	public Integer getRight1AddProcess() {
		return right1AddProcess;
	}

	public void setRight1AddProcess(Integer right1AddProcess) {
		this.right1AddProcess = right1AddProcess;
	}

	public Integer getRight2AddProcess() {
		return right2AddProcess;
	}

	public void setRight2AddProcess(Integer right2AddProcess) {
		this.right2AddProcess = right2AddProcess;
	}

	public Integer getRight3AddProcess() {
		return right3AddProcess;
	}

	public void setRight3AddProcess(Integer right3AddProcess) {
		this.right3AddProcess = right3AddProcess;
	}

	public Integer getRight4AddProcess() {
		return right4AddProcess;
	}

	public void setRight4AddProcess(Integer right4AddProcess) {
		this.right4AddProcess = right4AddProcess;
	}

	public Integer getRight5AddProcess() {
		return right5AddProcess;
	}

	public void setRight5AddProcess(Integer right5AddProcess) {
		this.right5AddProcess = right5AddProcess;
	}

	public Integer getRight6AddProcess() {
		return right6AddProcess;
	}

	public void setRight6AddProcess(Integer right6AddProcess) {
		this.right6AddProcess = right6AddProcess;
	}

	public Integer getFullRightAddScore() {
		return fullRightAddScore;
	}

	public void setFullRightAddScore(Integer fullRightAddScore) {
		this.fullRightAddScore = fullRightAddScore;
	}


	public Integer getIsRedpack() {
		return isRedpack;
	}

	public void setIsRedpack(Integer isRedpack) {
		this.isRedpack = isRedpack;
	}

	public BigDecimal getRedpackAmount() {
		return redpackAmount;
	}

	public void setRedpackAmount(BigDecimal redpackAmount) {
		this.redpackAmount = redpackAmount;
	}

	public Integer getRedpackMasonry() {
		return redpackMasonry;
	}

	public void setRedpackMasonry(Integer redpackMasonry) {
		this.redpackMasonry = redpackMasonry;
	}

	public Integer getRedpackBean() {
		return redpackBean;
	}

	public void setRedpackBean(Integer redpackBean) {
		this.redpackBean = redpackBean;
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

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

	public String getRoomShareImg() {
		return roomShareImg;
	}

	public void setRoomShareImg(String roomShareImg) {
		this.roomShareImg = roomShareImg;
	}
	
	public Integer getIsPk() {
		return isPk;
	}

	public void setIsPk(Integer isPk) {
		this.isPk = isPk;
	}
	
	

	public Integer getScrollGogal() {
		return scrollGogal;
	}

	public void setScrollGogal(Integer scrollGogal) {
		this.scrollGogal = scrollGogal;
	}

	public Integer getEndType() {
		return endType;
	}

	public void setEndType(Integer endType) {
		this.endType = endType;
	}
	
	public Integer getIsPassThrough() {
		return isPassThrough;
	}

	public void setIsPassThrough(Integer isPassThrough) {
		this.isPassThrough = isPassThrough;
	}
	
	

	public Integer getIsDanRoom() {
		return isDanRoom;
	}

	public void setIsDanRoom(Integer isDanRoom) {
		this.isDanRoom = isDanRoom;
	}
	
	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}
	
	public String getDanId() {
		return danId;
	}

	public void setDanId(String danId) {
		this.danId = danId;
	}
	
	

	public Integer getAchievementType() {
		return achievementType;
	}

	public void setAchievementType(Integer achievementType) {
		this.achievementType = achievementType;
	}
	
	public Integer getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(Integer isIncrease) {
		this.isIncrease = isIncrease;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	

	public Integer getIsFrendGroup() {
		return isFrendGroup;
	}

	public void setIsFrendGroup(Integer isFrendGroup) {
		this.isFrendGroup = isFrendGroup;
	}

	public Integer getEndEnable() {
		return endEnable;
	}

	public void setEndEnable(Integer endEnable) {
		this.endEnable = endEnable;
	}
	
	public Integer getIsDekorn() {
		return isDekorn;
	}

	public void setIsDekorn(Integer isDekorn) {
		this.isDekorn = isDekorn;
	}

	public String getDekornId() {
		return dekornId;
	}

	public void setDekornId(String dekornId) {
		this.dekornId = dekornId;
	}
	
	public Integer getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(Integer loveCount) {
		this.loveCount = loveCount;
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}
	
	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}
	
	public Integer getIsEndHandle() {
		return isEndHandle;
	}

	public void setIsEndHandle(Integer isEndHandle) {
		this.isEndHandle = isEndHandle;
	}

	public Integer getIsStart() {
		return isStart;
	}

	public void setIsStart(Integer isStart) {
		this.isStart = isStart;
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
