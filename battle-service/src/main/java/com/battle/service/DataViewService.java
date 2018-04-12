package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.DataViewDao;
import com.battle.domain.DataView;

@Service
public class DataViewService {

	@Autowired
	private DataViewDao dataViewDao;
	public DataView findOneByCode(String code) {
		
		return dataViewDao.findOneByCode(code);
	}
	public void add(DataView dataView) {
		
		dataView.setId(UUID.randomUUID().toString());
		dataView.setUpdateAt(new DateTime());
		dataView.setCreateAt(new DateTime());
		
		dataViewDao.save(dataView);
		
	}
	public void update(DataView dataView) {
		
		dataView.setUpdateAt(new DateTime());
		
		dataViewDao.save(dataView);
		
	}

}
