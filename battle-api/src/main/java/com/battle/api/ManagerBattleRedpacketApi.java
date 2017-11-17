package com.battle.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRedPacketAmountDistribution;
import com.battle.domain.BattleRedPacketType;
import com.battle.domain.BattleRedpacket;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattleRedPacketAmountDistributionService;
import com.battle.service.BattleRedPacketTypeService;
import com.battle.service.BattleRedpacketService;
import com.battle.service.BattleRoomService;
import com.wyc.annotation.HandlerAnnotation;

import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.util.DistributionAmountUtil;
import com.wyc.common.util.MySimpleDateFormat;

@Controller
@RequestMapping(value="/api/battleRedpacket/")
public class ManagerBattleRedpacketApi {

	@Autowired
	private BattleRedpacketService battleRedpacketService;
	
	@Autowired
	private MySimpleDateFormat mySimpleDateFormat;
	
	@Autowired
	private BattleRedPacketTypeService battleRedPacketTypeService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleRedPacketAmountDistributionService battleRedPacketAmountDistributionService;
	
	
	@RequestMapping(value="types")
	@ResponseBody
	public ResultVo types(HttpServletRequest httpServletRequest)throws Exception{
		List<BattleRedpacket> battleRedpackets = battleRedPacketTypeService.findAllByIsDel(0);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleRedpackets);
		
