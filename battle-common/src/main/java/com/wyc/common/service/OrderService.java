package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.Order;
import com.wyc.common.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public void add(Order order) {
		order.setId(UUID.randomUUID().toString());
		
		order.setUpdateAt(new DateTime());
		order.setCreateAt(new DateTime());
		
		orderRepository.save(order);
		
	}

	public void update(Order order) {
		
		order.setUpdateAt(new DateTime());
		
		orderRepository.save(order);
		
	}

	public Order findOneByOutTradeNo(String outTradeNo) {
		
		return orderRepository.findOneByOutTradeNo(outTradeNo);
	}
}
