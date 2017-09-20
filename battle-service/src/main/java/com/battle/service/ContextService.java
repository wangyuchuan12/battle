package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.ContextDao;
import com.battle.domain.Context;

@Service
public class ContextService {
	@Autowired
	private ContextDao contextDao;

	public Context findOneByCodeBySync(String code) {
		return contextDao.findOneByCode(code);
	}

	public void add(Context context) {
		
		context.setId(UUID.randomUUID().toString());
		context.setUpdateAt(new DateTime());
		context.setCreateAt(new DateTime());
		contextDao.save(context);
		
	}
	
	public void update(Context context){
		context.setUpdateAt(new DateTime());
		contextDao.save(context);
	}
}
