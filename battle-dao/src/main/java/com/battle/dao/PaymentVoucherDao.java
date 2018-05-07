package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.PaymentVoucher;

public interface PaymentVoucherDao extends CrudRepository<PaymentVoucher, String>{

}
