package com.wyc.common.config;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.annotation.ParamClassAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.filter.manager.FilterEntrySession;
import com.wyc.common.session.SessionManager;

@Aspect
@Component
public class ManagerInterceptConfig {
	
	final static Logger logger = LoggerFactory.getLogger(ManagerInterceptConfig.class);
	@Autowired
    private AutowireCapableBeanFactory factory;
	
	class FilterObject{
		private Filter filter;
		private Object param;
		private Object Response;
		public Filter getFilter() {
			return filter;
		}
		public void setFilter(Filter filter) {
			this.filter = filter;
		}
		public Object getParam() {
			return param;
		}
		public void setParam(Object param) {
			this.param = param;
		}
		public Object getResponse() {
			return Response;
		}
		public void setResponse(Object response) {
			Response = response;
		}
	}
	
	@Around(value="execution (* com.wyc.pay.api.*.*(..))")
	public Object payApi(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		return aroundAction(proceedingJoinPoint);
	}
	
	@Around(value="execution (* com.wyc.common.api.*.*(..))")
	public Object commonApi(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		return aroundAction(proceedingJoinPoint);
	}
	
	@Around(value="execution (* com.wyc.draw.web.controller.*.*(..))")
	public Object aroundController(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		return aroundAction(proceedingJoinPoint);
	}
	
	@Around(value="execution (* com.wyc.draw.web.api.*.*(..))")
	public Object aroundApi(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		return aroundAction(proceedingJoinPoint);
	}
	
	@Around(value="execution (* com.wyc.controller.*.*(..))")
	public Object gameController(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		return aroundAction(proceedingJoinPoint);
	}
	
	 
	
	public Object aroundAction(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		 Method method = getControllerMethod(proceedingJoinPoint);
		 Object returnValue = null;
		 HttpServletRequest httpServletRequest = (HttpServletRequest)proceedingJoinPoint.getArgs()[0];		 
		 HandlerAnnotation handlerAnnotation2 = method.getAnnotation(HandlerAnnotation.class);
		 if(handlerAnnotation2!=null){
			 ParamClassAnnotation paramAnnotation2 = method.getAnnotation(ParamClassAnnotation.class);
			 Class<?> paramType = null;
			 if(paramAnnotation2!=null){
				 paramType = paramAnnotation2.value();
			 }else{
				 paramType = Map.class;
			 }
			 
			 Class<? extends Filter> filterClass = handlerAnnotation2.hanlerFilter();
			 
			 SessionManager filterManager = SessionManager.getFilterManager(httpServletRequest, paramType);
			 factory.autowireBean(filterManager);
			 
			
			 
			
			 if(proceedingJoinPoint.getArgs().length>1){
				 HttpServletResponse httpServletResponse = (HttpServletResponse)proceedingJoinPoint.getArgs()[1];
				 
				 filterManager.setHttpServletResponse(httpServletResponse);
			 }
			 
			FilterEntrySession filterEntrySession = new FilterEntrySession(filterManager, filterClass, factory);
			
			filterEntrySession.pointInit();
			
			filterEntrySession.exectePreHandler();
			
			filterEntrySession.pointInit();
			filterEntrySession.pointLeafFilterStep();
			
			filterEntrySession.executeHandler();
			 
			 try{

				 if(filterManager.isEnd()){
					 Map<String, Object> attributes = filterManager.getReturnAttribute();
					 if(attributes!=null){
						 for(Entry<String, Object> entry:attributes.entrySet()){
							 String key = entry.getKey();
							 Object value = entry.getValue();
							 httpServletRequest.setAttribute(key, value);
						 }
					 }
					 return filterManager.getReturnValue();
				 }
				 
				 returnValue = proceedingJoinPoint.proceed();
				 
				 filterEntrySession.pointInit();
				 filterEntrySession.executeAfterHandler();
				 
				 /**
				  * 现在先不要后置处理，等以后框架成熟再加此功能，目前bug还很多
				  */
				 ResultVo resultVo = (ResultVo)filterManager.getObject(ResultVo.class);
				 
				 if(filterManager.getReturnValue()!=null){
					 ResultVo resultVo2 = (ResultVo)filterManager.getReturnValue();
				 }
				 
				 if(resultVo==null){
					 resultVo = (ResultVo)filterManager.getReturnValue();
				 }
				 
				 filterManager.commitUpdate();
				 if(resultVo!=null&&resultVo.isSuccess()==false){
					 Transactional transactional = method.getAnnotation(Transactional.class);
					 if(transactional!=null){
						 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					 }
					 
				 }
				 return returnValue;
			 }catch(Exception e){
				 logger.error("has an error:{}",e);
				 Transactional transactional = method.getAnnotation(Transactional.class);
				 if(transactional!=null){
					 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				 }
				
				 
			 }
		 }else{
			 httpServletRequest = (HttpServletRequest)proceedingJoinPoint.getArgs()[0];
			 SessionManager filterManager = SessionManager.getFilterManager(httpServletRequest, Map.class);
			 factory.autowireBean(filterManager);
			 try{
				 returnValue = proceedingJoinPoint.proceed();
				 filterManager.commitUpdate();
			 }catch(Exception e){
				 logger.error("has an error:{}",e);
				 Transactional transactional = method.getAnnotation(Transactional.class);
				 if(transactional!=null){
					 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				 }
				 
			 }
		 }
		 return returnValue;
	 }
	 
	 //获取控制器方法
	 private Method getControllerMethod(ProceedingJoinPoint proceedingJoinPoint){
		 Object target = proceedingJoinPoint.getTarget();
		 
		 for(Method oneMethod:target.getClass().getMethods()){
	            if(oneMethod.getName().equals(proceedingJoinPoint.getSignature().getName())){
	            	oneMethod.setAccessible(true);
	                return oneMethod;
	            }
		 }
		 return null;
	 }
}
