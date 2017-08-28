package com.wyc.common.wx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity(name = "access_token_bean")
public class AccessTokenBean {
    @Id
    private String id;
    @Column(name="access_token")
    @JsonProperty(value="access_token")
    private String accessToken;
    @Column(name="expires_in")
    @JsonProperty(value="expires_in")
    private String expiresIn;
    @Column(unique=true)
    private String token;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
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
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String access_token) {
        this.accessToken = access_token;
    }
    public String getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(String expires_in) {
        this.expiresIn = expires_in;
    }
}
