package com.wyc.common.wx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;

@Entity
@Table(name="share")
public class Share {
	@Id
    @IdAnnotation
    private String id;
	
	//链接id
	@ParamAnnotation
	@Column(name="href_id")
	private String hrefId;
	
	//url地址
	@ParamAnnotation
	@Column
	private String url;
	
	//发起人openid
	@ParamAnnotation
	@Column(name="sponsor_openid")
	private String sponsorOpenid;
	
	//发起时间
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@ParamAnnotation
	@Column(name="hand_time")
	private DateTime handTime;
	
	//分享类型
	@ParamAnnotation
	@Column
	private int type;
	
	//点击次数
	@ParamAnnotation
	@Column(name="click_count")
	private int clickCount;
	
	@Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
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
	
	
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public String getHrefId() {
		return hrefId;
	}
	public void setHrefId(String hrefId) {
		this.hrefId = hrefId;
	}
	public String getSponsorOpenid() {
		return sponsorOpenid;
	}
	public void setSponsorOpenid(String sponsorOpenid) {
		this.sponsorOpenid = sponsorOpenid;
	}
	public DateTime getHandTime() {
		return handTime;
	}
	public void setHandTime(DateTime handTime) {
		this.handTime = handTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
