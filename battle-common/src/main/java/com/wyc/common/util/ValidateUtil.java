package com.wyc.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
    public static boolean validateEmail(String email) {
	if (email == null || email.trim().equals("")) {
	    return false;
	}
	String pat = "[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com))";
	Pattern p = Pattern.compile(pat);
	Matcher match = p.matcher(email);
	return match.matches();
    }
}
