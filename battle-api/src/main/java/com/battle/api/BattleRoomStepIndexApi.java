package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomStepIndex;
import com.battle.domain.BattleStepIndexModel;
import com.battle.domain.BattleStepModel;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleRoomStepIndexService;
import com.battle.service.BattleStepIndexModelService;
import com.battle.service.BattleStepModelService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

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
	
	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="receive")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo receive(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		List<BattleMemberPaperAnswer> battleMemberPaperAnswers = battleMemberPaperAnswerService.findAllByBattlePeriodMemberIdAndIsReceive(battlePeriodMember.getId(),0);
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		for(BattleMemberPaperAnswer battleMemberPaperAnswer:battleMemberPaperAnswers){
			
			battleMemberPaperAnswer.setIsReceive(1);
			
			battleMemberPaperAnswerService.update(battleMemberPaperAnswer);
			
			Integer startIndex = battleMemberPaperAnswer.getStartIndex();
			
			Integer endIndex = battleMemberPaperAnswer.getEndIndex();
			
			
			
			System.out.println(".....................startIndex:"+startIndex+",endIndex:"+endIndex);
			
			List<BattleRoomStepIndex> battleRoomStepIndexs =  battleRoomStepIndexService.findAllByRoomIdAndStepIndexGreaterThanAndStepIndexLessThanEqualOrderByStepIndexAsc(battlePeriodMember.getRoomId(),startIndex,endIndex);
			
			
			
			Long wisdomCount = account.getWisdomCount();
			if(wisdomCount==null){
				wisdomCount = 0L;
			}
			
			for(BattleRoomStepIndex battleRoomStepIndex:battleRoomStepIndexs){
				Integer beanNum = battleRoomStepIndex.getBeanNum();
				if(beanNum==null){
					beanNum  = 0;
				}
				wisdomCount = wisdomCount + beanNum;
				account.setWisdomCount(wisdomCount);
				
			}
		}
		
		accountService.update(account);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	@RequestMapping(value="list")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo list(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String roomId = httpServletRequest.getParameter("roomId");
		
		if(CommonUtil.isEmpty(roomId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		if(battleRoom.getIsInit()==null){
			battleRoom.setIsInit(0);
		}
		
		List<BattleRoomStepIndex> battleRoomStepIndexs = new ArrayList<>();
		if(battleRoom.getIsInit().intValue()==1){
			
			System.out.println("..................进这里");
			battleRoomStepIndexs = battleRoomStepIndexService.findAllByRoomIdOrderByStepIndexAsc(roomId);
			
			System.out.println(".............roomId:"+roomId);
			
			System.out.println(".............battleRoomStepIndexs:"+battleRoomStepIndexs);
			
			battleRoomService.update(battleRoom);
		}else{
			System.out.println("..................进这里2");
			battleRoom.setIsInit(1);
			battleRoomService.update(battleRoom);
			String code = httpServletRequest.getParameter("code");
			BattleStepModel battleStepModel = null;
			if(CommonUtil.isEmpty(code)){
				System.out.println("..................进这里3");
				Pageable pageable = new PageRequest(0,1);
				Page<BattleStepModel> battleStepModelPage =  battleStepModelService.findAll(pageable);
				List<BattleStepModel> battleStepModels = battleStepModelPage.getContent();
				if(battleStepModels!=null&&battleStepModels.size()>0){
					battleStepModel = battleStepModels.get(0);
				}
			}else{
				System.out.println("..................进这里4");
				battleStepModel = battleStepModelService.findOneByCode(code);
			}
			
			
			System.out.println("...................battleStepModel:"+battleStepModel);
			if(battleStepModel!=null){
				
				System.out.println("battleStepModel.id:"+battleStepModel.getId());
				List<BattleStepIndexModel> battleStepIndexModels = battleStepIndexModelService.findAllByModelId(battleStepModel.getId());
				
				System.out.println("battleStepIndexModels:"+battleStepIndexModels);
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
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				return resultVo;
			}
		}
		
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		List<Map<String, Object>> responseIndexes = new ArrayList<>();
		
		if(battleRoomStepIndexs!=null&&battleRoomStepIndexs.size()>0){
			
			for(BattleRoomStepIndex battleRoomStepIndex:battleRoomStepIndexs){
				if(battleRoomStepIndex.getStepIndex()>battlePeriodMember.getProcess()){
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
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseIndexes);
		
		return resultVo;
	}
}
