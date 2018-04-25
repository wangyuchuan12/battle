package com.battle.socket.task;

import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleRoom;
import com.battle.socket.service.BattleRoomStartService;

@Service
public class RoomStartTask {
	
	@Autowired
	private BattleRoomStartService battleRoomStartService;

	public void run(final BattleRoom battleRoom){

		final Timer timer = new Timer();		
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Long differ =(battleRoom.getStartTime().getMillis()-new DateTime().getMillis())/1000;
				Integer num = battleRoom.getNum();
				Integer mininum = battleRoom.getMininum();
				if(num==null){
					num  = 0;
				}
				if(mininum==null){
					mininum = 0;
				}
				if(differ<=-20&&num>=mininum){
					timer.cancel();
					battleRoomStartService.startPublish(battleRoom);
				}
			}
		};
		
		timer.schedule(timerTask, 1000);
	}
}
