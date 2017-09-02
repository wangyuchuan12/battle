package com.battle.service.dbExectuer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriodMember;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleService;
import com.wyc.common.session.DbServiceExecuter;

@Service
public class DbServiceExecuterImp implements DbServiceExecuter{

	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	@Override
	public void update(List<Object> objs) {
		if(objs!=null&&objs.size()>0){
			for(Object target:objs){
				if(target!=null){
					Class<?> type = target.getClass();
					if(type.equals(Battle.class)){
						battleService.update((Battle)target);
					}else if(type.equals(BattlePeriodMember.class)){
						battlePeriodMemberService.update((BattlePeriodMember)target);
					}
				}
			}
		}
	}

	@Override
	public <T> T findOne(Class<T> clazz, String id) {
		if(clazz.equals(Battle.class)){
			Battle battle = battleService.findOne(id);
			return (T)battle;
		}
		return null;
	}

}
