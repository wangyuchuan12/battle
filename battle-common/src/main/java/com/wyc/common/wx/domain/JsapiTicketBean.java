package com.wyc.common.wx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;


@ParamEntityAnnotation(type=ParamEntityAnnotation.CONTEXT_TYPE)
@Entity(name="jsapi_ticket")
public class JsapiTicketBean {
    @Id
    @IdAnnotation
    private String id;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.CONTEXT_TYPE)
    private String errcode;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.CONTEXT_TYPE)
    private String errmsg;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.CONTEXT_TYPE)
    private String ticket;
    @Column
    @ParamAnnotation(type=ParamEntityAnnotation.CONTEXT_TYPE)
    private String expires_in;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    @Column(name = "begin_date_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime beginDateTime;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public DateTime getBeginDateTime() {
        return beginDateTime;
    }
    public void setBeginDateTime(DateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
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
    public String getErrcode() {
        return errcode;
    }
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
    public String getErrmsg() {
        return errmsg;
    }
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    public String getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
