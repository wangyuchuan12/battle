package com.battle.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleNotice;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleNoticeService;
import com.battle.service.BattleRoomService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battleNotice/")
public class BattleNoticeApi {
	
	@Autowired
	private BattleNoticeService battleNoticeService;
	
	@Autowired
	private BattleRoomService battleRoomService;

	
	@RequestMapping(value="receiveNotice")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo receiveNotice(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);

		String type = httpServletRequest.getParameter("type");
		
		Integer typeInt = Integer.parseInt(type);
		Sort sort = new Sort(Direction.ASC,"createAt");
		Pageable pageable = new PageRequest(0,10,sort);
		
		String roomId = httpServletRequest.getParameter("roomId");
	
		List<BattleNotice> battleNotices = battleNoticeService.findAllByToUserAndTypeAndRoomIdAndIsReadGroupByMemberId(userInfo.getId(),typeInt,roomId,0,pageable);
		
		
		int index = 0;
		while(true){
			if(battleNotices!=null&&battleNotices.size()>0){
				break;
			}else{
				battleNotices = battleNoticeService.findAllByToUserAndTypeAndRoomIdAndIsReadGroupByMemberId(userInfo.getId(),typeInt,roomId,0,pageable);
				System.out.println(".........battleNotices1:"+battleNotices+",type:"+type);
				if(battleNotices==null||battleNotices.size()==0){
					try{
						Thread.sleep(5000);
					}catch(Exception e){
						
					}
				}else{
					break;
				}
			}
			index++;
			
			if(index>=5){
				break;
			}
		}
		
		System.out.println(".........battleNotices2:"+battleNotices+",type:"+type);
		
		
		if(battleNotices!=null&&battleNotices.size()>0){
			for(BattleNotice battleNotice:battleNotices){
				battleNotice.setIsRead(1);
				
				battleNoticeService.update(battleNotice);
			}
		}else{
	
		}
		
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleNotices);
		
		return resultVo;
		
		
	}
}
