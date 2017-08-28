package com.wyc.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pay_success_call_back")
public class PaySuccessCallback {
	
	 @Id
	 private String id;
	
	 @Column(name="out_trade_no")
	 private String outTradeNo;
	 
	 //指向的id
	 @Column(name="href_id")
	 private String hrefId;
	 
	 //类型
	 @Column
	 private int type;

}
