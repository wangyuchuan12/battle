package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.BattleDrawItem;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomReward;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDrawItemService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomRewardService;
import com.battle.service.BattleRoomService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battle/battleDraw")
public class BattleDrawApi {

	@Autowired
	private BattleDrawItemService battleDrawItemService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleRoomRewardService battleRoomRewardService;
	
	@RequestMapping(value="list")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo list(HttpServletRequest httpServletRequest){
		List<BattleDrawItem> battleDrawItems = battleDrawItemService.findAllOrderByLevelAsc();
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleDrawItems);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="roomInfo")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo roomInfo(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String roomId = httpServletRequest.getParameter("roomId");
		
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		List<BattleRoomReward> battleRoomRewards = battleRoomRewardService.findAllByRoomIdOrderByRankAsc(battleRoom.getId());
		
		
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(
				battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId());
		
		BattlePeriodMember myBattlePeriodMember = null;
		for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
			if(battlePeriodMember.getUserId().equals(userInfo.getId())){
				myBattlePeriodMember = battlePeriodMember;
			}
		}
		Map<String, Object> data = new HashMap<>();
		data.put("name", battleRoom.getName());
		data.put("places",battleRoom.getPlaces());
		data.put("roomId", battleRoom.getId());
		data.put("battleId", battleRoom.getBattleId());
		data.put("rewards", battleRoomRewards);
		data.put("members", battlePeriodMembers);
		
		data.put("maxinum", battleRoom.getMaxinum());
		
		data.put("mininum", battleRoom.getMininum());
		
		data.put("roomStatus", battleRoom.getStatus());
		
		
		
		Long differ =(battleRoom.getStartTime().getMillis()-new DateTime().getMillis())/1000;
		
		data.put("timeDiffer",differ);
		
		if(!CommonUtil.isEmpty(myBattlePeriodMember)){
			data.put("status", myBattlePeriodMember.getStatus());
		}else{
			data.put("status", BattlePeriodMember.STATUS_FREE);
		}
		
		
		data.put("num", battleRoom.getNum());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
	
	@RequestMapping(value="randomLevel")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo randomLevel(HttpServletRequest httpServletRequest){
		Sort sort = new Sort(Direction.DESC,"createAt");
		Pageable pageable = new PageRequest(0,5,sort);
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattleRoom.STATUS_FREE);
		statuses.add(BattleRoom.STATUS_IN);
		List<BattleRoom> oldBattleRooms = battleRoomService.findAllByIsDanRoomAndStatusIn(1,statuses,pageable);
	
		
		BattleRoom battleRoom = null;
		for(BattleRoom oldBattleRoom:oldBattleRooms){
			if(oldBattleRoom.getStartTime().isBeforeNow()){
				if(oldBattleRoom.getMininum()>oldBattleRoom.getNum()){
					if(battleRoom==null){
						battleRoom = oldBattleRoom;
					}else{
						if(oldBattleRoom.getNum()<battleRoom.getNum()){
							battleRoom = oldBattleRoom;
						}
					}

				}
			}else{
				if(battleRoom==null){
					battleRoom = oldBattleRoom;
				}else{
					if(oldBattleRoom.getNum()<battleRoom.getNum()){
						battleRoom = oldBattleRoom;
					}
				}
			}
		}
		
		
		if(battleRoom==null){
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(false);
			
			resultVo.setErrorCode(0);
			
			return resultVo;
		}else{
			List<BattleDrawItem> battleDrawItems = battleDrawItemService.findAllByBattleIdAndPeriodId(battleRoom.getBattleId(),battleRoom.getPeriodId());
			
			if(battleDrawItems==null||battleDrawItems.size()==0){
				ResultVo resultVo = new ResultVo();
				
				resultVo.setSuccess(false);
				
				resultVo.setErrorCode(1);
				
				return resultVo;
			}else{

				Random random = new Random();
				
				Integer randomInt = random.nextInt(battleDrawItems.size());
				
				BattleDrawItem battleDrawItem = battleDrawItems.get(randomInt);
				
				
				Map<String, Object> data = new HashMap<String, Object>();
				
				data.put("roomId", battleRoom.getId());
				
				data.put("level", battleDrawItem.getLevel());
				
				data.put("battleId", battleRoom.getBattleId());
				
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
				
				resultVo.setData(data);
				
				return resultVo;
				
			}
		}
		
		
	}

}
