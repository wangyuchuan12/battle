package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomStepIndex;
import com.battle.domain.BattleStepIndexModel;
import com.battle.domain.BattleStepModel;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleRoomStepIndexService;
import com.battle.service.BattleStepIndexModelService;
import com.battle.service.BattleStepModelService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.util.CommonUtil;

@Controller
@RequestMapping(value="/api/battleRoomStepIndex/")
public class BattleRoomStepIndexApi {

	@Autowired
	private BattleRoomStepIndexService battleRoomStepIndexService;
	
	@Autowired
	private BattleStepIndexModelService battleStepIndexModelService;
	
	@Autowired
	private BattleStepModelService battleStepModelService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	@RequestMapping(value="list")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo list(HttpServletRequest httpServletRequest){
		String roomId = httpServletRequest.getParameter("roomId");
		
		if(CommonUtil.isEmpty(roomId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		List<BattleRoomStepIndex> battleRoomStepIndexs = new ArrayList<>();
		if(battleRoom.getIsInit()==0){
			battleRoomStepIndexs = battleRoomStepIndexService.findAllByRoomIdOrderByStepIndexAsc(roomId);
		}else{
			battleRoom.setIsInit(1);
			battleRoomService.update(battleRoom);
			String code = httpServletRequest.getParameter("code");
			BattleStepModel battleStepModel = null;
			if(CommonUtil.isEmpty(code)){
				Pageable pageable = new PageRequest(0,1);
				List<BattleStepModel> battleStepModels =  battleStepModelService.findAll(pageable);
				if(battleStepModels!=null&&battleStepModels.size()>0){
					battleStepModel = battleStepModels.get(0);
				}
			}else{
				battleStepModel = battleStepModelService.findOneByCode(code);
			}
			
			if(battleStepModel!=null){
				
				List<BattleStepIndexModel> battleStepIndexModels = battleStepIndexModelService.findAllByModelId(battleStepModel.getId());
				
				for(BattleStepIndexModel battleStepIndexModel:battleStepIndexModels){
					BattleRoomStepIndex battleRoomStepIndex = new BattleRoomStepIndex();
					
					battleRoomStepIndex.setBeanNum(battleStepIndexModel.getBeanNum());
					battleRoomStepIndex.setImgUrl(battleStepIndexModel.getImgUrl());
					battleRoomStepIndex.setLoveNum(battleStepIndexModel.getLoveNum());
					battleRoomStepIndex.setRewardType(battleStepIndexModel.getRewardType());
					battleRoomStepIndex.setRoomId(roomId);
					battleRoomStepIndex.setStepIndex(battleStepIndexModel.getStepIndex());
					
					battleRoomStepIndexService.add(battleRoomStepIndex);
					
					battleRoomStepIndexs.add(battleRoomStepIndex);
				}
				
			
			}
		}
		
		List<Map<String, Object>> responseIndexes = new ArrayList<>();
		
		if(battleRoomStepIndexs!=null&&battleRoomStepIndexs.size()>0){
			
			for(BattleRoomStepIndex battleRoomStepIndex:battleRoomStepIndexs){
				Map<String, Object> responseIndex = new HashMap<>();
				responseIndex.put("id", battleRoomStepIndex.getId());
				responseIndex.put("beanNum", battleRoomStepIndex.getBeanNum());
				responseIndex.put("loveNum", battleRoomStepIndex.getLoveNum());
				responseIndex.put("roomId", battleRoomStepIndex.getRoomId());
				responseIndex.put("rewardType", battleRoomStepIndex.getRewardType());
				responseIndex.put("stepIndex", battleRoomStepIndex.getStepIndex());
				responseIndexes.add(responseIndex);
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseIndexes);
		
		return resultVo;
	}
}
