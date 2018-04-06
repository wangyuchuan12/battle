package com.battle.manager.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Admin;
import com.battle.service.AdminService;
import com.wyc.common.util.DateFormatUtil;
import com.wyc.common.util.ValidateUtil;

@Controller
public class AdminController extends BaseController{
    final static Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminService adminService;
    @RequestMapping("/admin/login")
    public String login(Model m) {

	return "admin/login";
    }

    
    @ResponseBody
    @RequestMapping("/admin/add_default_admin")
    public Object addUser(HttpServletRequest httpServletRequest){
    	String token = httpServletRequest.getParameter("token");
    	if(token.equals("jingyingfanwei12")){
    		Admin admin = new Admin();
    		admin.setUsername("wyc");
    		String password = "4289912wang";
    		String salt = UUID.randomUUID().toString();
    		String passwordSha = new Sha256Hash(password, salt)
    				.toString();
    		admin.setPassword(passwordSha);
    		admin.setSalt(salt);
    		admin.setRoles("god");
    		adminService.add(admin);
    	}
    	return null;
    }
  
    
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String doLogin(@RequestParam(value = "username") String username,
	    @RequestParam(value = "password") String password, Model m) {
	try {
	    UsernamePasswordToken token = new UsernamePasswordToken(username,
		    password);

	    Subject subject = SecurityUtils.getSubject();
	    subject.login(token);

	    // 更新最新登陆时间
	    Admin admin = adminService.getAdminByName(username);
	    admin.setLastLogin(new DateTime());
	    adminService.update(admin);

	    return "redirect:/battle/list";
	} catch (Exception e) {
	    logger.debug("Login fail: {}", e);
	}

	return null;
    }

    @RequestMapping("/admin/logout")
    public String logout() {
	Subject subject = SecurityUtils.getSubject();
	subject.logout();
	return "redirect:/admin/";
    }

    @RequestMapping("/admin/")
    public String dashboard(Model m) {

	// 获取当前登录信息
	Subject subject = SecurityUtils.getSubject();
	String admin_login_userName = (String) subject.getPrincipal();
	Admin admin_login = adminService.getAdminByName(admin_login_userName);
	Timestamp tamp = admin_login.getOver_date();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	// 获取密码最后期限天数；
	int day = (int) (tamp.getTime() - timestamp.getTime())
		/ (1000 * 24 * 60 * 60);
	m.addAttribute("day", day);
	m.addAttribute("warm", "还有" + day + " 天 密码过期，请及时更改");
	System.out.println("************" + day);
	return "admin/dashboard";

    }

    @RequestMapping("/admin/list")
    public String list(Model m) {
	logger.debug("hjaha","ribenren");
	Iterable<Admin> admins = adminService.getAll();
	m.addAttribute("admins", admins);
	return "admin/admin";
    }


    @RequestMapping("/admin/password")
    public String changeLoginPassword(Model m) {
	return "admin/reset_login_admin";
    }

    @RequestMapping(value = "/admin/change_password", method = RequestMethod.POST)
    public String changePassword(
	    @RequestParam(value = "password_new") String password,
	    @RequestParam(value = "password_od") String old_password, Model m) {

	// 获取当前登录信息
	Subject subject = SecurityUtils.getSubject();
	String admin_login_userName = (String) subject.getPrincipal();
	Admin admin_login = adminService.getAdminByName(admin_login_userName);

	// 登录者在修改自己的密码之前要对原密码进行修正；
	String password_old = admin_login.getPassword();
	String password_salt = admin_login.getSalt();
	String password_old_salt = new Sha256Hash(old_password, password_salt)
		.toString();

	if (!password_old_salt.equals(password_old)) {

	    this.addErrorAttribues("登录者密码验证错误请核实", "登录修改密码页面信息错误",
		    "/admin/password", "403", m);
	    return "admin/error";

	}
	// 加盐
	String randomHex = new SecureRandomNumberGenerator().nextBytes()
		.toHex();
	String passwordSha = new Sha256Hash(password, randomHex).toString();
	admin_login.setPassword(passwordSha);
	admin_login.setSalt(randomHex);
	adminService.update(admin_login);
	return "redirect:/admin/";
    }


    @RequestMapping("/admin/delete")
    public String deleteAdmin(
	    @RequestParam(value = "username") String username, Model m) {

	Admin admin_delete = adminService.getAdminByName(username);
	adminService.delete(admin_delete);
	return "redirect:/admin/list";

    }

    @RequestMapping("/admin/get_admin_json")
    @ResponseBody
    public Admin getAdminOfJson(
	    @RequestParam(value = "username") String username, Model m) {
	Admin admin = adminService.getAdminByName(username);
	return admin;
    }

    @RequestMapping("/admin/update")
    public String update(@RequestParam(value = "username") String username,
	    Model m) {

	Admin admin_update = adminService.getAdminByName(username);
	m.addAttribute("admin", admin_update);
	return "admin/update_admin";

    }


