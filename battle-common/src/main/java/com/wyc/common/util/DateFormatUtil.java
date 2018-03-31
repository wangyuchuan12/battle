package com.wyc.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatUtil {

    /**
     * @Function 给密码设置指定的过期时长
     * @param duetime
     * @author ZhangLei
     * @serialData 2014年12月31日
     * @return 一个需要的格式的日期
     */
    public static Timestamp getUpMothDate(int duetime) {

	Date date = null;// 获得系统时间.
	Calendar lastDate = Calendar.getInstance();
	lastDate.add(Calendar.MONTH, duetime);
	// String date_str= lastDate.getTime().toString();
	// java.sql.Date date=new java.sql.Date(lastDate.getTime());
	// Date date_str1= new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_str);
	// timesstamp=new java.sql.Timestamp(lastDate.getTime());
	// date=(Date) lastDate.getTime();

	// 将时间格式转换成符合Timestamp要求的String格式.
	String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(lastDate.getTime());

	// 把String字符串格式的时间转换成Timestamp格式；
	Timestamp overdate = Timestamp.valueOf(nowTime);
	return overdate;
    }
    /**
     * 
     * @param duetime
     * @return
     */
    // public static int validateTime () {
    // // 获取当前登录信息
    // Subject subject = SecurityUtils.getSubject();
    // String admin_login_userName = (String) subject.getPrincipal();
    // Admin admin_login =adminService.getAdminByName(admin_login_userName);
    // System.out.println("******************"+admin_login);
    // System.out.println("******************"+admin_login.getOver_date());
    // Timestamp tamp=admin_login.getOver_date();
    // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    // int day=(int)(tamp.getTime()-timestamp.getTime())/(1000*24*60*60);
    // return day;
    // }
}
