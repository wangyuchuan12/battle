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

@ParamEntityAnnotation
@Entity
@Table(name="battle_red_packet_amount_distribution")
public class BattleRedPacketAmountDistribution {
	
	public static Integer STATUS_FREE = 0;
	
	public static Integer STATUS_DISTRIBUTION=1;
	
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="red_packet_id",nullable=false)
	private String redPacketId;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column(name="room_id",nullable=false)
	private String roomId;
	
	@ParamAnnotation
	@Column(name="member_id")
	private String memberId;
	
	@ParamAnnotation
	@Column(name="img_url")
	private String imgUrl;
	
	@ParamAnnotation
	@Column
	private String nickname;
	
	@ParamAnnotation
	@Column(nullable=false)
	private BigDecimal amount;
	
	@ParamAnnotation
	@Column(name="bean_num")
	private Long beanNum;
	
	@ParamAnnotation
	@Column(name="mastonry_num")
	private Long mastonryNum;
	
	@ParamAnnotation
	@Column(nullable=false)
	private Integer seq;
	
	@ParamAnnotation
	@Column(nullable=false)
	private Integer status;
	
	@ParamAnnotation
	@Column(name="receive_time")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime receiveTime;
	
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
	public String getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(String redPacketId) {
		this.redPacketId = redPacketId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public DateTime getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(DateTime receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Long getBeanNum() {
		return beanNum;
	}
	public void setBeanNum(Long beanNum) {
		this.beanNum = beanNum;
	}
	public Long getMastonryNum() {
		return mastonryNum;
	}
	public void setMastonryNum(Long mastonryNum) {
		this.mastonryNum = mastonryNum;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
