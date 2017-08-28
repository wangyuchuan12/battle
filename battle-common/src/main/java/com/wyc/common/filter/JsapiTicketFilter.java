package com.wyc.common.filter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.wyc.common.filter.service.WxConfigService;
import com.wyc.common.session.SessionManager;




public class JsapiTicketFilter extends Filter{
	@Autowired
	private WxConfigService wxConfigService;

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		return wxConfigService.getJsapiTicketBean(sessionManager);
	}

	
	@Override
	public List<Class<? extends Filter>> dependClasses() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}
}
