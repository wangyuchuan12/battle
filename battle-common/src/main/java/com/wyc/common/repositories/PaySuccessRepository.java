package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.PaySuccess;

public interface PaySuccessRepository extends CrudRepository<PaySuccess, String>{

	PaySuccess findOneByOutTradeNo(String outTradeNo);

}
