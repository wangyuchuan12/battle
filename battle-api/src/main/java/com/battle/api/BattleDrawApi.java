package com.battle.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.battle.domain.BattleDrawItem;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDrawItemService;
import com.battle.service.BattleRoomService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping(value="/api/battle/battleDraw")
public class BattleDrawApi {

	@Autowired
	private BattleDrawItemService battleDrawItemService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
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
				
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
				
				resultVo.setData(battleDrawItem);
				
				return resultVo;
				
			}
		}
		
		
	}

}
