package com.battle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePeriodMemberDao;
import com.battle.domain.BattlePeriodMember;
import com.wyc.common.service.RedisService;

@Service
public class BattlePeriodMemberService {
	
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private final String  LIST_KEY = "peroid_members_key";

	@Autowired
	private BattlePeriodMemberDao battlePeriodMemberDao;
	
	@Autowired
	private RedisService redisService;

	public BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(String battleId, String battleUserId,
			String periodId,String roomId,Integer isDel) {
		return battlePeriodMemberDao.findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(battleId,battleUserId,periodId,roomId,isDel);
	}
	
	public BattlePeriodMember findOneByRoomIdAndBattleUserIdAndIsDel(String roomId,String battleUserId,Integer isDel){
		return battlePeriodMemberDao.findOneByRoomIdAndBattleUserIdAndIsDel(roomId,battleUserId,isDel);
	}

	public void add(BattlePeriodMember battlePeriodMember) {
		
		battlePeriodMember.setId(UUID.randomUUID().toString());
		battlePeriodMember.setUpdateAt(new DateTime());
		battlePeriodMember.setCreateAt(new DateTime());

		battlePeriodMemberDao.save(battlePeriodMember);
		
		List<BattlePeriodMember> battlePeriodMembers = findBattlePeriodMembersByRoomIdFromCache(battlePeriodMember.getRoomId());
		if(battlePeriodMembers==null){
			battlePeriodMembers = new ArrayList<>();
		}
		
		battlePeriodMembers.add(battlePeriodMember);
		
		saveBattlePeriodMembersToCache(battlePeriodMember.getRoomId(),battlePeriodMembers);
		
	}

	public void update(BattlePeriodMember battlePeriodMember) {
		
		battlePeriodMember.setUpdateAt(new DateTime());
		
		battlePeriodMemberDao.save(battlePeriodMember);
		
		List<BattlePeriodMember> battlePeriodMembers = findBattlePeriodMembersByRoomIdFromCache(battlePeriodMember.getRoomId());
		if(battlePeriodMembers!=null&&battlePeriodMembers.size()>0){
			for(BattlePeriodMember battlePeriodMember2:battlePeriodMembers){
				if(battlePeriodMember2.getId().equals(battlePeriodMember.getId())){
					battlePeriodMember2.setBattleId(battlePeriodMember.getBattleId());
					battlePeriodMember2.setBattleUserId(battlePeriodMember.getBattleUserId());
					battlePeriodMember2.setHeadImg(battlePeriodMember.getHeadImg());
					battlePeriodMember2.setIsDel(battlePeriodMember.getIsDel());
					battlePeriodMember2.setLoveCount(battlePeriodMember.getLoveCount());
					battlePeriodMember2.setLoveResidule(battlePeriodMember.getLoveResidule());
					battlePeriodMember2.setNickname(battlePeriodMember.getNickname());
					battlePeriodMember2.setPeriodId(battlePeriodMember.getPeriodId());
					battlePeriodMember2.setProcess(battlePeriodMember.getProcess());
					battlePeriodMember2.setRoomId(battlePeriodMember.getRoomId());
					battlePeriodMember2.setStageCount(battlePeriodMember.getStageCount());
					battlePeriodMember2.setStageIndex(battlePeriodMember.getStageIndex());
					battlePeriodMember2.setStatus(battlePeriodMember.getStatus());
					battlePeriodMember2.setUpdateAt(battlePeriodMember.getUpdateAt());
					battlePeriodMember2.setUserId(battlePeriodMember.getUserId());
					battlePeriodMember2.setCreateAt(battlePeriodMember.getCreateAt());
				}
			}
			
			saveBattlePeriodMembersToCache(battlePeriodMember.getRoomId(),battlePeriodMembers);
		}
	}
	
	public List<BattlePeriodMember> findBattlePeriodMembersByRoomIdFromCache(String roomId){
		
		try{
			readWriteLock.readLock().lock();
			String key = LIST_KEY;
			key = key+"_"+roomId;
			List<BattlePeriodMember> battlePeriodMembers = redisService.getList(key);
			return battlePeriodMembers;
		}catch(Exception e){
			
		}finally{
			readWriteLock.readLock().unlock();
			
		}
		return null;
		
	}
	
	public void saveBattlePeriodMembersToCache(String roomId,List<BattlePeriodMember> battlePeriodMembers){
		try{
			readWriteLock.writeLock().lock();
			String key = LIST_KEY;
			key = key+"_"+roomId;
			redisService.setList(key, battlePeriodMembers,BattlePeriodMember.class);
		}catch(Exception e){
			
		}finally{
			readWriteLock.writeLock().unlock();
		}
		

	}
	
	
	public List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomId(String battleId,String periodId,String roomId){
		List<BattlePeriodMember> battlePeriodMembers = findBattlePeriodMembersByRoomIdFromCache(roomId);
		if(battlePeriodMembers!=null&&battlePeriodMembers.size()>0){
			return battlePeriodMembers;
		}else{
			battlePeriodMembers = battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndRoomIdOrderByCreateAtAsc(battleId, periodId, roomId);
			try{
				saveBattlePeriodMembersToCache(roomId, battlePeriodMembers);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return battlePeriodMembers;
			
		}
		
	}

	public List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,int isDel) {
		
		List<BattlePeriodMember> battlePeriodMembers = findAllByBattleIdAndPeriodIdAndRoomId(battleId, periodId, roomId);
		
		List<BattlePeriodMember> thisBattlePeriodMembers = new ArrayList<>();
		
		if(battlePeriodMembers!=null){
			for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
				
				
				int status2 = battlePeriodMember.getStatus();
				int isDel2 = battlePeriodMember.getIsDel();
				for(int status:statuses){
					if(status==status2){
						if(isDel2==isDel){
							thisBattlePeriodMembers.add(battlePeriodMember);
							break;
						}
					}
				}
			}
			return thisBattlePeriodMembers;
		}
		
		return battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDelOrderByCreateAtAsc(battleId,periodId,roomId,statuses,isDel);
		
	}

	public BattlePeriodMember findOne(String id) {
		
		return battlePeriodMemberDao.findOne(id);
	}
}
