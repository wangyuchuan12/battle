package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleAccountResult;
import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattleMemberQuestionAnswer;
import com.battle.domain.BattleNotice;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleRoom;
import com.battle.domain.Question;
import com.battle.domain.QuestionAnswer;
import com.battle.domain.QuestionAnswerItem;
import com.battle.domain.QuestionOption;
import com.battle.filter.element.CurrentAccountResultFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattleMemberLoveCoolingService;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattleMemberQuestionAnswerService;
import com.battle.service.BattleNoticeService;
import com.battle.service.BattlePeriodMemberService;
//import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleRoomService;
import com.battle.service.QuestionAnswerItemService;
import com.battle.service.QuestionAnswerService;
import com.battle.service.QuestionOptionService;
import com.battle.service.QuestionService;
import com.battle.service.other.AccountResultHandleService;
import com.battle.socket.service.ProgressStatusSocketService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/question/")
public class QuestionApi {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionOptionService questionOptionService;
	
	@Autowired
	private QuestionAnswerService questionAnswerService;
	
	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	
	@Autowired
	private QuestionAnswerItemService questionAnswerItemService;
	
	@Autowired
	private BattleMemberQuestionAnswerService battleMemberQuestionAnswerService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	/*@Autowired
	private BattlePeriodService battlePeriodService;*/
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private AccountResultHandleService accountResultHandleService;
	
	@Autowired
	private BattleMemberLoveCoolingService battleMemberLoveCoolingService;
	
	
	@Autowired
	private ProgressStatusSocketService progressStatusSocketService;
	

	final static Logger logger = LoggerFactory.getLogger(QuestionApi.class);
	
