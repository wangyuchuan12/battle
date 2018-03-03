package com.battle.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleGift;
import com.battle.domain.BattleGiftModel;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleGiftModelService;
import com.battle.service.BattleGiftService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battleGift/")
public class BattleGiftApi {

	@Autowired
	private BattleGiftService battleGiftService;
	
	@Autowired
	private BattleGiftModelService battleGiftModelService;
	
	@Autowired
	private AccountService accountService;
	
	
	@RequestMapping(value="receiveGift")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo receiveGift(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		
		Integer receiveGiftCount = account.getReceiveGiftCount();
		Integer giftCount = account.getGiftCount();
		
		if(giftCount==null){
			giftCount = 0;
		}
		
		if(receiveGiftCount==null){
			receiveGiftCount = 0;
		}
		
		if(giftCount.intValue()==0){
			List<BattleGiftModel> battleGiftModels = battleGiftModelService.findAllByIsDel(0);
			
			for(BattleGiftModel battleGiftModel:battleGiftModels){
				BattleGift battleGift = new BattleGift();
				battleGift.setAccountId(account.getId());
				battleGift.setBeanNum(battleGiftModel.getBeanNum());
				battleGift.setIsReceive(0);
				battleGift.setLevel(battleGiftModel.getLevel());
				battleGift.setLoveNum(battleGiftModel.getLoveNum());
				battleGift.setBeanNum(battleGiftModel.getBeanNum());
				battleGift.setRecieBeanNumCondition(battleGiftModel.getRecieBeanNumCondition());
				battleGift.setRecieLoveNumCondition(battleGiftModel.getRecieLoveNumCondition());
				battleGiftService.add(battleGift);
			}
			
			account.setGiftCount(battleGiftModels.size());
			account.setReceiveGiftCount(0);
		}
		
		giftCount = account.getGiftCount();
		receiveGiftCount = account.getReceiveGiftCount();
		
		if(receiveGiftCount>=giftCount){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setCode(0);
			resultVo.setMsg("今天领取数量已经满足，明天再来吧");
			
			return resultVo;
		}
		
		List<BattleGift> battleGifts = battleGiftService.findAllByAccountIdAndLevelAndIsReceive(account.getId(),receiveGiftCount+1,0);
		
		if(battleGifts==null||battleGifts.size()==0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setCode(1);
			resultVo.setMsg("可领取的数量为空，不能领取");
			return resultVo;
		}
		
		
		
		BattleGift battleGift = battleGifts.get(0);
		

		Integer addLove = battleGift.getLoveNum();
		Integer addBean = battleGift.getBeanNum();
		
		Integer loveNum = account.getLoveLife();
		
		Long beanNum = account.getWisdomCount();
		
		Integer recieBeanNumCondition = battleGift.getRecieBeanNumCondition();
		
		Integer recieLoveNumCondition = battleGift.getRecieLoveNumCondition();
		
		
		
		if(addLove==null){
			addLove = 0;
		}
		
		if(addBean==null){
			addBean = 0;
		}
		
		if(loveNum==null){
			loveNum = 0;
		}
		
		if(beanNum==null){
			beanNum = 0l;
		}
		
		if(recieBeanNumCondition==null){
			recieBeanNumCondition = 0;
		}
		
		if(recieLoveNumCondition==null){
			recieLoveNumCondition = 0;
		}
		
		if(recieLoveNumCondition<loveNum){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setCode(2);
			resultVo.setMsg("不具备爱心的领取条件");
			return resultVo;
		}
		
		if(recieBeanNumCondition<beanNum){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setCode(2);
			resultVo.setMsg("不具备智慧豆的领取条件");
			return resultVo;
		}
		
		account.setReceiveGiftCount(receiveGiftCount+1);
		
		loveNum = loveNum+addLove;
		
		beanNum = beanNum+addBean;
		
		account.setLoveLife(loveNum);
		
		account.setWisdomCount(beanNum.longValue());
		
		accountService.update(account);
		
		battleGift.setIsReceive(1);
		battleGift.setReceiveTime(new DateTime());
		
		battleGiftService.update(battleGift);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		Map<String, Object> data = new HashMap<>();
		data.put("love", addLove);
		data.put("bean", addBean);
		data.put("count", receiveGiftCount+1);
		
		resultVo.setData(data);
		resultVo.setCode(3);
		
		return resultVo;
		
		
		
	}
}
