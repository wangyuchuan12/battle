package com.wyc.common.api;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.DoWxShareFilter;
import com.wyc.common.session.SessionManager;
@Controller
@RequestMapping(value="/api/common/share")
public class ShareApi {
	@RequestMapping("share_app_message")
	@Transactional
	@HandlerAnnotation(hanlerFilter=DoWxShareFilter.class)
	public Object shareAppMessage(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		if(sessionManager.isReturn()){
			return sessionManager.getReturnValue();
		}else{
			return sessionManager.getObject(ResultVo.class);
		}
		
	}
}
