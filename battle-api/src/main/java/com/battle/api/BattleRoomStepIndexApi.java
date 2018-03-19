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
import com.battle.domain.BattleStepDomain;
import com.battle.domain.BattleStepIndexModel;
import com.battle.domain.BattleStepModel;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleRoomStepIndexService;
import com.battle.service.BattleStepDomainService;
import com.battle.service.BattleStepIndexModelService;
import com.battle.service.BattleStepModelService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.WxUserInfoService;
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
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattleStepDomainService battleStepDomainService;
	
	@Autowired
	private WxUserInfoService userInfoService;
	
	
	@RequestMapping(value="holdDomain")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo holdDomain(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		BattleStepDomain battleStepDomain = battleStepDomainService.findOneByRoomIdAndStepIndex(battlePeriodMember.getRoomId(),battlePeriodMember.getProcess());
		
		battleStepDomain.setFlagId(battlePeriodMember.getFlagId());
		
		battleStepDomain.setFlagImg(battlePeriodMember.getFlagImg());
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		
		Long beanNum = account.getWisdomCount();
		Integer costBean = battleStepDomain.getCostBean();
		if(beanNum==null){
			beanNum = 0L;
		}
		
		if(costBean==null){
			costBean = 0;
		}
		
		beanNum = beanNum - costBean;
		
		if(beanNum<0){
			beanNum = 0L;
		}
		account.setWisdomCount(beanNum);
		
		accountService.update(account);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
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
		
		
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		Map<String, Object> data = new HashMap<>();
		
		BattleStepDomain battleStepDomain = battleStepDomainService.findOneByRoomIdAndStepIndex(battlePeriodMember.getRoomId(),battlePeriodMember.getProcess());
		if(battleStepDomain!=null){
			
			BattlePeriodMember domainBattlePeriodMember = battlePeriodMemberService.findOne(battleStepDomain.getMemberId());
			Map<String, Object> domain = new HashMap<>();
			domain.put("costBean", battleStepDomain.getCostBean());
			domain.put("memberId", battleStepDomain.getMemberId());
			domain.put("stepIndex", battleStepDomain.getStepIndex());
			domain.put("headImg", domainBattlePeriodMember.getHeadImg());
			data.put("domain", domain);
			
			Long beanNum = account.getWisdomCount();
			if(beanNum==null){
				beanNum = 0L;
			}
			
			Integer costBean = battleStepDomain.getCostBean();
			if(costBean==null){
				costBean = 0;
			}
			if(beanNum>costBean){
				beanNum = 0L;
			}else{
				beanNum = beanNum - costBean;
			}
			
			account.setWisdomCount(beanNum);
			
			UserInfo domainUserInfo = userInfoService.findOne(domainBattlePeriodMember.getUserId());
			
			Account domainAccount = accountService.fineOneSync(domainUserInfo.getAccountId());
			
			Long domainbBeanNum = domainAccount.getWisdomCount();
			domainbBeanNum = domainbBeanNum + costBean;
			domainAccount.setWisdomCount(domainbBeanNum);
			
			accountService.update(domainAccount);
		}
		
		accountService.update(account);
		
		resultVo.setData(data);
	
		
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
			
			battleRoomStepIndexs = battleRoomStepIndexService.findAllByRoomIdOrderByStepIndexAsc(roomId);
			
			battleRoomService.update(battleRoom);
		}else{
			battleRoom.setIsInit(1);
			battleRoomService.update(battleRoom);
			String code = httpServletRequest.getParameter("code");
			BattleStepModel battleStepModel = null;
			if(CommonUtil.isEmpty(code)){
				Pageable pageable = new PageRequest(0,1);
				Page<BattleStepModel> battleStepModelPage =  battleStepModelService.findAll(pageable);
				List<BattleStepModel> battleStepModels = battleStepModelPage.getContent();
				if(battleStepModels!=null&&battleStepModels.size()>0){
					battleStepModel = battleStepModels.get(0);
				}
			}else{
				battleStepModel = battleStepModelService.findOneByCode(code);
			}
			
			if(battleStepModel!=null){
				
				System.out.println("battleStepModel.id:"+battleStepModel.getId());
				List<BattleStepIndexModel> battleStepIndexModels = battleStepIndexModelService.findAllByModelIdAndIsDel(battleStepModel.getId(),0);
				
				System.out.println("battleStepIndexModels:"+battleStepIndexModels);
				for(BattleStepIndexModel battleStepIndexModel:battleStepIndexModels){
					BattleRoomStepIndex battleRoomStepIndex = new BattleRoomStepIndex();
					
					battleRoomStepIndex.setBeanNum(battleStepIndexModel.getBeanNum());
					battleRoomStepIndex.setImgUrl(battleStepIndexModel.getImgUrl());
					battleRoomStepIndex.setLoveNum(battleStepIndexModel.getLoveNum());
					battleRoomStepIndex.setRewardType(battleStepIndexModel.getRewardType());
					battleRoomStepIndex.setRoomId(roomId);
					battleRoomStepIndex.setStepIndex(battleStepIndexModel.getStepIndex());
					
					battleRoomStepIndex.setIsBig(battleStepIndexModel.getIsBig());
					
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
					responseIndex.put("imgUrl", battleRoomStepIndex.getImgUrl());
					responseIndex.put("isBig", battleRoomStepIndex.getIsBig());
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
