package com.battle.service.dbExectuer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wyc.common.session.DbServiceExecuter;

@Service
public class DbServiceExecuterImp implements DbServiceExecuter{

	@Override
	public void update(List<Object> objs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T findOne(Class<T> clazz, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