	@RequestMapping(value="battleQuestionAnswer")
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	@ResponseBody
	@Transactional
	public Object battleQuestionAnswer(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		final BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		BattleRoom battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
		
		Integer right1AddProcess = battleRoom.getRight1AddProcess();
		Integer right2AddProcess = battleRoom.getRight2AddProcess();
		Integer right3AddProcess = battleRoom.getRight3AddProcess();
		Integer right4AddProcess = battleRoom.getRight4AddProcess();
		Integer right5AddProcess = battleRoom.getRight5AddProcess();
		Integer right6AddProcess = battleRoom.getRight6AddProcess();
		Integer rightAddScore = battleRoom.getRightAddScore();
		Integer wrongSubScore = battleRoom.getWrongSubScore();
		if(wrongSubScore==null){
			wrongSubScore = 0;
		}
		
		if(right1AddProcess==null){
			right1AddProcess = 0;
		}
		
		if(right2AddProcess==null){
			right2AddProcess = 0;
		}
		
		if(right3AddProcess==null){
			right3AddProcess = 0;
		}
		
		if(right4AddProcess==null){
			right4AddProcess = 0;
		}
		
		if(right5AddProcess==null){
			right5AddProcess = 0;
		}
		
		if(right6AddProcess==null){
			right6AddProcess = 0;
		}
		
		if(rightAddScore==null){
			rightAddScore = 0;
		}
	
		
		String id = httpServletRequest.getParameter("id");
		
		//String stageIndex = httpServletRequest.getParameter("stageIndex");
		
		System.out.println("member.stageIndex:"+battlePeriodMember.getStageIndex());
		
		Integer stageIndex = battlePeriodMember.getStageIndex()-1;
		
		Question question = questionService.findOne(id);
		
		List<QuestionAnswer> questionAnswers = questionAnswerService.findAllByTargetIdAndType(battlePeriodMember.getId()+"_"+stageIndex, QuestionAnswer.BATTLE_TYPE);
		
		QuestionAnswer questionAnswer = null;
		
		if(questionAnswers!=null&&questionAnswers.size()>0){
			questionAnswer = questionAnswers.get(0);
		}else{
			logger.error("答题无questionAnswer发现");
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(404);
			resultVo.setErrorMsg("答题无questionAnswer发现");
			return resultVo;
		}
		
		BattleMemberPaperAnswer battleMemberPaperAnswer = battleMemberPaperAnswerService.findOneByBattlePeriodMemberIdAndStageIndex(battlePeriodMember.getId(), stageIndex);
		
		
		QuestionAnswerItem questionAnswerItem = new QuestionAnswerItem();
		
		questionAnswerItem.setQuestionAnswerId(questionAnswer.getId());
		questionAnswerItem.setQuestionId(question.getId());
		questionAnswerItem.setType(question.getType());
		
		BattleMemberQuestionAnswer battleMemberQuestionAnswer = new BattleMemberQuestionAnswer();
		
		battleMemberQuestionAnswer.setBattleMemberPaperAnswerId(battleMemberPaperAnswer.getId());
		battleMemberQuestionAnswer.setImgUrl(question.getImgUrl());
		battleMemberQuestionAnswer.setQuestion(question.getQuestion());
		battleMemberQuestionAnswer.setQuestionId(question.getId());
		battleMemberQuestionAnswer.setType(question.getType());
		
		Map<String, Object> result = new HashMap<>();
		
		
		ResultVo resultVo = new ResultVo();
		
		if(question.getType()==Question.SELECT_TYPE){
			String optionId = httpServletRequest.getParameter("optionId");
			
			if(CommonUtil.isEmpty(optionId)){
				optionId = "";
				questionAnswerItem.setMyAnswer("");
				battleMemberQuestionAnswer.setAnswer("");
			}else{
				QuestionOption myOption = questionOptionService.findOne(optionId);
				questionAnswerItem.setMyAnswer(myOption.getContent());
				battleMemberQuestionAnswer.setAnswer(myOption.getContent());
			}
			
			
			
			QuestionOption rightOption = questionOptionService.findOne(question.getRightOptionId());
			StringBuffer sb = new StringBuffer();
			List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(question.getId());
			for(QuestionOption questionOption:questionOptions){
				sb.append(questionOption.getContent());
				sb.append(",");
			}
			
			if(questionOptions!=null&&questionOptions.size()>0){
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			
			
			questionAnswerItem.setMyOptionId(optionId);
			questionAnswerItem.setRightAnswer(rightOption.getContent());
			questionAnswerItem.setRightOptionId(question.getRightOptionId());

			
			
			battleMemberQuestionAnswer.setOptions(sb.toString());
			
			battleMemberQuestionAnswer.setRightAnswer(rightOption.getContent());
			
			if(optionId.equals(question.getRightOptionId())){
				
				questionAnswerItem.setIsRight(1);
				
			}else{
				
				questionAnswerItem.setIsRight(0);
				
			}
			resultVo.setSuccess(true);
			resultVo.setData(result);
			
			
			
			
		}else if(question.getType()==Question.INPUT_TYPE){
			String answer = httpServletRequest.getParameter("answer");
			
			questionAnswerItem.setMyAnswer(answer);
			questionAnswerItem.setRightAnswer(question.getAnswer());
			battleMemberQuestionAnswer.setAnswer(answer);
			battleMemberQuestionAnswer.setRightAnswer(question.getAnswer());
			if(answer!=null&&answer.equals(question.getAnswer())){
				
				questionAnswerItem.setIsRight(1);
				
			}else{
				
				questionAnswerItem.setIsRight(0);
			
			}
			resultVo.setSuccess(true);
			resultVo.setData(result);
			
		}else if(question.getType()==Question.FILL_TYPE){
			String answer = httpServletRequest.getParameter("answer");
			
			questionAnswerItem.setMyAnswer(answer);
			questionAnswerItem.setRightAnswer(question.getAnswer());
			battleMemberQuestionAnswer.setAnswer(answer);
			battleMemberQuestionAnswer.setRightAnswer(question.getAnswer());
			if(answer!=null&&answer.equals(question.getAnswer())){
				
				questionAnswerItem.setIsRight(1);
				
			}else{
				questionAnswerItem.setIsRight(0);
				
			}
			
			
			resultVo.setSuccess(true);
			resultVo.setData(result);
			
		}
		
		
		
		
		
		Integer answerCount = battleMemberPaperAnswer.getAnswerCount();
		
		if(answerCount==null){
			answerCount = 0;
		}
		
		battleMemberPaperAnswer.setAnswerCount(answerCount+1);
		
		Integer process = battlePeriodMember.getProcess();
		
		Integer paperProcess = 0;
		
		if(process==null){
			process = 0;
		}
		
		if(questionAnswerItem.getIsRight()==1){
			Integer paperScore = battleMemberPaperAnswer.getScore();
			if(paperScore==null){
				paperScore = 0;
			}
			
			paperScore = paperScore + rightAddScore;
			questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
			
			battleMemberPaperAnswer.setRightSum(battleMemberPaperAnswer.getRightSum()+1);
			
			battleMemberPaperAnswer.setScore(paperScore);
			
			Integer score = battlePeriodMember.getScore();
			
			battlePeriodMember.setScore(score+rightAddScore);
		}else{
			Integer paperScore = battleMemberPaperAnswer.getScore();
			if(paperScore==null){
				paperScore = 0;
			}	
			
			paperScore = paperScore-wrongSubScore;
			
			result.put("right", false);
			result.put("process", 0);
			
			
			Integer loveResidule = battlePeriodMember.getLoveResidule();
			
			
			
			if(loveResidule==battlePeriodMember.getLoveCount()){
				BattleMemberLoveCooling battleMemberLoveCooling = battleMemberLoveCoolingService.
						findOneByBattleMemberId(battleMemberPaperAnswer.getBattlePeriodMemberId());
				if(battleMemberLoveCooling!=null){
					battleMemberLoveCooling.setStartDatetime(new DateTime());
					battleMemberLoveCooling.setSchedule(0L);
					battleMemberLoveCoolingService.update(battleMemberLoveCooling);
				}
			}
			loveResidule--;
			battlePeriodMember.setLoveResidule(loveResidule);
			
			questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
			
			battleMemberPaperAnswer.setScore(paperScore);
			
			battleMemberPaperAnswer.setWrongSum(battleMemberPaperAnswer.getWrongSum()+1);
			
		}
		
		
		if(battleMemberPaperAnswer.getRightSum()==null||battleMemberPaperAnswer.getRightSum()==0){
			paperProcess = 0;
		}else if(battleMemberPaperAnswer.getRightSum()==1){
			paperProcess = battleRoom.getRight1AddProcess();
		}else if(battleMemberPaperAnswer.getRightSum()==2){
			paperProcess = battleRoom.getRight2AddProcess();
		}else if(battleMemberPaperAnswer.getRightSum()==3){
			paperProcess = battleRoom.getRight3AddProcess();
		}else if(battleMemberPaperAnswer.getRightSum()==4){
			paperProcess = battleRoom.getRight4AddProcess();
		}else if(battleMemberPaperAnswer.getRightSum()==5){
			paperProcess = battleRoom.getRight5AddProcess();
		}else if(battleMemberPaperAnswer.getRightSum()==6){
			paperProcess = battleRoom.getRight6AddProcess();
		}
		
		result.put("process", paperProcess);
		
		battleMemberPaperAnswer.setProcess(paperProcess);
		
		
		
		if(paperProcess==null){
			paperProcess = 0;
		}
		
		
		Integer startIndex = battleMemberPaperAnswer.getStartIndex();
		
		Integer endIndex = startIndex + paperProcess;
		
		battleMemberPaperAnswer.setEndIndex(endIndex);
		
		battlePeriodMember.setProcess(endIndex);
		
		if(questionAnswerItem.getIsRight()==1){
			
			result.put("right", true);
			

			
			
			result.put("score", rightAddScore);
			
			
		}else{
			
			result.put("score", 0);

		}
		
		

		Integer passCount = battleMemberPaperAnswer.getPassCount();
		if(passCount==null){
			passCount = 0;
		}
		Integer rightCount = battleMemberPaperAnswer.getRightSum();
		if(rightCount==null){
			rightCount = 0;
		}
		Long rewardCountBean = 0L;
		if(passCount<=rightCount&&battleMemberPaperAnswer.getIsPass()!=1){
			
			UserInfo userInfo = sessionManager.getObject(UserInfo.class);
			battleMemberPaperAnswer.setIsPass(1);
			Integer passRewardBean = battleMemberPaperAnswer.getPassRewardBean();
			if(passRewardBean==null){
				passRewardBean=0;
			}
			Account account = accountService.fineOneSync(userInfo.getAccountId());
			Long wisdomLimit = account.getWisdomLimit();
			Long wisdomCount = account.getWisdomCount();
			Long wisdomCountOld = account.getWisdomCount();
			wisdomCount = wisdomCount+passRewardBean;
			if(wisdomCount>wisdomLimit){
				wisdomCount = wisdomLimit;
			}
			rewardCountBean = wisdomCount-wisdomCountOld;
			battleMemberPaperAnswer.setThisRewardBean(rewardCountBean);
			account.setWisdomCount(wisdomCount);
			accountService.update(account);
		}
		
		
		
		
		
		result.put("battleMemberQuestionAnswerId",battleMemberQuestionAnswer.getId());
		
		result.put("battleMemberPaperAnswerId",battleMemberPaperAnswer.getId());
		
		result.put("rewardBean", battleMemberPaperAnswer.getThisRewardBean());
		
		result.put("isPass", battleMemberPaperAnswer.getIsPass());
		
		result.put("memberProcess", battlePeriodMember.getProcess());
		
		result.put("loveResidule", battlePeriodMember.getLoveResidule());
		
		questionAnswerItemService.add(questionAnswerItem);
		
		if(battleMemberPaperAnswer.getAnswerCount()>=battleMemberPaperAnswer.getQuestionCount()){
			
			battleMemberPaperAnswer.setStatus(BattleMemberPaperAnswer.END_STATUS);
			result.put("isLast", true);
			
		}else{
			result.put("isLast", false);
			battleMemberPaperAnswer.setStatus(BattleMemberPaperAnswer.IN_STATUS);
		}
		
		result.put("memberScore", battlePeriodMember.getScore());
		
		
		
		battlePeriodMemberService.update(battlePeriodMember);
		battleMemberQuestionAnswerService.add(battleMemberQuestionAnswer);
		
		sessionManager.update(questionAnswer);
		sessionManager.update(battleMemberPaperAnswer);
	
		new Thread(){
			public void run() {
				try{
					progressStatusSocketService.statusPublish(battlePeriodMember.getRoomId(), battlePeriodMember,battlePeriodMember.getUserId());
				}catch(Exception e){
					logger.error("{}",e);
				}
			}
		}.start();
		
		return resultVo;
	}
	
	@RequestMapping(value="createPaperAnswer")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public Object createPaperAnswer(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);

		String type = httpServletRequest.getParameter("type");

		String questions = httpServletRequest.getParameter("questions");
		
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		BattleRoom battleRoom = sessionManager.findOne(BattleRoom.class, battlePeriodMember.getRoomId());
		
		Integer stageIndex = battlePeriodMember.getStageIndex();
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_IN){
			
			battlePeriodMember.setStageIndex(stageIndex+1);
			
			battlePeriodMemberService.update(battlePeriodMember);
		}else{
			
			ResultVo result = new ResultVo();
			
			result.setSuccess(false);
			result.setErrorMsg("不是正在进行中状态");
			return result;
		}
		
		
		
