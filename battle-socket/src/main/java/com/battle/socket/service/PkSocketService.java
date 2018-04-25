package com.battle.socket.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.battle.domain.BattlePk;
import com.battle.socket.MessageHandler;
import com.battle.socket.MessageVo;
import com.battle.socket.vo.PkStatusVo;
import com.wyc.common.util.CommonUtil;

@Service
public class PkSocketService {
	
	@Autowired
	private MessageHandler messageHandler;
	
	public void statusPublish(final BattlePk battlePk){
		
		new Thread(){
			public void run() {
				PkStatusVo pkStatusVo = new PkStatusVo();
				pkStatusVo.setBattleId(battlePk.getBattleId());
				pkStatusVo.setRoomId(battlePk.getRoomId());
				pkStatusVo.setBeatImg(battlePk.getBeatUserImgurl());
				pkStatusVo.setBeatNickname(battlePk.getBeatUsername());
				pkStatusVo.setBeatStatus(battlePk.getBeatStatus());
				
				pkStatusVo.setHomeImg(battlePk.getHomeUserImgurl());
				pkStatusVo.setHomeNickname(battlePk.getHomeUsername());
				pkStatusVo.setHomeStatus(battlePk.getHomeStatus());
				
				pkStatusVo.setRoomStatus(battlePk.getRoomStatus());
				
				MessageVo messageVo = new MessageVo();
				
				List<String> userIds = new ArrayList<>();
				if(CommonUtil.isNotEmpty(battlePk.getBeatUserId())){
					userIds.add(battlePk.getBeatUserId());
				}
				
				if(CommonUtil.isNotEmpty(battlePk.getHomeUserId())){
					userIds.add(battlePk.getHomeUserId());
				}
				
				messageVo.setUserIds(userIds);
				messageVo.setCode(MessageVo.PK_STATUS_CODE);
				messageVo.setType(MessageVo.USERS_TYPE);
				messageVo.setData(pkStatusVo);
				
				try{
					messageHandler.sendMessage(messageVo);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		
	}
}
