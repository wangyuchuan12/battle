package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

	Order findOneByOutTradeNo(String outTradeNo);

}
