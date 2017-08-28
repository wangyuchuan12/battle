package com.wyc.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@Entity(name = "my_resource")

@ParamEntityAnnotation
public class MyResource {
    @Id
    @IdAnnotation
    private String id;
    @ParamAnnotation
    @Column(name = "url")
    private String url;
    @ParamAnnotation
    @Column(name="system_url")
    private String systemUrl;
    @ParamAnnotation
    @Column(name = "name")
    private String name;
    @ParamAnnotation
    @Column(name = "instruction")
    private String instruction;
    @JsonIgnore
    @Column(name = "data")
    @Lob
    @ParamAnnotation
    private byte[] data;
    @ParamAnnotation
    @Column(name = "suffix")
    private String suffix;
    @Column
    @ParamAnnotation
    private Integer type;
    
    @ParamAnnotation
    @Column(name="ref_id")
    private String refId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    
    
    
    
    public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSystemUrl() {
        return systemUrl;
    }
    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
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
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
}
