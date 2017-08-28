package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.PaySuccess;
import com.wyc.common.repositories.PaySuccessRepository;

@Service
public class PaySuccessService {
	
	@Autowired
	private PaySuccessRepository paySuccessRepository;

	public PaySuccess add(PaySuccess paySuccess) {
		
		paySuccess.setUpdateAt(new DateTime());
		paySuccess.setCreateAt(new DateTime());
		paySuccess.setId(UUID.randomUUID().toString());
		
		return paySuccessRepository.save(paySuccess);
		
	}

	public PaySuccess findOneByOutTradeNo(String outTradeNo) {
		return paySuccessRepository.findOneByOutTradeNo(outTradeNo);
	}

	public PaySuccess save(PaySuccess paySuccess) {
		paySuccess.setUpdateAt(new DateTime());
		
		return paySuccessRepository.save(paySuccess);
	}
}
