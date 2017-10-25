package com.battle.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.AttrEnum;
import com.wyc.annotation.AttrAnnotation;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
@Entity
@Table(name="battle_expert")
public class BattleExpert {
	
	public static Integer APPLY_STATUS=0;
	
	public static Integer AUDIT_STATUS=1;
	
	public static Integer RJECT_STATUS=2;
	
	public static Integer REVOKE_STATUS=3;
	@Id
	@IdAnnotation
	@AttrAnnotation(name=AttrEnum.battleId)
	private String id;
	
	//对应用户
	@ParamAnnotation
	@Column(name="battle_user_id")
	private String battleUserId;
	
	//审核通过管理员
	@ParamAnnotation
	@Column(name="audit_battle_user_id")
	private String auditBattleUserId;
	
	//审批审核不通过管理员
	@ParamAnnotation
	@Column(name="reject_battle_user_id")
	private String rejectBattleUserId;
	
	//吊销资格管理员
	@ParamAnnotation
	@Column(name="revoke_battle_user_id")
	private String revokeBattleUserId;
	
	//出题数量
	@ParamAnnotation
	@Column(name="hand_num")
	private Integer handNum;
	
	//是否已经出题
	@ParamAnnotation
	@Column(name="is_hand")
	private Integer isHand;
	
	//微信号
	@ParamAnnotation
	@Column
	private String wechat;
	
	//手机号码
	@ParamAnnotation
	@Column
	private String phonenum;
	
	
	//0表示审核阶段 1审核通过 2吊销专家资格
	@ParamAnnotation
	@Column
	private Integer status;
	
	//吊销资格理由
	@ParamAnnotation
	@Column(name="revoke_reason")
	private String revokeReason;
	
	//拒绝理由
	@ParamAnnotation
	@Column(name="reject_reason")
	private String rejectReason;
	
	//比赛id
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	//备注
	@ParamAnnotation
	@Column
	private String remark;
	
	
	//自我介绍
	@Lob
	@ParamAnnotation
	@Column
	private String introduce;
	
	
	@ParamAnnotation
	@Column
	private String nickname;
	
	@ParamAnnotation
	@Column(name="user_img")
	private String userImg;
	
	@ParamAnnotation
	@Column
	private String openid;
	
	@ParamAnnotation
	@Column(name = "apply_date_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime applyDateTime;
	
	@ParamAnnotation
	@Column(name = "audit_date_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime auditDateTime;
	
	@ParamAnnotation
	@Column(name = "revoke_date_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime revokeDateTime;
	
	@ParamAnnotation
	@Column(name = "reject_date_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime rejectDateTime;
	
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

	public String getBattleUserId() {
		return battleUserId;
	}

	public void setBattleUserId(String battleUserId) {
		this.battleUserId = battleUserId;
	}

	public String getAuditBattleUserId() {
		return auditBattleUserId;
	}

	public void setAuditBattleUserId(String auditBattleUserId) {
		this.auditBattleUserId = auditBattleUserId;
	}

	public String getRevokeBattleUserId() {
		return revokeBattleUserId;
	}

	public void setRevokeBattleUserId(String revokeBattleUserId) {
		this.revokeBattleUserId = revokeBattleUserId;
	}

	public Integer getHandNum() {
		return handNum;
	}

	public void setHandNum(Integer handNum) {
		this.handNum = handNum;
	}

	public Integer getIsHand() {
		return isHand;
	}

	public void setIsHand(Integer isHand) {
		this.isHand = isHand;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRevokeReason() {
		return revokeReason;
	}

	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public DateTime getApplyDateTime() {
		return applyDateTime;
	}

	public void setApplyDateTime(DateTime applyDateTime) {
		this.applyDateTime = applyDateTime;
	}

	public DateTime getAuditDateTime() {
		return auditDateTime;
	}

	public void setAuditDateTime(DateTime auditDateTime) {
		this.auditDateTime = auditDateTime;
	}

	public DateTime getRevokeDateTime() {
		return revokeDateTime;
	}

	public void setRevokeDateTime(DateTime revokeDateTime) {
		this.revokeDateTime = revokeDateTime;
	}
	
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getRejectBattleUserId() {
		return rejectBattleUserId;
	}

	public void setRejectBattleUserId(String rejectBattleUserId) {
		this.rejectBattleUserId = rejectBattleUserId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public DateTime getRejectDateTime() {
		return rejectDateTime;
	}

	public void setRejectDateTime(DateTime rejectDateTime) {
		this.rejectDateTime = rejectDateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
