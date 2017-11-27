package com.battle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="battle_period")
public class BattlePeriod {
	
	//游离状态
	public static Integer FREE_STATUS = 0;
	
	//进行中状态
	public static Integer IN_STATUS = 1;
	
	@Id
	@IdAnnotation
	@AttrAnnotation(name=AttrEnum.periodId)
	private String id;
	
	@ParamAnnotation
	@Column(name="z_index")
	@AttrAnnotation(name=AttrEnum.periodIndex)
	private Integer index;
	
	@ParamAnnotation
	@Column(name="battle_id")
	private String battleId;
	
	//总距离
	@ParamAnnotation
	@Column(name="total_distance")
	private Integer totalDistance;
	
	@ParamAnnotation
	@Column(name="min_members")
	private Integer minMembers;
	
	@ParamAnnotation
	@Column(name="max_members")
	private Integer maxMembers;
	
	@ParamAnnotation
	@Column(name="stage_count")
	private Integer stageCount;
	
	//0表示停用状态 1表示使用中
	@ParamAnnotation
	@Column(name="status")
	private Integer status;
	
	
	//是否为默认
	@ParamAnnotation
	@Column(name="is_default")
	private Integer isDefault;
	
	@ParamAnnotation
	@Column
	private Integer unit;
	
	//出题人头像
	@ParamAnnotation
	@Column(name="owner_img")
	private String ownerImg;
	
	//出题人别呢
	@ParamAnnotation
	@Column(name="owner_nickname")
	private String ownerNickname;
	
	//总参与人数
	@ParamAnnotation
	@Column(name="takepart_count")
	private Integer takepartCount;
	
	//正确数量
	@ParamAnnotation
	@Column(name="right_count")
	private Integer rightCount;
	
	//错误数量
	@ParamAnnotation
	@Column(name="wrong_count")
	private Integer wrongCount;
	
	//作者
	@ParamAnnotation
	@Column(name="author_battle_user_id")
	private String authorBattleUserId;
	
	@ParamAnnotation
	@Column(name="is_public")
	private Integer isPublic;
	
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}
	
	

	public Integer getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Integer totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Integer getMinMembers() {
		return minMembers;
	}

	public void setMinMembers(Integer minMembers) {
		this.minMembers = minMembers;
	}

	public Integer getMaxMembers() {
		return maxMembers;
	}

	public void setMaxMembers(Integer maxMembers) {
		this.maxMembers = maxMembers;
	}
	

	public Integer getStageCount() {
		return stageCount;
	}

	public void setStageCount(Integer stageCount) {
		this.stageCount = stageCount;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	
	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getOwnerImg() {
		return ownerImg;
	}

	public void setOwnerImg(String ownerImg) {
		this.ownerImg = ownerImg;
	}

	public String getOwnerNickname() {
		return ownerNickname;
	}

	public void setOwnerNickname(String ownerNickname) {
		this.ownerNickname = ownerNickname;
	}

	public Integer getTakepartCount() {
		return takepartCount;
	}

	public void setTakepartCount(Integer takepartCount) {
		this.takepartCount = takepartCount;
	}

	public Integer getRightCount() {
		return rightCount;
	}

	public void setRightCount(Integer rightCount) {
		this.rightCount = rightCount;
	}

	public Integer getWrongCount() {
		return wrongCount;
	}

	public void setWrongCount(Integer wrongCount) {
		this.wrongCount = wrongCount;
	}
	
	public String getAuthorBattleUserId() {
		return authorBattleUserId;
	}

	public void setAuthorBattleUserId(String authorBattleUserId) {
		this.authorBattleUserId = authorBattleUserId;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
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
