package com.battle.manager.api;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.BattleSubject;
import com.battle.service.BattleSubjectService;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping("/api/battle/subject")
public class BattleSubjectApi {

	@Autowired
	private BattleSubjectService battleSubjectService;
	
	
	@RequestMapping("/info")
	@ResponseBody
	public Object info(HttpServletRequest httpServletRequest){
		
		String id = httpServletRequest.getParameter("id");
		
		BattleSubject battleSubject = battleSubjectService.findOne(id);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleSubject);
		
		return resultVo;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(HttpServletRequest httpServletRequest){
		
		String id = httpServletRequest.getParameter("id");
		
		String name = httpServletRequest.getParameter("name");
		
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		
		String seq = httpServletRequest.getParameter("seq");
		
		BattleSubject battleSubject = battleSubjectService.findOne(id);
		
		battleSubject.setName(name);
		
		battleSubject.setImgUrl(imgUrl);
		
		battleSubject.setSeq(Integer.parseInt(seq));
		
		battleSubjectService.update(battleSubject);
		
		ResultVo resultVo  = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleSubject);
		return resultVo;
	}
	
	
	@RequestMapping("/add")
	@ResponseBody
	public Object add(HttpServletRequest httpServletRequest){
		
		String name = httpServletRequest.getParameter("name");
		String battleId = httpServletRequest.getParameter("battleId");
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		
		String seq  = httpServletRequest.getParameter("seq");
		
		BattleSubject battleSubject = new BattleSubject();
		battleSubject.setBattleId(battleId);
		battleSubject.setImgUrl(imgUrl);
		battleSubject.setName(name);
		battleSubject.setIsDel(0);
		battleSubject.setSeq(Integer.parseInt(seq));
		battleSubjectService.add(battleSubject);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleSubject);
		
		return resultVo;
	}
	
	
	@RequestMapping("/del")
	@ResponseBody
	public Object del(HttpServletRequest httpServletRequest){
		
		String id = httpServletRequest.getParameter("id");
		
		BattleSubject battleSubject = battleSubjectService.findOne(id);
		
		battleSubject.setIsDel(1);
		
		battleSubjectService.update(battleSubject);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
}
