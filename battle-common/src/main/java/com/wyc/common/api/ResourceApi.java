package com.wyc.common.api;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.MyResource;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.AddResourceFilter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.smart.service.UploadToQNService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.service.QrcodeService;

@Controller
@RequestMapping(value="/api/common/resource")
public class ResourceApi {
	
	@Autowired
	private QrcodeService qrcodeService;
	
	@Autowired
	private UploadToQNService uploadToQNService;
	@HandlerAnnotation(hanlerFilter=AddResourceFilter.class)
	@ResponseBody
	@RequestMapping(value="upload")
	public ResultVo uploadFile(MultipartHttpServletRequest multipartHttpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(multipartHttpServletRequest);
		MyResource myResource = (MyResource)sessionManager.getObject(MyResource.class);
		
		if(myResource!=null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setMsg("上传文件成功");
			resultVo.setData(myResource);
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("上传文件失败");
			return resultVo;
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="createwxaqrcode")
	public ResultVo createwxaqrcode(HttpServletRequest httpServletRequest)throws Exception{
		String path = httpServletRequest.getParameter("path");
		MyResource myResource = qrcodeService.createWxaqrcode(path);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(myResource);
		
		return resultVo;
	}
	
	@ResponseBody
	@RequestMapping(value="shareImg")
	public ResultVo shareImg(HttpServletRequest httpServletRequest)throws Exception{
		String path = httpServletRequest.getParameter("path");
		MyResource myResource = qrcodeService.createWxaqrcode(path);
		
		String systemPath = "/root/iconde"+UUID.randomUUID()+".jpg";
		
		CommonUtil.overlapHttpImage("http://7xlw44.com1.z0.glb.clouddn.com/bb.jpeg", myResource.getUrl(), systemPath);
		
		MyResource overlapImgResource = new MyResource();
		
		overlapImgResource.setId(UUID.randomUUID().toString());;
		
		overlapImgResource.setSystemUrl(systemPath);
		
		uploadToQNService.syncResource(overlapImgResource);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(overlapImgResource);
		
		return resultVo;
	}
}
