package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.battle.domain.Battle;
import com.battle.domain.BattleQuestionFactoryItem;
import com.battle.domain.BattleQuestionReview;
import com.battle.domain.BattleSubject;
import com.battle.domain.Context;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleQuestionFactoryItemService;
import com.battle.service.BattleQuestionReviewService;
import com.battle.service.BattleService;
import com.battle.service.BattleSubjectService;
import com.battle.service.ContextService;
import com.battle.service.other.QuestionAuditService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battleFactory/")
public class BattleFactoryApi {
	
	@Autowired
	private ContextService contextService;
	
	@Autowired
	private BattleQuestionFactoryItemService battleQuestionFactoryItemService;
	
	@Autowired
	private WxUserInfoService userInfoService;
	
	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleQuestionReviewService battleQuestionReviewService;
	
	@Autowired
	private QuestionAuditService questionAuditService;

	@RequestMapping(value="apply")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object apply(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		String question = httpServletRequest.getParameter("question");
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		String answer = httpServletRequest.getParameter("answer");
		String fillWords = httpServletRequest.getParameter("fillWords");
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		String subjectId = httpServletRequest.getParameter("subjectId");
		
		String type = httpServletRequest.getParameter("type");
		
		String options = httpServletRequest.getParameter("options");
		
		Context context = contextService.findOneByCodeBySync(Context.QUESTION_MAX_INDEX_CODE);
		if(context==null){
			context = new Context();
			context.setCode(Context.QUESTION_MAX_INDEX_CODE);
			context.setValue("1");
			contextService.add(context);
		}else{
			String value = context.getValue();
			Integer index = Integer.parseInt(value);
			index++;
			context.setValue(index+"");
			contextService.update(context);
		}
		
		BattleQuestionFactoryItem questionTarget = new BattleQuestionFactoryItem();
		questionTarget.setQuestion(question);
		questionTarget.setImgUrl(imgUrl);
		questionTarget.setIsImg(1);
		questionTarget.setAnswer(answer);
		questionTarget.setFillWords(fillWords);
		questionTarget.setIsDel(0);
		questionTarget.setStatus(BattleQuestionFactoryItem.STATUS_AUDIT);
		questionTarget.setOptions(options);
		questionTarget.setBattleId(battleId);
		questionTarget.setBattleSubjectId(subjectId);
		questionTarget.setType(Integer.parseInt(type));
		
		questionTarget.setUserId(userInfo.getId());
		
		battleQuestionFactoryItemService.add(questionTarget);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	@RequestMapping(value="agree")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo agree(HttpServletRequest httpServletRequest)throws Exception{
		String itemId = httpServletRequest.getParameter("itemId");
		String reviewId = httpServletRequest.getParameter("reviewId");
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleQuestionFactoryItem battleQuestionFactoryItem = battleQuestionFactoryItemService.findOne(itemId);
		
		BattleQuestionReview battleQuestionReview = battleQuestionReviewService.findOne(reviewId);
		questionAuditService.passAudit(battleQuestionFactoryItem, battleQuestionReview, userInfo.getId());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	@RequestMapping(value="reject")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo reject(HttpServletRequest httpServletRequest)throws Exception{
		String itemId = httpServletRequest.getParameter("itemId");
		BattleQuestionFactoryItem battleQuestionFactoryItem = battleQuestionFactoryItemService.findOne(itemId);
		questionAuditService.rejectAudti(battleQuestionFactoryItem);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	
	@RequestMapping(value="randomAuditQuestion")
	@ResponseBody
	//@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo randomAuditQuestion(HttpServletRequest httpServletRequest)throws Exception{
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		if(CommonUtil.isEmpty(battleId)){
			battleId = "2";
		}
		
		int status = BattleQuestionFactoryItem.STATUS_AUDIT;
		Pageable pageable = new PageRequest(0, 1);
		List<BattleQuestionFactoryItem> battleQuestionFactoryItems = battleQuestionFactoryItemService.findAllByBattleIdAndStatusRandom(battleId,status,pageable);
	
		if(battleQuestionFactoryItems!=null&&battleQuestionFactoryItems.size()>0){
			BattleQuestionFactoryItem battleQuestionFactoryItem = battleQuestionFactoryItems.get(0);
			
			List<BattleQuestionReview> battleQuestionReviews = battleQuestionReviewService.findAllByBattleIdAndBattleSubjectId(battleId,battleQuestionFactoryItem.getBattleSubjectId());
			
			if(battleQuestionReviews==null||battleQuestionReviews.size()==0){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("battleQuestionReviews不能为空");
				return resultVo;
			}
			
			BattleQuestionReview battleQuestionReview = battleQuestionReviews.get(0);
			int rewardBean = battleQuestionReview.getRewardBean();
			String stageIndexes = battleQuestionReview.getStageIndexes();
			String answer = battleQuestionFactoryItem.getAnswer();
			String userId = battleQuestionFactoryItem.getUserId();
			String battleSubjectId = battleQuestionFactoryItem.getBattleSubjectId();
			String fillWords = battleQuestionFactoryItem.getFillWords();
			String id = battleQuestionFactoryItem.getId();
			String imgUrl = battleQuestionFactoryItem.getImgUrl();
			String options = battleQuestionFactoryItem.getOptions();
			String periodId = battleQuestionFactoryItem.getPeriodId();
			String question = battleQuestionFactoryItem.getQuestion();
			int type = battleQuestionFactoryItem.getType();
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("answer", answer);
			data.put("fillWords", fillWords);
			data.put("id", id);
			data.put("imgUrl", imgUrl);
			data.put("options", options);
			data.put("periodId", periodId);
			data.put("question", question);
			data.put("type", type);
			data.put("rewardBean", rewardBean);
			data.put("stageIndexes", stageIndexes);
			data.put("reviewId", battleQuestionReview.getId());
			UserInfo userInfo = null;
			if(CommonUtil.isNotEmpty(userId)){
				userInfo = userInfoService.findOne(userId);
			}
			BattleSubject battleSubject = battleSubjectService.findOne(battleSubjectId);
			Battle battle = battleService.findOne(battleId);
			
			if(battle!=null){
				data.put("battleName", battle.getName());
			}
			
			if(userInfo!=null){
				data.put("auditUsername", userInfo.getNickname());
			}
			
			if(battleSubject!=null){
				data.put("subjectName", battleSubject.getName());
			}
			
			
			
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(data);
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("没有返回记录");
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="myQuestions")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo myQuestions(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		String status = httpServletRequest.getParameter("status");
		
		Integer statusInt = 0;
		
		if(!CommonUtil.isEmpty(status)){
			statusInt = Integer.parseInt(status);
		}
		Sort sort = new Sort(Direction.DESC,"createAt");
		Pageable pageable = new PageRequest(0, 100, sort);
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(statusInt);
		
		List<BattleQuestionFactoryItem> battleQuestionFactoryItems = battleQuestionFactoryItemService.findAllByUserIdAndStatusInAndIsDel(userInfo.getId(),statuses,0,pageable);
	
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleQuestionFactoryItems);
		
		return resultVo;
	}
}