		return resultVo;
	}
	
	@RequestMapping(value="add")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo add(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattlePeriodMember battlePeriodMember =sessionManager.getObject(BattlePeriodMember.class);
		

		String takepartBean = httpServletRequest.getParameter("takepartBean");
		String takepartMasonry = httpServletRequest.getParameter("takepartMasonry");
		String takepartType = httpServletRequest.getParameter("takepartType");
		String isRoom = httpServletRequest.getParameter("isRoom");
		String isRoomMeet = httpServletRequest.getParameter("isRoomMeet");
		String roomId = httpServletRequest.getParameter("roomId");
		String typeId = httpServletRequest.getParameter("typeId");
		
		
		String isPersonalProcessMeet = httpServletRequest.getParameter("isPersonalProcessMeet");
		String isPersonalScoreMeet = httpServletRequest.getParameter("isPersonalScoreMeet");
		String isRoomProcessMeet = httpServletRequest.getParameter("isRoomProcessMeet");
		String isRoomScoreMeet = httpServletRequest.getParameter("isRoomScoreMeet");
		
		String personalProcessMeet = httpServletRequest.getParameter("personalProcessMeet");
		String personalScoreMeet = httpServletRequest.getParameter("personalScoreMeet");
		String roomProcessMeet = httpServletRequest.getParameter("roomProcessMeet");
		String roomScoreMeet = httpServletRequest.getParameter("roomScoreMeet");
		
		String roomMeetNum = httpServletRequest.getParameter("roomMeetNum");
		
		String stageIndex = httpServletRequest.getParameter("stageIndex");
		
		
		Integer isPersonalProcessMeetInt = null;
		Integer isPersonalScoreMeetInt = null;
		Integer isRoomProcessMeetInt = null;
		Integer isRoomScoreMeetInt = null;
		Integer personalProcessMeetInt = null;
		Integer personalScoreMeetInt = null;
		Integer roomProcessMeetInt = null;
		Integer roomScoreMeetInt = null;
		
		if(CommonUtil.isEmpty(isPersonalProcessMeet)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的isPersonalProcessMeet参数不能为空");
			return resultVo;
		}else{
			isPersonalProcessMeetInt = Integer.parseInt(isPersonalProcessMeet);
		}
		
		if(CommonUtil.isEmpty(isPersonalScoreMeet)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的isPersonalScoreMeet参数不能为空");
			return resultVo;
		}else{
			isPersonalScoreMeetInt = Integer.parseInt(isPersonalScoreMeet);
		}
		
		if(CommonUtil.isEmpty(isRoomProcessMeet)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的isRoomProcessMeet参数不能为空");
			return resultVo;
		}else{
			isRoomProcessMeetInt = Integer.parseInt(isRoomProcessMeet);
		}
		
		if(CommonUtil.isEmpty(isRoomScoreMeet)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的isRoomScoreMeet参数不能为空");
			return resultVo;
		}else{
			isRoomScoreMeetInt = Integer.parseInt(isRoomScoreMeet);
		}
		
		if(isPersonalProcessMeetInt==1){
			if(CommonUtil.isEmpty(personalProcessMeet)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的personalProcessMeet参数不能为空");
				return resultVo;
			}
			personalProcessMeetInt = Integer.parseInt(personalProcessMeet);
		}
		
		if(isPersonalScoreMeetInt==1){
			if(CommonUtil.isEmpty(personalScoreMeet)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的personalScoreMeet参数不能为空");
				return resultVo;
			}
			personalScoreMeetInt = Integer.parseInt(personalScoreMeet);
		}
		
		if(isRoomProcessMeetInt==1){
			if(CommonUtil.isEmpty(roomProcessMeet)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的roomProcessMeet参数不能为空");
				return resultVo;
			}
			roomProcessMeetInt = Integer.parseInt(roomProcessMeet);
		}
		
		if(isRoomScoreMeetInt==1){
			if(CommonUtil.isEmpty(roomScoreMeet)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的roomScoreMeet参数不能为空");
				return resultVo;
			}
			
			roomScoreMeetInt = Integer.parseInt(roomScoreMeet);
		}
		
		if(CommonUtil.isEmpty(takepartType)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的takepartType参数不能为空");
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(typeId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的type参数不能为空");
			return resultVo;
		}
		

		if(CommonUtil.isEmpty(isRoom)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的isRoom参数不能为空");
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(isRoomMeet)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的isRoomMeet参数不能为空");
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(stageIndex)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的stageIndex参数不能为空");
			return resultVo;
		}

		
		Integer takepartTypeInt = Integer.parseInt(takepartType);
		Integer isRoomInt = Integer.parseInt(isRoom);
		Integer isRoomMeetInt = Integer.parseInt(isRoomMeet);
		
		Integer stageIndexInt = Integer.parseInt(stageIndex);
		
		Integer roomMeetInt = 0;
		
		if(isRoomMeetInt==1){
			if(CommonUtil.isEmpty(roomMeetNum)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的roomMeetNum参数不能为空");
				return resultVo;
			}
		}
		
		
		roomMeetInt = Integer.parseInt(roomMeetNum);
		
		Integer takepartBeanInt = 0;
		Integer takepartMasonryInt = 0;
		
		if(takepartTypeInt==BattleRedpacket.TAKEPART_COST_BEAN_TYPE){
			if(CommonUtil.isEmpty(takepartBean)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的takepartBean参数不能为空");
				return resultVo;
			}
			takepartBeanInt = Integer.parseInt(takepartBean);
		}else if(takepartTypeInt == BattleRedpacket.COST_CHANGE_TYPE){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("不能零钱参与类型，目前还不可以");
		}else if(takepartTypeInt == BattleRedpacket.COST_MASONRY_TYPE){
			if(CommonUtil.isEmpty(takepartMasonryInt)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的takepartBean参数不能为空");
				return resultVo;
			}
			takepartMasonryInt = Integer.parseInt(takepartMasonry);
		}else {
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的参数type不合法");
			return resultVo;
		}
		
		if(isRoomInt==0){
			if(CommonUtil.isEmpty(roomId)){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("输入的参数roomId不能为空");
				return resultVo;
			}
		}
		
		
		BattleRedpacket battleRedpacket = new BattleRedpacket();
		battleRedpacket.setIsPersonalProcessMeet(isPersonalProcessMeetInt);
		battleRedpacket.setIsPersonalScoreMeet(isPersonalScoreMeetInt);
		battleRedpacket.setIsRoom(isRoomInt);
		battleRedpacket.setIsRoomMeet(isRoomMeetInt);
		battleRedpacket.setIsRoomProcessMeet(isRoomProcessMeetInt);
		battleRedpacket.setIsRoomScoreMeet(isRoomScoreMeetInt);
		battleRedpacket.setPersonalProcessMeet(personalProcessMeetInt);
		battleRedpacket.setPersonalScoreMeet(personalScoreMeetInt);
		
		battleRedpacket.setRoomProcessMeet(roomProcessMeetInt);
		battleRedpacket.setRoomScoreMeet(roomScoreMeetInt);
		
		battleRedpacket.setTakepartCostBean(takepartBeanInt.longValue());
		battleRedpacket.setTakepartCostMasonry(takepartMasonryInt.longValue());
		
		battleRedpacket.setIsRoom(isRoomInt);
		battleRedpacket.setIsRoomMeet(isRoomMeetInt);
		battleRedpacket.setRoomMeetNum(roomMeetInt);
		battleRedpacket.setRoomId(roomId);
		
		battleRedpacket.setTypeId(typeId);
		
		
		
		
		battleRedpacket.setStageIndex(stageIndexInt);
		
		battleRedpacket.setSenderImg(battlePeriodMember.getHeadImg());
		battleRedpacket.setSenderName(battlePeriodMember.getNickname());
		
		battleRedpacket.setReceiveNum(0);
		
		BattleRedPacketType battleRedPacketType = battleRedPacketTypeService.findOne(typeId);
		
		if(battleRedPacketType==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("取回的battleRedPacketType对象为空");
			return resultVo;
		}
		
		BigDecimal amount = battleRedPacketType.getAmount();
		if(amount==null){
			amount = new BigDecimal(0);
		}
		Long beanNum = battleRedPacketType.getBeanNum();
		if(beanNum==null){
			beanNum = 0L;
		}
		BigDecimal costAmount = battleRedPacketType.getCostAmount();
		Long costBean = battleRedPacketType.getCostBean();
		Long costMasonry = battleRedPacketType.getCostMasonry();
		Integer costType = battleRedPacketType.getCostType();
		Long masonryNum = battleRedPacketType.getMasonryNum();
		if(masonryNum==null){
			masonryNum = 0L;
		}
		Integer num = battleRedPacketType.getNum();
		
		battleRedpacket.setAmount(amount);
		battleRedpacket.setBeanNum(beanNum);
		battleRedpacket.setMasonryNum(masonryNum);
		battleRedpacket.setCostAmount(costAmount);
		battleRedpacket.setCostBean(costBean);
		battleRedpacket.setCostMasonry(costMasonry);
		battleRedpacket.setCostType(costType);
		
		battleRedpacket.setNum(num);
		
		battleRedpacket.setHandTime(new DateTime());
	
		battleRedpacketService.add(battleRedpacket);
		
		DistributionAmountUtil distributionAmountUtil = new DistributionAmountUtil();
		
		List<Integer> amountDistributions = null;
		List<Integer> beanDistributions = null;
		List<Integer> masonryDistributions = null;
		if(amount!=null&&amount.floatValue()>0){
			amountDistributions = distributionAmountUtil.splitRedPackets(amount.multiply(new BigDecimal(1000)).intValue(), battleRedpacket.getNum(),1,100000);
		}
		
		if(beanNum!=null&&beanNum>0){
			beanDistributions = distributionAmountUtil.splitRedPackets(beanNum.intValue(), battleRedpacket.getNum(),0,100000);
		}
		
		if(masonryNum!=null&&masonryNum>0){
			masonryDistributions = distributionAmountUtil.splitRedPackets(masonryNum.intValue(), battleRedpacket.getNum(), 0, 100);
		}
		
		
		for(int index=0;index<battleRedpacket.getNum();index++){
			BattleRedPacketAmountDistribution battleRedPacketAmountDistribution = new BattleRedPacketAmountDistribution();
			if(amountDistributions!=null&&amountDistributions.size()>0){
				Integer amountDistribution = amountDistributions.get(index);
				if(amountDistribution==null){
					amountDistribution = 0;
				}
				BigDecimal amountBigDecimal = new BigDecimal(amountDistribution).divide(new BigDecimal("1000"));
				battleRedPacketAmountDistribution.setAmount(amountBigDecimal);
				battleRedPacketAmountDistribution.setSeq(index);
			}
			
			if(beanDistributions!=null&&beanDistributions.size()>0){
				Integer beanDistribution = beanDistributions.get(index);
				if(beanDistribution==null){
					beanDistribution = 0;
				}
				battleRedPacketAmountDistribution.setBeanNum(beanDistribution.longValue());
			}
			
			if(masonryDistributions!=null&&masonryDistributions.size()>0){
				Integer masonryDistribution = masonryDistributions.get(index);
				if(masonryDistribution==null){
					masonryDistribution = 0;
				}
				
				battleRedPacketAmountDistribution.setMastonryNum(masonryDistribution.longValue());
			}
			
			
			
			battleRedPacketAmountDistribution.setRoomId(roomId);
			battleRedPacketAmountDistribution.setRedPacketId(battleRedpacket.getId());
			battleRedPacketAmountDistribution.setStatus(BattleRedPacketAmountDistribution.STATUS_FREE);
			
			
			battleRedPacketAmountDistributionService.add(battleRedPacketAmountDistribution);
			
		
		}
		
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		Integer redPackNum = battleRoom.getRedPackNum();
		if(redPackNum==null){
			redPackNum = 0;
		}
		
		redPackNum++;
		
		battleRoom.setRedPackNum(redPackNum);
		
		battleRoom.setIsRedpack(1);
		
		BigDecimal redpackAmount = battleRoom.getRedpackAmount();
		
		if(redpackAmount==null){
			redpackAmount = new BigDecimal(0);
		}
		
		Integer redpackBean = battleRoom.getRedpackBean();
		
		if(redpackBean==null){
			redpackBean = 0;
		}
		
		Integer redpackMasonry = battleRoom.getRedpackMasonry();
		
		if(redpackMasonry==null){
			redpackMasonry = 0;
		}
		
		
		redpackAmount = redpackAmount.add(amount);
		
		redpackBean = redpackBean + beanNum.intValue();
		
		redpackMasonry = redpackMasonry+masonryNum.intValue();
		
		battleRoom.setRedpackAmount(redpackAmount);
		
		battleRoom.setRedpackBean(redpackBean);
		
		battleRoom.setRedpackMasonry(redpackMasonry);
		
		battleRoomService.update(battleRoom);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	@RequestMapping(value="listByRoomId")
	@ResponseBody
	public ResultVo listByRoomId(HttpServletRequest httpServletRequest){
		String roomId = httpServletRequest.getParameter("roomId");
		
		List<BattleRedpacket> battleRedpackets = battleRedpacketService.findAllByIsRoomAndRoomIdOrderByHandTimeDesc(1,roomId);
		
		ResultVo resultVo = new ResultVo();
		
		List<Map<String, Object>> responseData = new ArrayList<>();
		
		for(BattleRedpacket battleRedpacket:battleRedpackets){
			Map<String, Object> redPacketMap = new HashMap<>();
			redPacketMap.put("amount", battleRedpacket.getAmount());
			redPacketMap.put("beanNum", battleRedpacket.getBeanNum());
			redPacketMap.put("masonryNum", battleRedpacket.getMasonryNum());
			redPacketMap.put("costBean", battleRedpacket.getCostBean());
			redPacketMap.put("costMasonry", battleRedpacket.getCostMasonry());
			redPacketMap.put("id", battleRedpacket.getId());
			redPacketMap.put("isRoom", battleRedpacket.getIsRoom());
			redPacketMap.put("isRoomMeet", battleRedpacket.getIsRoomMeet());
			redPacketMap.put("num", battleRedpacket.getNum());
			redPacketMap.put("receiveAmount", battleRedpacket.getReceiveAmount());
			redPacketMap.put("receiveNum", battleRedpacket.getReceiveNum());
			redPacketMap.put("roomId", battleRedpacket.getRoomId());
			redPacketMap.put("roomMeetNum", battleRedpacket.getRoomMeetNum());
			redPacketMap.put("status", battleRedpacket.getStatus());
			redPacketMap.put("timeLong", battleRedpacket.getTimeLong());
			
			redPacketMap.put("isPersonalProcessMeet", battleRedpacket.getIsPersonalProcessMeet());
			redPacketMap.put("isPersonalScoreMeet", battleRedpacket.getIsPersonalScoreMeet());
			redPacketMap.put("isRoomProcessMeet", battleRedpacket.getIsRoomProcessMeet());
			redPacketMap.put("isRoomScoreMeet", battleRedpacket.getIsRoomScoreMeet());
			
			redPacketMap.put("personalProcessMeet", battleRedpacket.getPersonalProcessMeet());
			redPacketMap.put("personalScoreMeet", battleRedpacket.getPersonalScoreMeet());
			redPacketMap.put("roomProcessMeet", battleRedpacket.getRoomProcessMeet());
			redPacketMap.put("roomScoreMeet", battleRedpacket.getRoomScoreMeet());
			
			redPacketMap.put("handTime", mySimpleDateFormat.format(battleRedpacket.getHandTime().toDate()));
			
			redPacketMap.put("senderImg", battleRedpacket.getSenderImg());
			redPacketMap.put("senderName", battleRedpacket.getSenderName());
			
			responseData.add(redPacketMap);
		}
		
		resultVo.setSuccess(true);
		resultVo.setData(responseData);
		return resultVo;
		
	}
}
