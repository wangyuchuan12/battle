package com.wyc.common.wx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name="token")
public class Token {
    @Id
    private String id;
    @Column
    private int status;
    @Column(unique=true,name="token_key")
    private String tokenKey;
    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime invalidDate;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    @Override
    public String toString() {
        return "id:"+id+",token_key:"+tokenKey;
    }
   
    public String getTokenkey() {
        return tokenKey;
    }
    public void setTokenKey(String token_key) {
        this.tokenKey = token_key;
    }
    public DateTime getInvalidDate() {
        return invalidDate;
    }
    public void setInvalidDate(DateTime invalidDate) {
        this.invalidDate = invalidDate;
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
