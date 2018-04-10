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
    
    //短信appid
    @Column(name="submail_appid")
    private String submailAppid;
    
    //短信秘钥
    @Column(name="submail_signature")
    private String submailSignature;
    
    @Column(name="apply_expert_project_code")
    private String applyExpertProjectCode;
 
    @Column(name="qn_access_key")
    private String qnAccessKey;
    
    @Column(name="qn_scret_key")
    private String qnScretKey;
    
    @Column(name="qn_bucketname")
    private String qnBucketname;
    
    @Column(name="qn_domain")
    private String qnDomain;
    
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
	public String getSubmailAppid() {
		return submailAppid;
	}
	public void setSubmailAppid(String submailAppid) {
		this.submailAppid = submailAppid;
	}
	public String getSubmailSignature() {
		return submailSignature;
	}
	public void setSubmailSignature(String submailSignature) {
		this.submailSignature = submailSignature;
	}
	public String getApplyExpertProjectCode() {
		return applyExpertProjectCode;
	}
	public void setApplyExpertProjectCode(String applyExpertProjectCode) {
		this.applyExpertProjectCode = applyExpertProjectCode;
	}
	public String getQnAccessKey() {
		return qnAccessKey;
	}
	public void setQnAccessKey(String qnAccessKey) {
		this.qnAccessKey = qnAccessKey;
	}
	public String getQnScretKey() {
		return qnScretKey;
	}
	public void setQnScretKey(String qnScretKey) {
		this.qnScretKey = qnScretKey;
	}
	public String getQnBucketname() {
		return qnBucketname;
	}
	public void setQnBucketname(String qnBucketname) {
		this.qnBucketname = qnBucketname;
	}
	public String getQnDomain() {
		return qnDomain;
	}
	public void setQnDomain(String qnDomain) {
		this.qnDomain = qnDomain;
	}
}