		BattlePeriodStage battlePeriodStage = battlePeriodStageService.
				findOneByBattleIdAndPeriodIdAndIndex(battlePeriodMember.getBattleId(), 
						battlePeriodMember.getPeriodId(), stageIndex);
		Integer passCount = battlePeriodStage.getPassCount();
		
		if(passCount==null){
			passCount = 0;
		}
		
		Integer passRewardBean = battlePeriodStage.getPassRewardBean();
		
		if(passRewardBean==null){
			passRewardBean = 0;
		}
		
		String memberId = battlePeriodMember.getId();
		
		Map<String, Object> data = new HashMap<>();
		
		
		QuestionAnswer questionAnswer = new QuestionAnswer();
		
		questionAnswer.setQuestions(questions);
		questionAnswer.setRightSum(0);
		questionAnswer.setTargetId(memberId+"_"+stageIndex);
		questionAnswer.setType(Integer.parseInt(type));
		questionAnswer.setWrongSum(0);
		questionAnswer.setQuestionCount(questions.length());
		questionAnswer.setQuestionIndex(0);
		
		questionAnswerService.add(questionAnswer);
		
		BattleMemberPaperAnswer battleMemberPaperAnswer = new BattleMemberPaperAnswer();
		battleMemberPaperAnswer.setAddDistance(0);
		battleMemberPaperAnswer.setBattlePeriodMemberId(memberId);
		battleMemberPaperAnswer.setRightSum(0);
		battleMemberPaperAnswer.setStageIndex(stageIndex);
		battleMemberPaperAnswer.setSubLove(0);
		battleMemberPaperAnswer.setWrongSum(0);
		battleMemberPaperAnswer.setStatus(BattleMemberPaperAnswer.FREE_STATUS);
		battleMemberPaperAnswer.setQuestionAnswerId(questionAnswer.getId());
		battleMemberPaperAnswer.setQuestionCount(questions.split(",").length);
		battleMemberPaperAnswer.setAnswerCount(0);
		battleMemberPaperAnswer.setPassCount(passCount);
		battleMemberPaperAnswer.setIsPass(0);
		battleMemberPaperAnswer.setPassRewardBean(passRewardBean);
		battleMemberPaperAnswer.setThisRewardBean(0L);
		battleMemberPaperAnswer.setIsSyncData(0);
		

