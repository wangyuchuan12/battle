package com.battle.service.other;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleQuestionFactoryItem;
import com.battle.domain.BattleQuestionReview;
import com.battle.domain.Question;
import com.battle.domain.QuestionOption;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionFactoryItemService;
import com.battle.service.BattleQuestionReviewService;
import com.battle.service.BattleQuestionService;
import com.battle.service.QuestionOptionService;
import com.battle.service.QuestionService;
import com.wyc.common.domain.Account;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class QuestionAuditService {

	@Autowired
	private BattleQuestionFactoryItemService battleQuestionFactoryItemService;
	
	@Autowired
	private BattleQuestionReviewService battleQuestionReviewService;
	
	@Autowired
	private WxUserInfoService userInfoService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionOptionService questionOptionService;
	
	public static void main(String[]args){
		for(int i=0;i<100;i++){
			int index=(int)(Math.random()*5);
			System.out.println("index:"+index);
		}
	}
	
	
	public void rejectAudti(BattleQuestionFactoryItem battleQuestionFactoryItem){
		
		battleQuestionFactoryItem.setStatus(BattleQuestionFactoryItem.STATUS_REJECT);
		
		battleQuestionFactoryItemService.update(battleQuestionFactoryItem);
	}
	
	public void passAudit(BattleQuestionFactoryItem battleQuestionFactoryItem,BattleQuestionReview battleQuestionReview,String userId){
		
		UserInfo userInfo = userInfoService.findOne(battleQuestionFactoryItem.getUserId());
		
		/*Account account = accountService.findOne(userInfo.getAccountId());
		
		Long wisdomCount = account.getWisdomCount();*/
		Integer rewardBean = battleQuestionReview.getRewardBean();
		
		//wisdomCount = wisdomCount+rewardBean;
		
		battleQuestionFactoryItem.setRewardBean(rewardBean);
		
		String stageIndexes = battleQuestionReview.getStageIndexes();
		
		
		String[] stageIndexesStr = stageIndexes.split(",");
		
		List<BattlePeriodStage> battlePeriodStages = new ArrayList<>();
		
		for(String stageIndexStr:stageIndexesStr){
			Integer stageIndex = Integer.parseInt(stageIndexStr);
			BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOneByBattleIdAndPeriodIdAndIndex(battleQuestionReview.getBattleId(), battleQuestionReview.getPeriodId(), stageIndex);
			battlePeriodStages.add(battlePeriodStage);
		}
		
		int index=(int)(Math.random()*battlePeriodStages.size());
		
		BattlePeriodStage battlePeriodStage = battlePeriodStages.get(index);
		
		battleQuestionFactoryItem.setStatus(BattleQuestionFactoryItem.STATUS_PASS);
		battleQuestionFactoryItem.setStageId(battlePeriodStage.getId());
		
		String answer = battleQuestionFactoryItem.getAnswer();
		String fillWords = battleQuestionFactoryItem.getFillWords();
		String imgUrl = battleQuestionFactoryItem.getImgUrl();
		String options = battleQuestionFactoryItem.getOptions();
		String question = battleQuestionFactoryItem.getQuestion();
		int type = battleQuestionFactoryItem.getType();
		
		Question questionObj = new Question();
		questionObj.setAnswer(answer);
		questionObj.setFillWords(fillWords);
		questionObj.setImgUrl(imgUrl);
		questionObj.setType(type);
		questionObj.setQuestion(question);
		if(userInfo!=null){
			questionObj.setAuthorName(userInfo.getNickname());
		}
		questionObj.setIndex(0);
		questionObj.setIsDel(0);
		if(CommonUtil.isEmpty(imgUrl)){
			questionObj.setIsImg(0);
		}else{
			questionObj.setIsImg(1);
		}
		questionObj.setScore(5);
		questionObj.setSource(Question.FACTORY_SOURCE);
		questionService.add(questionObj);
		
		BattleQuestion battleQuestion = new BattleQuestion();
		battleQuestion.setAnswer(answer);
		battleQuestion.setBattleId(battleQuestionReview.getBattleId());
		battleQuestion.setBattlePeriodId(battleQuestionReview.getPeriodId());
		battleQuestion.setBattleSubjectId(battleQuestionReview.getBattleSubjectId());
		battleQuestion.setImgUrl(imgUrl);
		battleQuestion.setIsDel(0);
		battleQuestion.setOptions(options);
		battleQuestion.setPeriodStageId(battlePeriodStage.getId());
		battleQuestion.setQuestion(question);
		battleQuestion.setQuestionId(questionObj.getId());
		battleQuestion.setRightAnswer(answer);
		battleQuestion.setSeq(0);
		battleQuestion.setType(type);
		battleQuestionService.add(battleQuestion);
		if(type==Question.SELECT_TYPE){
			Integer seq = 0;
			for(String option:options.split(",")){
				seq++;
				QuestionOption questionOption = new QuestionOption();
				questionOption.setContent(option);
				questionOption.setQuestionId(questionObj.getId());
				questionOption.setIsDel(0);
				questionOption.setSeq(seq);
				questionOptionService.add(questionOption);
				if(option.equals(answer)){
					questionObj.setRightOptionId(questionOption.getId());
				}
			}
			questionService.update(questionObj);
		}
		
		battleQuestionFactoryItemService.update(battleQuestionFactoryItem);
	}
}
