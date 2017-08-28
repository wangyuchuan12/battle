package com.wyc.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="href")
public class Href {
	@Id
    private String id;
	
	//地址
	@Column
	private String address;
	
	//点击次数
	@Column(name="click_count")
	private int clickCount;
	
	//链接编号
	@Column
	private String code;
	
	@Column(name="share_count")
	private int shareCount;
	
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
	
	
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
