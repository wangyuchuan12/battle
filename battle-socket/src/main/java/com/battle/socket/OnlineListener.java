package com.battle.socket;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.battle.domain.DataView;
import com.battle.domain.UserStatus;
import com.battle.service.DataViewService;
import com.battle.service.UserStatusService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class OnlineListener {

	@Autowired
	private WxUserInfoService userInfoService;
	
	@Autowired
	private UserStatusService userStatusService;
	
	@Autowired
	private DataViewService dataViewService;
	
	@Autowired
    private PlatformTransactionManager platformTransactionManager;

	
	@Transactional
	public void onLine(final String id){
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	
    	TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);
		UserInfo userInfo = userInfoService.findOne(id);
		
		UserStatus userStatus = null;
		if(!CommonUtil.isEmpty(userInfo.getStatusId())){
			userStatus = userStatusService.findOne(userInfo.getStatusId());
		}
		
		if(userStatus==null){
			userStatus = new UserStatus();
			userStatus.setIsLine(1);
			userStatus.setToken(userInfo.getToken());
			userStatus.setUserId(userInfo.getId());
			userStatusService.add(userStatus);
			
			userInfo.setStatusId(userStatus.getId());
			userInfoService.update(userInfo);
		}
		
		userStatus.setIsLine(1);
		
		userStatusService.update(userStatus);
		
		/*
		DataView dataView = dataViewService.findOneByCode(DataView.ONELINE_NUM_CODE);
    	String value = dataView.getValue();
    	Integer num = 0;
    	if(CommonUtil.isNotEmpty(value)){
    		num = Integer.parseInt(value);
    	}
    	num++;
    	dataView.setValue(num+"");
    	dataViewService.update(dataView);*/
    	
    	platformTransactionManager.commit(transactionStatus);
		
	}
	
	
	@Transactional
	public void downLine(final String id){

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	
    	TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);
		UserInfo userInfo = userInfoService.findOne(id);
		
		UserStatus userStatus = userStatusService.findOne(userInfo.getStatusId());
		
		userStatus.setIsLine(0);
		
		userStatusService.update(userStatus);
		
		/*
		DataView dataView = dataViewService.findOneByCode(DataView.ONELINE_NUM_CODE);
    	String value = dataView.getValue();
    	Integer num = 0;
    	if(CommonUtil.isNotEmpty(value)){
    		num = Integer.parseInt(value);
    	}
    	num--;
    	if(num<0){
    		num = 0;
    	}
    	dataView.setValue(num+"");
    	dataViewService.update(dataView);*/
    	
    	platformTransactionManager.commit(transactionStatus);

	}
}
