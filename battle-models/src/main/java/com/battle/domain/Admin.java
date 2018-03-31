package com.battle.domain;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "admin")
public class Admin{
    public static final int MANAGER_ROLE = 1;
    public static final int USER_ROLE = 2;
    public static final int BOY_SEX = 1;
    public static final int GIRL_SEX = 2 ;
    public static final int UNSPECIFIED_SEX = 0;
    @Id
    private String id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;
    @JsonIgnore
    @Column(name = "salt", length = 50)
    private String salt;
    @Column
    private int sex;
    @Column(name = "roles")
    private String roles;

    @Column(name = "realname", length = 100)
    private String realname;

    @Column(name = "mobile", length = 50)
    private String mobile;

    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "last_login")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime lastLogin;

    // 此字段是为设置密码过期的字段；
    @Column(name = "over_date")
    @JsonIgnore
    private Timestamp over_date;

    @CreatedDate
    @Column(name = "created_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createdAt;
    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updatedAt;
    @JsonIgnore
    @LastModifiedDate
    @Column(name = "deadline")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deadline;
    
    
    
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

   

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return realname;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// java的Timestamp与mysql的datetime一一对应
    public Timestamp getOver_date() {
        return over_date;
    }

    public void setOver_date(Timestamp over_date) {
        this.over_date = over_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
