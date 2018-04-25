package com.battle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
	
	public BattlePeriodMember findOneByRoomIdAndUserIdAndIsDel(String roomId,String userId,Integer isDel){
		return battlePeriodMemberDao.findOneByRoomIdAndUserIdAndIsDel(roomId,userId,isDel);
	}
	
	public Long rank(String roomId,Integer score){
		return battlePeriodMemberDao.rank(roomId, score);
	}
	
	

	public void add(BattlePeriodMember battlePeriodMember) {
		
		
		
		/*List<BattlePeriodMember> battlePeriodMembers = findAllByBattleIdAndPeriodIdAndRoomId(battlePeriodMember.getBattleId(),battlePeriodMember.getPeriodId(),battlePeriodMember.getRoomId());
		if(battlePeriodMembers==null){
			battlePeriodMembers = new ArrayList<>();
		}
		
		battlePeriodMember.setId(UUID.randomUUID().toString());
		battlePeriodMember.setUpdateAt(new DateTime());
		battlePeriodMember.setCreateAt(new DateTime());

		battlePeriodMemberDao.save(battlePeriodMember);
		
		battlePeriodMembers.add(battlePeriodMember);
		
		saveBattlePeriodMembersToCache(battlePeriodMember.getRoomId(),battlePeriodMembers);*/
		
		battlePeriodMember.setId(UUID.randomUUID().toString());
		battlePeriodMember.setUpdateAt(new DateTime());
		battlePeriodMember.setCreateAt(new DateTime());
		battlePeriodMemberDao.save(battlePeriodMember);
		
	}

	public void update(BattlePeriodMember battlePeriodMember) {
		
		
		
		/*List<BattlePeriodMember> battlePeriodMembers = findAllByBattleIdAndPeriodIdAndRoomId(battlePeriodMember.getBattleId(),battlePeriodMember.getPeriodId(),battlePeriodMember.getRoomId());
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
			
			battlePeriodMember.setUpdateAt(new DateTime());
			
			battlePeriodMemberDao.save(battlePeriodMember);
			
			saveBattlePeriodMembersToCache(battlePeriodMember.getRoomId(),battlePeriodMembers);
		}*/
		battlePeriodMember.setUpdateAt(new DateTime());
		
		battlePeriodMemberDao.save(battlePeriodMember);
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
				//saveBattlePeriodMembersToCache(roomId, battlePeriodMembers);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return battlePeriodMembers;
			
		}
		
	}

	public List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,int isDel,Pageable pageable) {
		
		/*List<BattlePeriodMember> battlePeriodMembers = findAllByBattleIdAndPeriodIdAndRoomId(battleId, periodId, roomId);
		
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
		}*/
		
		return battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleId,periodId,roomId,statuses,isDel,pageable);
		
	}
	
	public List<BattlePeriodMember>findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,String groupId,int isDel,Pageable pageable){
		
		return battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(battleId, periodId, roomId, statuses, groupId, isDel, pageable);
	}

	public BattlePeriodMember findOne(String id) {
		
		return battlePeriodMemberDao.findOne(id);
	}

	public List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomId(String battleId, String periodId, String roomId,
			Pageable pageable) {
		
		return battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndRoomId(battleId,periodId,roomId,pageable);
	}
}
