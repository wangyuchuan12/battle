package com.wyc.common.session;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;
import com.wyc.common.filter.Filter;
import com.wyc.common.smart.service.RedisService;
import com.wyc.common.util.CommonUtil;
class Param{
	private String type;
	private String name;
	private Object value;
	private String id;
	private String entityName;
	private Field field;
	private boolean isId;
	private boolean isMap;
	public boolean isMap() {
		return isMap;
	}
	public void setIsMap(boolean isMap) {
		this.isMap = isMap;
	}
	public boolean isId() {
		return isId;
	}
	public void setIsId(boolean isId) {
		this.isId = isId;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}

public class SessionManager {
	
	
	private Object returnValue;
	
	private boolean isEnd;
	
	private boolean isReturn;
	private HttpServletRequest httpServletRequest;
	private HttpSession httpSession;
	private ServletContext servletContext;
	
	private Object params;
	
	private Map<String,Object> returnAttribute;
	
	private HttpServletResponse httpServletResponse;
	
	public static final String contextId = UUID.randomUUID().toString();
	

	private Map<String, Object> attributes = new HashMap<>();
	
	@Autowired
	private DbServiceExecuter updateExecuter;
	
	@Autowired
	private RedisService redisService;
	

//	private static final ThreadLocal<SessionManager> filterManagerThreadLocal = new ThreadLocal<>();
	
	private List<Class<? extends Filter>> notExecuteFilterClasses = new ArrayList<>();
	
	final static Logger logger = LoggerFactory.getLogger(SessionManager.class);
	
	final static String CONTEXT_PREFIX = "/context";
	
	public void addNotExecuteFilterClass(Class<? extends Filter> filterClass){
		notExecuteFilterClasses.add(filterClass);
	}
	
	public boolean containNotExecuteFilter(Class<? extends Filter> filterClass){
		for(Class<? extends Filter> entryClass:notExecuteFilterClasses){
			if(filterClass.equals(entryClass)){
				return true;
			}
		}
		return false;
	}


	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}
	
	public void setAttribute(String name , Object value){
		attributes.put(name, value);
	}
	
