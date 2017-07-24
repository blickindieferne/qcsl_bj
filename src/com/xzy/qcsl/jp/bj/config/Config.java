/**
 * 版权所有 2013 成都子非鱼软件有限公司 保留所有权利
 * 1 项目签约客户只拥有对项目业务代码的所有权，以及在本项目范围内使用平台框架
 * 2 平台框架及相关代码属子非鱼软件有限公司所有，未经授权不得扩散、二次开发及用于其它项目
 */
package com.xzy.qcsl.jp.bj.config;

import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @author xiangzy
 * @date 2014-12-25
 * 
 */
public class Config {

	private static final Logger logger = Logger.getLogger(Config.class);
	private static Properties prop = new Properties();
	

	private static String qunaerPolicyListUrl;
	
	private static String qunaerPolicyViewUrl;
	private static String qunaerPolicyEditUrl;
	
	private static String qunaerBJQueryUrl;
	
	private static String qunaerSavePolicyUrl;
	
	 
	static {
		
		try {
			prop.load(Config.class.getResourceAsStream("/config.properties"));
	
			
			qunaerPolicyListUrl = prop.getProperty("qunaer.policyList.url");
			
			qunaerPolicyEditUrl = prop.getProperty("qunaer.policyEdit.url");
			qunaerPolicyViewUrl = prop.getProperty("qunaer.policyView.url");
			
			qunaerBJQueryUrl = prop.getProperty("qunaer.policyBJQuery.url");
			
			qunaerSavePolicyUrl = prop.getProperty("qunaer.savePolicy.url");
			
//			wmd.trade.qunar.com
//			hxs.trade.qunar.com
			
			

		} catch (Exception e) {
			throw new RuntimeException("读取配置config.properties文件失败",e);
		}
	}
	
	public static String getConfig(String key){
		return prop.getProperty(key);
	}


	public static String getConfig(String key,String defaultValue){
		return prop.getProperty(key,defaultValue);
	}


	public static String getQunaerPolicyListUrl(String domain) {
		return qunaerPolicyListUrl.replace("${domain}", domain);
	}


	public static String getQunaerPolicyViewUrl(String domain) {
		return qunaerPolicyViewUrl.replace("${domain}", domain);
	}


	public static String getQunaerPolicyEditUrl(String domain) {
		return qunaerPolicyEditUrl.replace("${domain}", domain);
	}


	public static String getQunaerBJQueryUrl(String domain) {
		return qunaerBJQueryUrl.replace("${domain}", domain);
	}


	public static String getQunaerSavePolicyUrl() {
		return qunaerSavePolicyUrl;
	}

	


}
