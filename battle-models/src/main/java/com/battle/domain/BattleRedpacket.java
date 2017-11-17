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

//比赛问题
@ParamEntityAnnotation
@Entity
@Table(name="battle_redpacket")
public class BattleRedpacket {
	
	
	//参与消耗智慧豆
	public static final Integer TAKEPART_COST_BEAN_TYPE = 0;
		
	//参与消耗零钱
	public static final Integer  TAKEPART_COST_CHANGE_TYPE = 1;
		
	//参与消耗砖石
	public static final Integer  TAKEPART_COST_MASONRY_TYPE=2;
	
	
	//发起者消耗智慧
	public static final Integer COST_BEAN_TYPE = 0;
	
	//发起者消耗零钱
	public static final Integer COST_CHANGE_TYPE = 1;
	
	//发起者消耗砖石
	public static final Integer COST_MASONRY_TYPE=2;
	
	
	//游离状态
	public static final Integer STATUS_FREE = 0;
	
	//进行中
	public static final Integer STATUS_IN = 1;
	
	//已经抢完
	public static final Integer STATUS_END = 2;
	
	//时间到
	public static final Integer STATUS_TIME_OUT = 3;
	@Id
	@IdAnnotation
	private String id;
	
	
	//1进行中 2已抢完 3已超时
	@ParamAnnotation
	@Column
	private Integer status;
	
	
	//红包数量
	@ParamAnnotation
	@Column
	private Integer num;
	
	//已领取的数量
	@ParamAnnotation
	@Column(name="receive_num")
	private Integer receiveNum;
	
	//提交时间
	@ParamAnnotation
	@Column(name="hand_time")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime handTime;
	
	//结束时间
	@ParamAnnotation
	@Column(name="end_time")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime endTime;
	
	//红包到期时长，单位是秒
	@ParamAnnotation
	@Column(name="time_long")
	private Long timeLong;
	
	//金额
	@ParamAnnotation
	@Column
	private BigDecimal amount;
	
	@ParamAnnotation
	@Column(name="bean_num")
	private Long beanNum;
	
	@ParamAnnotation
	@Column(name="masonry_num")
	private Long masonryNum;
	
	//已领取金额
	@ParamAnnotation
	@Column(name="receive_amount")
	private BigDecimal receiveAmount;
	
	//是否是房间红包
	@ParamAnnotation
	@Column(name="is_room")
	private Integer isRoom;
	
	//房间id
	@ParamAnnotation
	@Column(name="room_id")
	private String roomId;
	
	//发起者消耗智慧豆数量
	@ParamAnnotation
	@Column(name="cost_bean")
	private Long costBean;
	
	//发起者消耗砖石数量
	@ParamAnnotation
	@Column(name="cost_masonry")
	private Long costMasonry;
	
	//发起者消耗金额
	@ParamAnnotation
	@Column(name="cost_amount")
	private BigDecimal costAmount;
	
	
	//消耗类型 0智慧豆 1零钱 2砖石
	@ParamAnnotation
	@Column(name="cost_type")
	private Integer costType;
	
	//参与者消耗智慧豆
	@ParamAnnotation
	@Column(name="takepart_cost_bean")
	private Long takepartCostBean;
	
	//参与者消耗砖石数量
	@ParamAnnotation
	@Column(name="takepart_cost_masonry")
	private Long takepartCostMasonry;
	
	//是否需要人数满足
	@ParamAnnotation
	@Column(name="is_room_meet")
	private Integer isRoomMeet;
	
	//人数满足数量，如果是0表示要整个房间人数充满为止
	@ParamAnnotation
	@Column(name="room_Meet_num")
	private Integer roomMeetNum;
	
	//是否满足进程的要求
	@ParamAnnotation
	@Column(name="is_room_process_meet")
	private Integer isRoomProcessMeet;
	
	//是否满足进程的要求
	@ParamAnnotation
	@Column(name="is_personal_process_meet")
	private Integer isPersonalProcessMeet;
	
	
	//是否需要满足房间分数要求
	@ParamAnnotation
	@Column(name="is_room_score_meet")
	private Integer isRoomScoreMeet;
	
	//是否需要满足个人分数要求
	@ParamAnnotation
	@Column(name="is_personal_score_meet")
	private Integer isPersonalScoreMeet;
	

