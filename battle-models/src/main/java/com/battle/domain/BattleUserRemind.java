package com.battle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
@Entity
@Table(name="battle_user_remind")
public class BattleUserRemind {
	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column
	private Integer type;
	
	@ParamAnnotation
	@Column(name="user_id")
	private String userId;
	
	@ParamAnnotation
	@Column
	private Integer status;
	
	@ParamAnnotation
	@Column
	private Integer rank;
	
	@ParamAnnotation
	@Column(name="reward_bean")
	private Integer rewardBean;
}
