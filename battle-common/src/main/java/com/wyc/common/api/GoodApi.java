package com.wyc.common.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyc.common.domain.Good;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.GoodService;
import com.wyc.common.util.CommonUtil;

@Controller
@RequestMapping(value="/api/common/good")
public class GoodApi {
	
	@Autowired
	private GoodService goodService;

	@Transactional
	@ResponseBody
	@RequestMapping(value="list")
	public ResultVo list(HttpServletRequest httpServletRequest){
		String type = httpServletRequest.getParameter("type");
		List<Good> goods = null;
		if(!CommonUtil.isEmpty(type)){
			Integer typeInt = Integer.parseInt(type);
			goods = goodService.findAllByStatusAndTypeOrderByIndexAsc(Good.STATUS_ON_SHELVES,typeInt);
		}else {
			goods = goodService.findAllByStatusOrderByIndexAsc(Good.STATUS_ON_SHELVES);
		}
		
		
		
		List<Map<String, Object>> resonseData = new ArrayList<>();
		
		for(Good good:goods){
			Map<String, Object> map = new HashMap<>();
			map.put("id", good.getId());
			map.put("imgUrl", good.getImgUrl());
			map.put("type", good.getType());
			map.put("costType", good.getCostType());
			
			if(good.getCostType()==Good.AMOUNT_COST_TYPE){
				map.put("cost", good.getCostMoney());
			}else if(good.getCostType()==Good.BEAN_COST_TYPE){
				map.put("cost", good.getCostBean());
			}else if(good.getCostType()==Good.MASONRY_COST_TYPE){
				map.put("cost", good.getCostMasonry());
			}
			
			
			if(good.getType()==Good.BEAN_TYPE){
				map.put("num", good.getBeanNum());
			}else if(good.getType()==Good.MASONRY_TYPE){
				map.put("num", good.getMasonryNum());
			}else if(good.getType()==Good.LOVE_TYPE){
				map.put("num", good.getLoveNum());
			}
			resonseData.add(map);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(resonseData);
		
		return resultVo;
	}
}