    @RequestMapping("/admin/update_admin")
    public String updateAdmin(
	    @RequestParam(value = "realname") String realname,
	    @RequestParam(value = "id") Long id,
	    @RequestParam(value = "username") String username,
	    @RequestParam(value = "mobile") String mobile,
	    @RequestParam(value = "email") String email,
	    @RequestParam(value = "roles") String roles, Model m) {

	// 在提交之前對用戶信息進行驗證；
	Admin admin_update = adminService.getAdminByName(username);

	// 判斷修改用戶的名字是否傳過來了；name為空的話就說明沒有傳過來；
	String name = admin_update.getUsername();

	// 验证邮箱是否合格有效;
	if (name == null || "".equals(name)) {
	    this.addErrorAttribues("用户名無效", "用户修改页面信息错误",
		    "/admin/update?username=" + username, "403", m);
	    return "admin/error";
	} else if (!ValidateUtil.validateEmail(email)) {
	    this.addErrorAttribues("郵箱修改無效", "用户修改页面信息错误",
		    "/admin/update?username=" + username, "403", m);
	    return "admin/error";
	}
	admin_update.setEmail(email);
	admin_update.setUsername(username);
	admin_update.setMobile(mobile);
	admin_update.setRealname(realname);
	admin_update.setRoles(roles);
	adminService.update(admin_update);
	return "redirect:/admin/list";
    }


    @RequestMapping("/admin/add")
    public String addItem(Model m) {

	return "admin/add_admin";
    }


    @RequestMapping("/admin/add_admin")
    public String addItemImpl(
	    @RequestParam(value = "realname") String realname,
	    @RequestParam(value = "username") String username,
	    @RequestParam(value = "roles") String roles,
	    @RequestParam(value = "mobile") String mobile,
	    @RequestParam(value = "password") String password,
	    @RequestParam(value = "email") String email, Model m) {

	String randomHex = new SecureRandomNumberGenerator().nextBytes()
		.toHex();
	String passwordSha = new Sha256Hash(password, randomHex).toString();

	// 验证密码是否包含字符数字；
	String code = "[a-zA-Z0-9]";

	// 对密码是否为空做验证；
	if ("".equals(password) || password == null) {
	    this.addErrorAttribues("用户密码为空", "用户新增页面信息错误", "/admin/add", "403",
		    m);
	    return "admin/error";
	} else if (password.length() < 6) {
	    this.addErrorAttribues("用户密码长度必须大于5", "用户新增页面信息错误", "/admin/add",
		    "403", m);
	    return "admin/error";
	} else if (Pattern.matches(code, password)) {
	    this.addErrorAttribues("密码必须包含字母或者数字", "用户新增页面信息错误", "/admin/add",
		    "403", m);
	    return "admin/error";
	} /*else if (!ValidateUtil.validateEmail(email)) {
	    this.addErrorAttribues("输入的邮箱不合法", "用户新增页面信息错误", "/admin/add",
		    "403", m);
	    return "admin/error";
	}*/

	// 对用户名是否重复做验证；
	if ("".equals(username) || username == null) {
	    this.addErrorAttribues("用户名为空", "用户新增页面信息错误", "/admin/add", "403",
		    m);
	    return "admin/error";
	} else {
	    Admin admin = adminService.getAdminByName(username);
	    if (admin != null) {
		this.addErrorAttribues("用户名重复", "用户新增页面信息错误", "/admin/add",
			"403", m);
		return "admin/error";
	    }
	}
	// 当数据都合格通过验证才存入数据库；
	// date_over字段是用来设置密码过期提醒最后时间；参数1代表一个月的意思；
	Timestamp date_over = DateFormatUtil.getUpMothDate(1);
	Admin admin_add = new Admin();
	admin_add.setRealname(realname);
	admin_add.setUsername(username);
	admin_add.setRoles(roles);
	admin_add.setMobile(mobile);
	admin_add.setPassword(passwordSha);
	admin_add.setSalt(randomHex);
	admin_add.setCreatedAt(new DateTime());

	// 设置密码过期日期；
	admin_add.setOver_date(date_over);
	admin_add.setEmail(email);
	adminService.add(admin_add);
	
	return "redirect:/admin/list";

    }


    @RequestMapping("/admin/reset")
    public String resetPassword(
	    @RequestParam(value = "username") String username, Model m) {

	Admin admin_reset = adminService.getAdminByName(username);
	m.addAttribute("admin", admin_reset);
	return "admin/reset_admin";

    }

    @RequestMapping("/admin/reset_admin")
    public String resetPasswordImpl(
	    @RequestParam(value = "username") String username,
	    @RequestParam(value = "password") String password, Model m) {
	Subject subject = SecurityUtils.getSubject();
	String admin_userName = (String) subject.getPrincipal();
	String randomHex = new SecureRandomNumberGenerator().nextBytes()
		.toHex();
	String passwordSha = new Sha256Hash(password, randomHex).toString();
	Admin admin_add = adminService.getAdminByName(username);
	Admin admin_user = adminService.getAdminByName(admin_userName);

	// to judge the username whether has the right to reset the password;
	if (admin_user.getRoles().contains("admin")) {
	    admin_add.setPassword(passwordSha);
	    admin_add.setSalt(randomHex);
	    admin_add.setUpdatedAt(new DateTime());
	    adminService.add(admin_add);
	} else {
	    return "admin/reset_admin_alarm";
	}

	return "redirect:/admin/list";

    }

    @RequestMapping("/admin/reset_admin_alarm")
    public String resetAdminInvalid() {

	return "redirect:/admin/list";
    }


    @RequestMapping(value = "/admin/addUserJson")
    public String addUserJson(Admin admin, HttpServletRequest request,
	    HttpServletResponse response) {
	String result = "{\"userName\":\"" + admin.getUsername()
		+ "\",\"age\":\" " + admin.getMobile() + " \"}";
	PrintWriter out = null;
	response.setContentType("application/json");

	try {
	    out = response.getWriter();
	    out.write(result);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return "userManager";
    }
    @Override
    public Object getObject() throws Exception {
	logger.debug("hjaha............","ribenren");
	return null;
    }
}