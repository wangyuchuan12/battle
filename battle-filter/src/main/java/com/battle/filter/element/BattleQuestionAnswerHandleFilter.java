package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattleMemberQuestionAnswer;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.Question;
import com.battle.domain.QuestionAnswer;
import com.battle.domain.QuestionAnswerItem;
import com.battle.domain.QuestionOption;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattleMemberQuestionAnswerService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.QuestionOptionService;
import com.wyc.AttrEnum;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleQuestionAnswerHandleFilter extends Filter{

	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerSerivice;
	
	@Autowired
	private BattleMemberQuestionAnswerService battleMemberQuestionAnswerService;
	
	@Autowired
	private QuestionOptionService questionOptionService;

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		
		Question question = sessionManager.getObject(Question.class);
		QuestionAnswer questionAnswer = sessionManager.getObject(QuestionAnswer.class);
		
		
		String memberId = (String)sessionManager.getAttribute(AttrEnum.periodMemberId);
		BattleMemberPaperAnswer battleMemberPaperAnswer = battleMemberPaperAnswerSerivice.findOneByBattlePeriodMemberId(memberId);
		if(battleMemberPaperAnswer==null){
			battleMemberPaperAnswer = new BattleMemberPaperAnswer();
			battleMemberPaperAnswer.setBattlePeriodMemberId(memberId);
			battleMemberPaperAnswer.setWrongSum(questionAnswer.getWrongSum());
			battleMemberPaperAnswer.setRightSum(questionAnswer.getRightSum());
			battleMemberPaperAnswerSerivice.add(battleMemberPaperAnswer);
		}else{
			battleMemberPaperAnswer.setWrongSum(questionAnswer.getWrongSum());
			battleMemberPaperAnswer.setRightSum(questionAnswer.getRightSum());
			battleMemberPaperAnswer.setSubLove(questionAnswer.getWrongSum());
			
			battleMemberPaperAnswer.setAddDistance(questionAnswer.getRightSum()*10);
			
			battleMemberPaperAnswerSerivice.update(battleMemberPaperAnswer);
		}
		
		BattlePeriodMember battlePeriodMember = sessionManager.findOne(BattlePeriodMember.class, memberId);
		
		Battle battle = sessionManager.findOne(Battle.class, battlePeriodMember.getBattleId());
		
		if(battlePeriodMember.getStageIndex()<battle.getCurrentPeriodIndex()){
			battlePeriodMember.setStageIndex(battlePeriodMember.getStageIndex()+1);
			
			sessionManager.update(battlePeriodMember);
		}
		
		QuestionAnswerItem questionAnswerItem = sessionManager.getObject(QuestionAnswerItem.class);
		
		BattleMemberQuestionAnswer battleMemberQuestionAnswer = new BattleMemberQuestionAnswer();
		battleMemberQuestionAnswer.setBattleMemberPaperAnswerId(battleMemberPaperAnswer.getId());
		battleMemberQuestionAnswer.setQuestionId(questionAnswerItem.getQuestionId());
		battleMemberQuestionAnswer.setImgUrl(question.getImgUrl());
		battleMemberQuestionAnswer.setQuestion(question.getQuestion());
		battleMemberQuestionAnswer.setRightAnswer(questionAnswerItem.getRightAnswer());
		battleMemberQuestionAnswer.setAnswer(questionAnswerItem.getMyAnswer());
		battleMemberQuestionAnswer.setType(question.getType());
		
		if(question.getType()==Question.SELECT_TYPE){
			List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(question.getId());
			
			StringBuffer sb = new StringBuffer();
			
			for(QuestionOption questionOption:questionOptions){
				sb.append(questionOption.getContent());
				sb.append(",");
			}
			
			if(questionOptions!=null&&questionOptions.size()>0){
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			
			battleMemberQuestionAnswer.setOptions(sb.toString());
		}
		
		
		
		battleMemberQuestionAnswerService.add(battleMemberQuestionAnswer);
		
		return battleMemberQuestionAnswer;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
