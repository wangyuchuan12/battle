package com.battle.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleNotice;
import com.battle.service.BattleNoticeService;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping(value="/api/battleNotice/")
public class BattleNoticeApi {
	
	@Autowired
	private BattleNoticeService battleNoticeService;
	
	@RequestMapping(value="receiveMemberNotice")
	@ResponseBody
	@Transactional
	public ResultVo receiveNotice(HttpServletRequest httpServletRequest){
		
		String type = httpServletRequest.getParameter("type");
		
		Integer typeInt = Integer.parseInt(type);
		Sort sort = new Sort(Direction.ASC,"createAt");
		Pageable pageable = new PageRequest(0,1,sort);
		
		String roomId = httpServletRequest.getParameter("roomId");
		Page<BattleNotice> battleNoticePage = battleNoticeService.findAllByTypeAndRoomIdAndIsRead(typeInt,roomId,0,pageable);
	
		List<BattleNotice> battleNotices = battleNoticePage.getContent();
		
		BattleNotice battleNotice = null;
		
		if(battleNotices!=null&&battleNotices.size()>0){
			battleNotice = battleNotices.get(0);
		}
		
		
		while(true){
			if(battleNotice!=null){
				break;
			}else{
				battleNoticePage = battleNoticeService.findAllByTypeAndRoomIdAndIsRead(typeInt,roomId,0,pageable);
				battleNotices = battleNoticePage.getContent();
				if(battleNotices==null||battleNotices.size()==0){
					try{
						Thread.sleep(1000);
					}catch(Exception e){
						
					}
				}else{
					battleNotice = battleNotices.get(0);
				}
			}
		}
		
		if(battleNotice==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			return resultVo;
		}else{
			
			battleNotice.setIsRead(1);
			battleNoticeService.update(battleNotice);
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(true);
			
			resultVo.setData(battleNotice);
			
			return resultVo;
		}
		
		
	}
}
