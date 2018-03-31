package com.battle.manager.action;

import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.battle.domain.Admin;
import com.battle.service.AdminService;


public class BaseController<T> implements FactoryBean<T>{
    @Autowired
    private AdminService adminService;

    public void addErrorAttribues(String errorMessage, String errortitle,
	    String returnUrl, String errorcode, Model m) {
	m.addAttribute("errorMessage", errorMessage);
	m.addAttribute("errortitle", errortitle);
	m.addAttribute("returnUrl", returnUrl);
	m.addAttribute("errorcode", errorcode);
    }

    /**
     * 
     * @param m
     * @param warnDayNum
     *            提前提醒天数
     * @return 如果返回true表示已经到期，false没有到期
     */
    public boolean checkOvertime(Model m, int warnDayNum) {
	Subject subject = SecurityUtils.getSubject();
	String username = (String) subject.getPrincipal();
	Admin admin = adminService.getAdminByName(username);
	DateTime deadlineDatetime = admin.getDeadline();
	Date deadlineDate = new Date(deadlineDatetime.getMillis());
	if (deadlineDate.before(new Date())) {
	    m.addAttribute("isOverTime", true);
	    // m.addAttribute("deadline", DateFormat.format(deadlineDate));
	    return true;
	} else {
	    m.addAttribute("isOverTime", false);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_MONTH, warnDayNum);
	    if (deadlineDate.before(calendar.getTime())) {
		m.addAttribute("isWareTime", true);
	    } else {
		m.addAttribute("isWareTime", false);
	    }
	    // m.addAttribute("deadline", DateFormat.format(deadlineDate));
	    return false;
	}
    }

    @Override
    public T getObject() throws Exception {
	System.out.println("getObject......................................");
	return (T)this;
    }

    @Override
    public Class<?> getObjectType() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isSingleton() {
	// TODO Auto-generated method stub
	return true;
    }
    
}
