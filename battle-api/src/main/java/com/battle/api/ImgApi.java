package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.ImgService;
import com.wyc.common.domain.MyResource;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping(value="/api/img/")
public class ImgApi {

	@Autowired
	private ImgService imgService;
	
	@RequestMapping(value="roomImg")
	@ResponseBody
	public ResultVo roomImg(HttpServletRequest httpServletRequest)throws Exception{
		
		String roomId = httpServletRequest.getParameter("roomId");
		MyResource myResource = imgService.createRoomImg(roomId);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(myResource);
		
		return resultVo;
	}
}