	public Object getAttribute(String name){
		return attributes.get(name);
	}


	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}


	public boolean isReturn() {
		return isReturn;
	}


	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}


	public Map<String, Object> getReturnAttribute() {
		return returnAttribute;
	}


	public void setReturnAttribute(Map<String, Object> returnAttribute) {
		this.returnAttribute = returnAttribute;
	}


	public Object getReturnValue() {
		return returnValue;
	}


	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}


	public boolean isEnd() {
		return isEnd;
	}


	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}


	private SessionManager(HttpServletRequest httpServletRequest,Class<?> paramClass)throws Exception{
		this.httpServletRequest = httpServletRequest;
		this.httpSession = this.httpServletRequest.getSession();
		this.servletContext = this.httpServletRequest.getServletContext();
		
		if(paramClass.isAssignableFrom(Map.class)){
			Map<String, Object> obj = toParamObjectOfMap(httpServletRequest);
			params = obj;
		}else{
			params = toParamObject(paramClass, httpServletRequest);
			save(params);
		}
	}
	
	
	public static synchronized SessionManager getFilterManager(HttpServletRequest httpServletRequest,Class<?> paramClass)throws Exception{
		/*SessionManager<?> filterManager = filterManagerThreadLocal.get();
		if(filterManager==null){
			filterManager = new SessionManager<>(httpServletRequest, paramClass);
			filterManagerThreadLocal.set(filterManager);
		}
		return filterManager;*/
		
		String sessionManagerId = "sessionManager_"+httpServletRequest.getRequestedSessionId();
		SessionManager filterManager = null;
		if(httpServletRequest.getAttribute(sessionManagerId)!=null){
			filterManager = (SessionManager)httpServletRequest.getAttribute(sessionManagerId);
		}else{
			filterManager = new SessionManager(httpServletRequest, paramClass);
			httpServletRequest.setAttribute(sessionManagerId, filterManager);
		}
		return filterManager;
		
	}
	
	public static synchronized SessionManager getFilterManager(HttpServletRequest httpServletRequest){
//		return filterManagerThreadLocal.get();
		String sessionManagerId = "sessionManager_"+httpServletRequest.getRequestedSessionId();
		SessionManager sessionManager = (SessionManager)httpServletRequest.getAttribute(sessionManagerId);
		return sessionManager;
		
	}
	
	
	
	public Object getParams() {
		return params;
	}


	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}


	public void rawSaveToRequest(String key,Object value){
		
		httpServletRequest.setAttribute(key, value);
	}
	
	public void rawSaveToSession(String key , Object value){
		httpSession.setAttribute(key, value);
	}
	
	public void rawSaveToContext(String key , Object value){
		servletContext.setAttribute(key, value);
	}
	
	public void rawSaveToRedis(String key,Object value)throws Exception{
		redisService.setObject(key, value);
	}
	
	public Object rawGetByRedis(String key , Class<?> type){
		return redisService.getObject(key, type);
	}
	
	public void rawRemoveKeyByRequest(String key){
		httpServletRequest.removeAttribute(key);
	}
	
	public void rawRemoveKeyBySession(String key){
		httpServletRequest.getSession().removeAttribute(key);
	}
	
	public void rawRemoveKeyByContext(String key){
		httpServletRequest.getSession().getServletContext().removeAttribute(key);
	}
	
	public Object rawGetByRequest(String key){
		return httpServletRequest.getAttribute(key);
	}
	
	
	
	public Object rawGetBySession(String key){
		return httpSession.getAttribute(key);
	}
	
	public Object rawGetByContext(String key){
		return servletContext.getAttribute(key);
	}
	
	
	private Map<String, Object> toParamObjectOfMap(HttpServletRequest httpServletRequest)throws Exception{
		Enumeration<String> enumeration = httpServletRequest.getParameterNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 map.put(name,httpServletRequest.getParameter(name));
		 }
		 return map;
	}
	
	private Object toParamObject(Class<?> clazz ,HttpServletRequest httpServletRequest)throws Exception{
		
		
			Object target = clazz.newInstance();
			
			Field[] fileds = clazz.getDeclaredFields();
			for(Field field:fileds){
				field.setAccessible(true);
				ParamAnnotation paramAnnotation2 = field.getAnnotation(ParamAnnotation.class);
				if(paramAnnotation2!=null){
					String name = null;
					if(paramAnnotation2.name().equals("")){
						name = field.getName();
					}else{
						name = paramAnnotation2.name();
					}
					String value = httpServletRequest.getParameter(name);
					field.set(target, value);
				}
			}
			
			save(target);
			return target;
	}
		
	
	
	
	public Map<String, Object> getAllContextAttributes(){
		 Enumeration<String> enumeration = httpServletRequest.getSession().getServletContext().getAttributeNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 if(!name.startsWith("org.springframework")&&
					 !name.startsWith("com.sun")&&
					 !name.startsWith("sessionManager")&&
					 !name.startsWith("CharacterEncodingFilter")&&
					 !name.startsWith("org.eclipse")&&
					 !name.startsWith("org.apache")&&
					 !name.startsWith("javax.servlet")){
				 map.put(name,httpServletRequest.getSession().getServletContext().getAttribute(name));
			 }
				
		 }
		 return map;
	}
	
	
	public Map<String, Object> getAllSessionAttributes(){
		 Enumeration<String> enumeration = httpServletRequest.getSession().getAttributeNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 if(!name.startsWith("org.springframework")&&
					 !name.startsWith("com.sun")&&
					 !name.startsWith("sessionManager")&&
					 !name.startsWith("CharacterEncodingFilter")&&
					 !name.startsWith("org.eclipse")&&
					 !name.startsWith("org.apache")&&
					 !name.startsWith("javax.servlet")){
				 	map.put(name,httpServletRequest.getSession().getAttribute(name));
			 }
		 }
		 return map;
	}
	
	
	public Map<String, Object> getAllRequestAttributes(){
		 Enumeration<String> enumeration = httpServletRequest.getAttributeNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 if(!name.startsWith("org.springframework")&&
					 !name.startsWith("com.sun")&&
					 !name.startsWith("sessionManager")&&
					 !name.startsWith("CharacterEncodingFilter")&&
					 !name.startsWith("org.eclipse")&&
					 !name.startsWith("org.apache")&&
					 !name.startsWith("javax.servlet")){
				 		map.put(name,httpServletRequest.getAttribute(name));
			 }
		 }
		 return map;
	}
	
	public <T>T getObject(Class<T> type)throws Exception{
		
		Object id = null;
		
		ParamEntityAnnotation paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation.class);
		if(paramEntityAnnotation2.type().equals(ParamAnnotation.REQUEST_TYPE)){
			id = rawGetByRequest(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation.SESSION_TYPE)){
			id = rawGetBySession(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation.CONTEXT_TYPE)){
			id = rawGetByContext(idRef(type));
		}
		

		
		if(id!=null){
			Object obj = getObject(type, id+"");

			return (T)obj;
		}
		
		return null;
		
		
	}

	public Object getObject(Class<?> type , String id)throws Exception{
		
		Object obj = type.newInstance();
		Field[] fields = type.getDeclaredFields();
		
		boolean flag = false;
		for(Field field:fields){
			field.setAccessible(true);
			
			if(field.getAnnotation(IdAnnotation.class)!=null){
				field.set(obj, id);
			}
			
			if(field.getAnnotation(ParamAnnotation.class)!=null){
				String name = name(field);
				Object value = getValue(type, id, name);
				field.set(obj, value);
				if(!CommonUtil.isEmpty(value)){
					flag = true;
				}else{
					
				}
			}
		}
		
		if(flag){
			return obj;
		}else{
			return null;
		}
		
	}
	
	
	public void remove(Class<?>type)throws Exception{
		Object id = null;
		
		ParamEntityAnnotation paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation.class);
		if(paramEntityAnnotation2.type().equals(ParamAnnotation.REQUEST_TYPE)){
			id = rawGetByRequest(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation.SESSION_TYPE)){
			id = rawGetBySession(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation.CONTEXT_TYPE)){
			id = rawGetByContext(idRef(type));
		}
		
		remove(type,id+"");
		
		rawRemoveKeyByContext(idRef(type));
		rawRemoveKeyByRequest(idRef(type));
		rawRemoveKeyBySession(idRef(type));
	}
	
	public void remove(Class<?> type , String id)throws Exception{
		Field[] fields = type.getDeclaredFields();
		
		for(Field field:fields){
			if(field.getAnnotation(IdAnnotation.class)!=null||field.getAnnotation(ParamAnnotation.class)!=null){
				String name = name(field);
				String key = key(type, id, name);
				removeValueByKey(key);
			}
		}
	}
	
	
	public void removeValueByKey(String key){
		rawRemoveKeyByContext(key);
		rawRemoveKeyByRequest(key);
		rawRemoveKeyBySession(key);
	}
	
	public Object getValueByKey(String key){
		Object value = null;
		value = rawGetByRequest(key);

		if(value==null){
			value = rawGetBySession(key);

		}
		if(value==null){
			value = rawGetByContext(key);
		}
		return value;
	}
	
	public Object getValue(Class<?> type , String id , String name)throws Exception{
		String key = key(type, id, name);
		
		return getValueByKey(key);
	}
	
	
	
	private String getSessionId(Class<?> clazz){
		ParamEntityAnnotation paramEntityAnnotation2 = clazz.getAnnotation(ParamEntityAnnotation.class);
		String type = paramEntityAnnotation2.type();
		if(type.equals(ParamAnnotation.REQUEST_TYPE)){
			return httpServletRequest.getRequestedSessionId();
		}else if(type.equals(ParamAnnotation.SESSION_TYPE)){
			return httpServletRequest.getSession().getId();
		}else if(type.equals(ParamAnnotation.CONTEXT_TYPE)){
			return contextId;
		}
		return null;
	}
	
	public void commitUpdate()throws Exception{
		List<Object> updateCache = (List<Object>)httpServletRequest.getAttribute("13738139702updateCache");
		updateExecuter.update(updateCache);
	}
	
	public <T>T findOne(Class<T> type , String id)throws Exception{
		Object obj = getObject(type,id);
		if(obj!=null){
			System.out.println("。。。。。。。。。。。。。从缓存获取");
		}else{
			System.out.println("。。。。。。。。。。。。。。。从数据库获取");
			obj = updateExecuter.findOne(type, id);
		}
		
		if(obj!=null){
			save(obj);
		}
		return (T)obj;
	}
	
	public void update(Object obj)throws Exception{
		List<Object> updateCache = null;
		if(httpServletRequest.getAttribute("13738139702updateCache")!=null){
			updateCache = (List<Object>)httpServletRequest.getAttribute("13738139702updateCache");
		}else{
			updateCache = new ArrayList<>();
			httpServletRequest.setAttribute("13738139702updateCache", updateCache);
		}
		 
		for(Object cacheObj:updateCache){
			Class<?> cacheClass = cacheObj.getClass();
			if(cacheClass.equals(obj.getClass())){
				Field idField = cacheClass.getDeclaredField("id");
				idField.setAccessible(true);
				Object cacheIdValue = idField.get(cacheObj);
				Object objIdValue = idField.get(obj);
				if(cacheIdValue.equals(objIdValue)){
					updateCache.remove(cacheObj);
					break;
				}
			}
			
		}
		
		save(obj);

		updateCache.add(obj);
	}
	
	public void save(Object obj)throws Exception{
		
		if(obj==null){
			return;
		}
		Class<?> clazz = obj.getClass();
	//	remove(clazz);
		Object idValue = null;
		List<Param> params = getParams(obj);
		ParamEntityAnnotation paramEntityAnnotation2 = clazz.getAnnotation(ParamEntityAnnotation.class);
		
		/*if(paramEntityAnnotation2.type()==ParamAnnotation.SESSION_TYPE){
			
		}*/
		
		for(Param param:params){
			String type = param.getType();
			if(param.isId()){
				idValue = param.getValue();
			}
			

			if(type.equals(ParamAnnotation.REQUEST_TYPE)){

				rawSaveToRequest(param.getName(), param.getValue());
			}else if(type.equals(ParamAnnotation.SESSION_TYPE)){

				rawSaveToSession(param.getName(), param.getValue());
			}else if(type.equals(ParamAnnotation.CONTEXT_TYPE)){

				rawSaveToContext(param.getName(), param.getValue());
			}
		}
		
		
		
		if(paramEntityAnnotation2.type().equals(ParamEntityAnnotation.REQUEST_TYPE)){
			rawSaveToRequest(idRef(clazz), idValue);
		}else if(paramEntityAnnotation2.type().equals(ParamEntityAnnotation.SESSION_TYPE)){
			rawSaveToSession(idRef(clazz), idValue);
		}else if(paramEntityAnnotation2.type().equals(ParamEntityAnnotation.CONTEXT_TYPE)){
			rawSaveToContext(idRef(clazz), idValue);
		}
		
	}
	
	
	private String idRef(Class<?> type)throws Exception{
		String className = type.getName();
		String sessionId = getSessionId(type);
		return className + "_" + sessionId;
	}
	
	private String entityName(Class<?> type)throws Exception{
		String entityName = null;
		ParamEntityAnnotation paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation.class);
		if(paramEntityAnnotation2.name().equals("")){
			entityName = type.getName();
		}else{
			entityName = paramEntityAnnotation2.name();
		}
		return entityName;
	}
	
	private String name(Field field){
		field.setAccessible(true);
		ParamAnnotation paramAnnotation2 = field.getAnnotation(ParamAnnotation.class);
		IdAnnotation idAnnotation2 = field.getAnnotation(IdAnnotation.class);
		String name = null;
		
		if(idAnnotation2!=null){
			if(idAnnotation2.name().equals("")){
				name = field.getName();
			}else{
				name = paramAnnotation2.name();
			}
			return name;
		}
		if(paramAnnotation2.name().equals("")){
			name = field.getName();
		}else{
			name = paramAnnotation2.name();
		}
		return name;
	}
	
	
	private String key(String entry,String id , String name)throws Exception{
		String key = entry+"_"+id+"_"+name;
		return key;
	}
	
	private String key(Class<?> type , String id , String name)throws Exception{
		
		String entryName = entityName(type);
		Field field = type.getDeclaredField(name);
		name = name(field);
		return key(entryName, id, name);
		
	}
	
	private List<Param> getParams(String entryName,Map<String, Object> map)throws Exception{
		List<Param> params = new ArrayList<>();
		
		String idValue = UUID.randomUUID().toString();
		String idName = "id";
		String idKey = key(entryName, idValue, idName);
		Param idParam = new Param();
		idParam.setEntityName(entryName);

		idParam.setId(idValue);
		idParam.setIsId(true);
		idParam.setIsMap(false);
		idParam.setName(idKey);
		idParam.setType(ParamEntityAnnotation.REQUEST_TYPE);
		idParam.setValue(idValue);
		
		for(Entry<String, Object> entry:map.entrySet()){
			Param param = new Param();
			
			String paramKey = key(entryName, idValue, entry.getKey());
			param.setEntityName(entryName);
			
			param.setId(idValue);
			param.setIsId(false);
			param.setIsMap(false);
			param.setName(paramKey);
			param.setType(ParamEntityAnnotation.REQUEST_TYPE);
			param.setValue(entry.getValue());
			params.add(param);
		}
		
		return params;
		
	}
	
	private List<Param> getParams(Object obj)throws Exception{
		Class<?> type = obj.getClass();
		Field[] fields = type.getDeclaredFields();
		
		List<Param> params = new ArrayList<>();
		Field idField = null;
		List<Field> paramFields = new ArrayList<>();
		for(Field field:fields){
			IdAnnotation idAnnotation2 = field.getAnnotation(IdAnnotation.class);
			if(idAnnotation2!=null){
				idField = field;
			}
			
			ParamAnnotation paramAnnotation2 = field.getAnnotation(ParamAnnotation.class);
			if(paramAnnotation2!=null){
				paramFields.add(field);
			}
		}
		
		String idName = null;
		String idValue = null;
		if(idField!=null){
			idName = name(idField);
			if(idField.get(obj)!=null){
				idValue = idField.get(obj)+"";
			}
			
		}else{
			idName = "id";
			idValue = UUID.randomUUID().toString();
		}
		
		if(idValue==null){
			idValue = UUID.randomUUID().toString();
		}
		String idKey = key(type, idValue, idName);
		ParamEntityAnnotation paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation.class);
		
		Param idParam = new Param();
		String entityName = entityName(type);
		idParam.setEntityName(entityName);
		idParam.setField(idField);
		idParam.setId(idValue);
		idParam.setIsId(true);
		idParam.setIsMap(false);
		idParam.setName(idKey);
		idParam.setType(paramEntityAnnotation2.type());
		idParam.setValue(idValue);
		
		params.add(idParam);
		for(Field paramField:paramFields){
			
			paramField.setAccessible(true);
			ParamAnnotation paramAnnotation2 = paramField.getAnnotation(ParamAnnotation.class);
			
			String paramName = name(paramField);
			String paramType = paramAnnotation2.type();
			
			String paramKey = key(type, idValue, paramName);
			Object paramValue = paramField.get(obj);

			Param param = new Param();
			param.setEntityName(entityName);
			param.setField(paramField);
			param.setId(idValue);
			param.setIsId(false);
			param.setIsMap(false);
			param.setName(paramKey);
			param.setType(paramType);
			param.setValue(paramValue);
			params.add(param);
		}
		
		return params;
	}
}
