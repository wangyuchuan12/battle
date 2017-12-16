package com.battle.service.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleAccountResult;
import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.service.BattleAccountResultService;
import com.battle.service.BattleMemberPaperAnswerService;

@Service
public class AccountResultHandleService {

	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	
	@Autowired
	private BattleAccountResultService battleAccountResultService;
	
	public BattleAccountResult answerResult(BattleMemberPaperAnswer battleMemberPaperAnswer,BattleAccountResult battleAccountResult){
		Integer answerCount = battleMemberPaperAnswer.getAnswerCount();
		Integer passCount = battleMemberPaperAnswer.getPassCount();
		Integer rightSum = battleMemberPaperAnswer.getRightSum();
		Integer wrongSum = battleMemberPaperAnswer.getWrongSum();
		
		Long exp = battleAccountResult.getExp();
		if(exp==null){
			exp = 0l;
		}
		
		if(passCount==null){
			passCount = 0;
		}
		
		if(rightSum==null){
			rightSum = 0;
		}
		
		Integer addExp = 0;
		if(passCount<=rightSum){
			addExp = rightSum + passCount;
			
		}else{
			addExp = rightSum;
		}
		
		exp = exp + addExp;
		
		
		battleAccountResult.setExp(exp);
		
		battleMemberPaperAnswer.setExp(addExp);
		
		battleAccountResultService.update(battleAccountResult);
		
		return battleAccountResult;
	}
}
