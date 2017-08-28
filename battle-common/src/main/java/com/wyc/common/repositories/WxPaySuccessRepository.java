package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.PaySuccess;


public interface WxPaySuccessRepository extends CrudRepository<PaySuccess, String>{
    public PaySuccess findByOutTradeNo(String outTradeNo);
}
