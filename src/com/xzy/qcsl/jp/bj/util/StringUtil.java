package com.xzy.qcsl.jp.bj.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


	
	public static final List<String> regs;
	static{
		regs = new ArrayList<String>();
		regs.add(",");
		regs.add("、");
		regs.add("，");
	}
	
	/*特殊字符*/
	public static final char[]  specialChars = new char[]{
		'\\','/',' '
	};
	
	/*非法字符*/
	public static final char[] illegalChars = new char[]{
		'\\','\'','\"'
	};
	
	public static boolean isEmptyOrNull(String str){
		return (str==null || str.trim().equals(""));
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyOrNull(Object obj){
		if(obj == null){
			return true;
		}
		if(obj instanceof Collection){
			return ((Collection)obj).size() == 0;
		}else if (obj instanceof Map){
			return ((Map)obj).size() == 0;	
		} else{
			return obj.toString().trim().equals("");
		}
	}
	
	public static boolean isNotEmptyOrNull(String str){
		return !isEmptyOrNull(str);
	}
	
	public static boolean isNotEmptyOrNull(Object str){
		return !isEmptyOrNull(str);
	}
	
	public static boolean isLong(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static boolean isDouble(String str){
		try{
			Double.parseDouble(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isDoubleOrNull(String str){
		if(isEmptyOrNull(str)){
			return true;
		}
		try{
			Double.parseDouble(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
}
