package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.PaymentVoucherDao;
import com.battle.domain.PaymentVoucher;

@Service
public class PaymentVoucherService {

	@Autowired
	private PaymentVoucherDao paymentVoucherDao;

	public void add(PaymentVoucher paymentVoucher) {
		
		paymentVoucher.setId(UUID.randomUUID().toString());
		paymentVoucher.setCreateAt(new DateTime());
		paymentVoucher.setUpdateAt(new DateTime());
		
		paymentVoucherDao.save(paymentVoucher);
		
	}
}