		battleMemberPaperAnswer.setRightAddScore(battleRoom.getRightAddScore());
		battleMemberPaperAnswer.setWrongSubScore(battleRoom.getWrongSubScore());
		
		battleMemberPaperAnswer.setStartIndex(battlePeriodMember.getProcess());
		
		battleMemberPaperAnswer.setEndIndex(battlePeriodMember.getProcess());
		
		battleMemberPaperAnswer.setIsReceive(0);
		
		battleMemberPaperAnswerService.add(battleMemberPaperAnswer);
		
		ResultVo resultVo = new ResultVo();
		
		data.put("battleMemberPaperAnswerId", battleMemberPaperAnswer.getId());
		
		data.put("stageIndex",stageIndex);
		
		resultVo.setData(data);
		
		resultVo.setSuccess(true);
		
		return resultVo;
		
	}
	
	
	@RequestMapping(value="questionResults")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentAccountResultFilter.class)
	public Object questionResults(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String battleMemberPaperAnswerId = httpServletRequest.getParameter("battleMemberPaperAnswerId");
		
		BattleMemberPaperAnswer battleMemberPaperAnswer = battleMemberPaperAnswerService.findOne(battleMemberPaperAnswerId);
		
		BattlePeriodMember battlePeriodMember = battlePeriodMemberService.findOne(battleMemberPaperAnswer.getBattlePeriodMemberId());
		
		Map<String, Object> data = new HashMap<>();
		
		List<BattleMemberQuestionAnswer> battleMemberQuestionAnswers = battleMemberQuestionAnswerService.findAllByBattleMemberPaperAnswerId(battleMemberPaperAnswerId);
		
		
		if(battlePeriodMember.getStageIndex()>battlePeriodMember.getStageCount()){
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			
			battlePeriodMemberService.update(battlePeriodMember);
			
			
			BattleRoom battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
			
			if(battleRoom.getEndEnable()!=null&&battleRoom.getEndEnable()==1){
				battleRoom.setStatus(BattleRoom.STATUS_END);
				
				battleRoom.setEndType(BattleRoom.CLEARANCE_END_TYPE);
				
				battleRoomService.update(battleRoom);
			}
		}
		
		BattleAccountResult battleAccountResult = sessionManager.getObject(BattleAccountResult.class);
		
		
		
		/*Integer processAll = battlePeriodMember.getProcess();
		
		if(processAll==null){
			processAll = 0;
		}
		
		Integer processThis = battleMemberPaperAnswer.getProcess();
		if(processThis==null){
			processThis = 0;
		}
		
		processAll = processAll+processThis;
		
		battlePeriodMember.setProcess(processAll);
		
		battlePeriodMemberService.update(battlePeriodMember);
		
		*/
		
		data.put("questionAnswers", battleMemberQuestionAnswers);
		
		data.put("memberStatus", battlePeriodMember.getStatus());
		
		data.put("status", battleMemberPaperAnswer.getStatus());
		
		data.put("process", battleMemberPaperAnswer.getProcess());
		
		data.put("rewardBean", battleMemberPaperAnswer.getThisRewardBean());
		
		
		
		accountResultHandleService.answerResult(battleMemberPaperAnswer, battleAccountResult);
		
		data.put("addExp", battleMemberPaperAnswer.getExp());
		
		data.put("allExp", battleAccountResult.getExp());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(data);
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="infoByBattleQuestionId")
	@ResponseBody
	@Transactional
	public Object infoByBattleQuestionId(HttpServletRequest httpServletRequest){
		String battleQuestionId = httpServletRequest.getParameter("battleQuestionId");
		
		BattleQuestion battleQuestion = battleQuestionService.findOne(battleQuestionId);
		
		Question question = questionService.findOne(battleQuestion.getQuestionId());

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("id", question.getId());
		responseData.put("answer", question.getAnswer());
		responseData.put("fillWords", question.getFillWords());
		responseData.put("imgUrl", question.getImgUrl());
		responseData.put("index", question.getIndex());
		responseData.put("instruction", question.getInstruction());
		responseData.put("isImg", question.getIsImg());
		responseData.put("question", question.getQuestion());
		responseData.put("type", question.getType());
		
		if(question.getType()==0){
			List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(battleQuestion.getQuestion());
			responseData.put("options", questionOptions);
		}
		ResultVo resultVo = new ResultVo();
		
		resultVo.setData(responseData);
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	
	
	@RequestMapping(value="info")
	@ResponseBody
	@Transactional
	public Object info(HttpServletRequest httpServletRequest){
		String id = httpServletRequest.getParameter("id");
		
		if(CommonUtil.isEmpty(id)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("id不能为空");
			
			return resultVo;
		}
		
		Question question = questionService.findOne(id);
		
		if(question==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("返回的question为空");
			return resultVo;
		}

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("id", question.getId());
		responseData.put("answer", question.getAnswer());
		responseData.put("fillWords", question.getFillWords());
		responseData.put("imgUrl", question.getImgUrl());
		responseData.put("index", question.getIndex());
		responseData.put("instruction", question.getInstruction());
		responseData.put("isImg", question.getIsImg());
		responseData.put("question", question.getQuestion());
		responseData.put("type", question.getType());
		
		if(question.getType()==0){
			List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(id);
			List<Map<String, Object>> questionOptionsList = new ArrayList<>();
			for(QuestionOption questionOption:questionOptions){
				Map<String, Object> questionOptionsMap = new HashMap<>();
				questionOptionsMap.put("content", questionOption.getContent());
				questionOptionsMap.put("id", questionOption.getId());
				if(questionOption.getId().equals(question.getRightOptionId())){
					questionOptionsMap.put("isRight", 1);
				}else{
					questionOptionsMap.put("isRight", 0);
				}
				questionOptionsList.add(questionOptionsMap);
				
			}
			responseData.put("options", questionOptionsList);
		}
		ResultVo resultVo = new ResultVo();
		
		resultVo.setData(responseData);
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
}
