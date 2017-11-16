package com.battle.api;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.other.PayService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.TakeoutAmountEntry;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.domain.vo.TransfersResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.TakeoutAmountEntryService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/common/amount")
public class AmountApi {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TakeoutAmountEntryService takeoutAmountEntryService;
	
	@Autowired
	private PayService payService;
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="takeoutEnties")
	public ResultVo takeoutEnties(HttpServletRequest httpServletRequest)throws Exception{
		List<TakeoutAmountEntry> takeoutAmountEntries = takeoutAmountEntryService.findAllByIsDisplay(1);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(takeoutAmountEntries);
		return resultVo;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="takeout")
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo takeout(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String entryId = httpServletRequest.getParameter("entryId");
		
		TakeoutAmountEntry takeoutAmountEntry = takeoutAmountEntryService.findOne(entryId);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		
		BigDecimal amount = account.getAmountBalance();
		if(amount==null){
			amount = new BigDecimal("0");
		}
		
		if(amount.compareTo(takeoutAmountEntry.getAmount())<0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("余额不足");
			return resultVo;
		}
		
		TransfersResultVo transfersResultVo = payService.transfers(userInfo.getOpenid(), takeoutAmountEntry.getAmount(), "10.2.3.10", "提现");
		
		
		if(transfersResultVo.getResultCode().equalsIgnoreCase("SUCCESS")){
			amount = amount.subtract(takeoutAmountEntry.getAmount());
			account.setAmountBalance(amount);
			accountService.update(account);
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setData(transfersResultVo);
			return resultVo;
		}
	}
}
