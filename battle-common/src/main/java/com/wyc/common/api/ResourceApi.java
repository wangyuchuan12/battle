package com.wyc.common.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.MyResource;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.AddResourceFilter;
import com.wyc.common.session.SessionManager;

@Controller
@RequestMapping(value="/api/common/resource")
public class ResourceApi {

	@HandlerAnnotation(hanlerFilter=AddResourceFilter.class)
	@ResponseBody
	@RequestMapping(value="upload")
	public ResultVo uploadFile(MultipartHttpServletRequest multipartHttpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(multipartHttpServletRequest);
		MyResource myResource = (MyResource)sessionManager.getObject(MyResource.class);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setMsg("上传文件成功");
		resultVo.setData(myResource);
		return resultVo;
	}
}
