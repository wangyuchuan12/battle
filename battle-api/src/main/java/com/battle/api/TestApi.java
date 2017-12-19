package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping(value="/api/test")
public class TestApi {

	@ResponseBody
	@RequestMapping(value="test")
	public Object test(HttpServletRequest httpServletRequest)throws Exception{
		
		
		System.out.println("等待");
		Thread.sleep(1000);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData("111223");
		System.out.println("结束");
		return resultVo;
	}
}
