package com.wyc.common.session;

import java.util.List;

public interface DbServiceExecuter {
	public void update(List<Object> objs);
	
	public <T> T findOne(Class<T> clazz , String id);
}
