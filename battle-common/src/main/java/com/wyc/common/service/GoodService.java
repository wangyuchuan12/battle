package com.wyc.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.Good;
import com.wyc.common.repositories.GoodRepository;

@Service
public class GoodService {

	@Autowired
	private GoodRepository goodRepository;

	public List<Good> findAllByStatusAndTypeOrderByIndexAsc(Integer status, Integer type) {
		return goodRepository.findAllByStatusAndTypeOrderByIndexAsc(status,type);
	}

	public List<Good> findAllByStatusOrderByIndexAsc(Integer status) {
		return goodRepository.findAllByStatusOrderByIndexAsc(status);
	}

	public Good findOne(String id) {
		return goodRepository.findOne(id);
	}
}
