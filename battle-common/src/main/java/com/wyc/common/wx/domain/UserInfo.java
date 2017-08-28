package com.wyc.common.wx.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;


@ParamEntityAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
@Entity(name="user_info")
@Table(indexes={@Index(columnList="openid",name="openid_index"),
				@Index(columnList="token",name="token_index")})
public class UserInfo {
    @Id
    @IdAnnotation
    private String id;
    
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @Column(unique=true,updatable=false)
    private String openid;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String nickname;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String sex;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String province;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String city;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String country;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String headimgurl;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String[] privilege;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String unionid;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String language;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String remark;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String subscribe_time;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String groupid;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String subscribe;
    @Column(unique=true)
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String token;
    

    @Transient
    private List<Object> tagid_list;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    @Column
    private Long count;
    
    
    
  
	public List<Object> getTagid_list() {
		return tagid_list;
	}
	public void setTagid_list(List<Object> tagid_list) {
		this.tagid_list = tagid_list;
	}
	public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getSubscribe() {
        return subscribe;
    }
    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
    public String getSubscribe_time() {
        return subscribe_time;
    }
    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }
    public String getGroupid() {
        return groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getHeadimgurl() {
        return headimgurl;
    }
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    public String[] getPrivilege() {
        return privilege;
    }
    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }
    public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