	//满足要求的房间分数值
	@ParamAnnotation
	@Column(name="room_score_meet")
	private Integer roomScoreMeet;
	
	//满足要求的个人分数值
	@ParamAnnotation
	@Column(name="personal_score_meet")
	private Integer personalScoreMeet;
	
	
	
	//满足房间进程的要求值是多少
	@ParamAnnotation
	@Column(name="room_process_meet")
	private Integer roomProcessMeet;
	
	//满足个人要求进程的要求值是多少
	@ParamAnnotation
	@Column(name="personal_process_meet")
	private Integer personalProcessMeet;
	
	
	@ParamAnnotation
	@Column(name="type_id")
	private String typeId;
	
	@ParamAnnotation
	@Column
	private String name;
	
	@ParamAnnotation
	@Column(name="sender_name")
	private String senderName;
	
	@ParamAnnotation
	@Column(name="sender_img")
	private String senderImg;
	
	@ParamAnnotation
	@Column(name="stage_index")
	private Integer stageIndex;
	
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

	public DateTime getHandTime() {
		return handTime;
	}

	public void setHandTime(DateTime handTime) {
		this.handTime = handTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	public Long getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Long timeLong) {
		this.timeLong = timeLong;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Integer getIsRoom() {
		return isRoom;
	}

	public void setIsRoom(Integer isRoom) {
		this.isRoom = isRoom;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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

	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
	}

	public Long getTakepartCostBean() {
		return takepartCostBean;
	}

	public void setTakepartCostBean(Long takepartCostBean) {
		this.takepartCostBean = takepartCostBean;
	}

	public Long getTakepartCostMasonry() {
		return takepartCostMasonry;
	}

	public void setTakepartCostMasonry(Long takepartCostMasonry) {
		this.takepartCostMasonry = takepartCostMasonry;
	}

	public Integer getIsRoomMeet() {
		return isRoomMeet;
	}

	public void setIsRoomMeet(Integer isRoomMeet) {
		this.isRoomMeet = isRoomMeet;
	}

	public Integer getRoomMeetNum() {
		return roomMeetNum;
	}

	public void setRoomMeetNum(Integer roomMeetNum) {
		this.roomMeetNum = roomMeetNum;
	}

	public Integer getIsRoomProcessMeet() {
		return isRoomProcessMeet;
	}

	public void setIsRoomProcessMeet(Integer isRoomProcessMeet) {
		this.isRoomProcessMeet = isRoomProcessMeet;
	}

	public Integer getIsPersonalProcessMeet() {
		return isPersonalProcessMeet;
	}

	public void setIsPersonalProcessMeet(Integer isPersonalProcessMeet) {
		this.isPersonalProcessMeet = isPersonalProcessMeet;
	}

	public Integer getIsRoomScoreMeet() {
		return isRoomScoreMeet;
	}

	public void setIsRoomScoreMeet(Integer isRoomScoreMeet) {
		this.isRoomScoreMeet = isRoomScoreMeet;
	}

	public Integer getIsPersonalScoreMeet() {
		return isPersonalScoreMeet;
	}

	public void setIsPersonalScoreMeet(Integer isPersonalScoreMeet) {
		this.isPersonalScoreMeet = isPersonalScoreMeet;
	}

	public Integer getRoomScoreMeet() {
		return roomScoreMeet;
	}

	public void setRoomScoreMeet(Integer roomScoreMeet) {
		this.roomScoreMeet = roomScoreMeet;
	}

	public Integer getPersonalScoreMeet() {
		return personalScoreMeet;
	}

	public void setPersonalScoreMeet(Integer personalScoreMeet) {
		this.personalScoreMeet = personalScoreMeet;
	}

	public Integer getRoomProcessMeet() {
		return roomProcessMeet;
	}

	public void setRoomProcessMeet(Integer roomProcessMeet) {
		this.roomProcessMeet = roomProcessMeet;
	}

	public Integer getPersonalProcessMeet() {
		return personalProcessMeet;
	}

	public void setPersonalProcessMeet(Integer personalProcessMeet) {
		this.personalProcessMeet = personalProcessMeet;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderImg() {
		return senderImg;
	}

	public void setSenderImg(String senderImg) {
		this.senderImg = senderImg;
	}

	public Integer getStageIndex() {
		return stageIndex;
	}

	public void setStageIndex(Integer stageIndex) {
		this.stageIndex = stageIndex;
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
