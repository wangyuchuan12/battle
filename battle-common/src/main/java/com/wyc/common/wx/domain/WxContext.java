package com.wyc.common.wx.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity(name="wx_context")
public class WxContext {
    @Id@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column
    private String appid;
    @Column
    private String appsecret;
    @Column(name="file_path")
    private String filePath;
    @Column
    private String flag;
    @Column(name="domain_name")
    private String domainName;
    @Column(name="mch_id")
    private String mchId;
    @Column(name="mac_key")
    private String key;
       
    //转账手续费 小数点
    @Column(name="transfer_fee")
    private Integer transferFee;
    
    //提现即时到账
    @Column(name="instant_arrival")
    private Integer instantArrival;
    
    //一天处理提现到账人数
    @Column(name="arrival_num")
    private Integer arrivalNum;
    
    //分享几个人可看答案
    @Column(name="share_num_show_answer")
    private Integer shareNumShowAnswer;
    
    public Integer getShareNumShowAnswer() {
		return shareNumShowAnswer;
	}
	public void setShareNumShowAnswer(Integer shareNumShowAnswer) {
		this.shareNumShowAnswer = shareNumShowAnswer;
	}
	public Integer getArrivalNum() {
		return arrivalNum;
	}
	public void setArrivalNum(Integer arrivalNum) {
		this.arrivalNum = arrivalNum;
	}
	public Integer getTransferFee() {
		return transferFee;
	}
	public void setTransferFee(Integer transferFee) {
		this.transferFee = transferFee;
	}
	
	
	public Integer getInstantArrival() {
		return instantArrival;
	}
	public void setInstantArrival(Integer instantArrival) {
		this.instantArrival = instantArrival;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDomainName() {
        return domainName;
    }
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getAppsecret() {
        return appsecret;
    }
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
