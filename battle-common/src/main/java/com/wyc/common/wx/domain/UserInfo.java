package com.wyc.common.wx.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@ParamEntityAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
@Entity(name="user_info")
@Table(indexes={@Index(columnList="openid",name="openid_index"),
				@Index(columnList="token",name="token_index")})
public class UserInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @IdAnnotation
    @AttrAnnotation(name=AttrEnum.userInfoId)
    private String id;
    
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @Column(unique=true,updatable=false)
    @AttrAnnotation(name=AttrEnum.userInfoOpenId)
    private String openid;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @AttrAnnotation(name=AttrEnum.userInfoNickname)
    private String nickname;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @AttrAnnotation(name=AttrEnum.userInfoGender)
    private String sex;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @AttrAnnotation(name=AttrEnum.userInfoProvince)
    private String province;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @AttrAnnotation(name=AttrEnum.userInfoCity)
    private String city;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @AttrAnnotation(name=AttrEnum.userInfoCountry)
    private String country;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @AttrAnnotation(name=AttrEnum.userInfoAvatarUrl)
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
    
    //0表示从 公众号来 1表示从小程序而来
    //来源
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private Integer source;
    
    //账户id
    @Column(name="account_id")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String accountId;
    
    @Column(name="is_create_frend_group")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private Integer isCreateFrendGroup;
    
    @Column(name="is_sync_dan")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private Integer isSyncDan;
    
    //一切的主宰
    @Column(name="is_god")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private Integer isGod;
    
    @Column(name="status_id")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    private String statusId;
    
    @Column(name = "create_at")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
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
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getIsGod() {
		return isGod;
	}
	public void setIsGod(Integer isGod) {
		this.isGod = isGod;
	}
	public Integer getIsCreateFrendGroup() {
		return isCreateFrendGroup;
	}
	public void setIsCreateFrendGroup(Integer isCreateFrendGroup) {
		this.isCreateFrendGroup = isCreateFrendGroup;
	}
	public Integer getIsSyncDan() {
		return isSyncDan;
	}
	public void setIsSyncDan(Integer isSyncDan) {
		this.isSyncDan = isSyncDan;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
}
