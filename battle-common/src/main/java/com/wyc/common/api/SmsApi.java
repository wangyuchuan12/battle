package com.wyc.common.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.other.service.SubmailMsgService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;

@Controller
@RequestMapping(value="/api/common/sms")
public class SmsApi {

	@Autowired
	private SubmailMsgService submailMsgService;
	@ResponseBody
	@RequestMapping(value="authCode")
	public Object authCode(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String phonenum = httpServletRequest.getParameter("phonenumber");
		String project = httpServletRequest.getParameter("project");
		try{
			String code = submailMsgService.sendMsg(phonenum,project);
			
			if(!CommonUtil.isEmpty(code)){
				Map<String, Object> data = new HashMap<>();
				data.put("code", code);
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
			//	resultVo.setData(data);
				
				sessionManager.rawSaveToSession("auth_msg_code_"+phonenum+"_"+project, code);
				
				return resultVo;
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				return resultVo;
			}
		}catch(Exception e){
			e.printStackTrace();
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("发生错误");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
	}
}
