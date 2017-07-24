/**
 * 版权所有 2013 成都子非鱼软件有限公司 保留所有权利
 * 1 项目签约客户只拥有对项目业务代码的所有权，以及在本项目范围内使用平台框架
 * 2 平台框架及相关代码属子非鱼软件有限公司所有，未经授权不得扩散、二次开发及用于其它项目
 */
package com.xzy.qcsl.jp.bj.controller;

/**
 * @author xiangzy
 * @date 2013-7-19
 * 
 */
public class ResultData {
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public static final String OPT_ADD = "add";
	public static final String OPT_DELETE = "delete";
	public static final String OPT_UPDATE = "update";
	public static final String OPT_QUERY = "query";
	
	private String code;
	private String info;
	private Object operation;
	private Object data;
	private boolean success;

	public ResultData(String code) {
		this.code = code;
		this.success = (SUCCESS == code);

	}

	public ResultData(String code, String info) {
		this.code = code;
		this.info = info;
		this.success = (SUCCESS == code);
	}
	
	public ResultData(String code, String info, String operation) {
		this.code = code;
		this.info = info;
		this.success = (SUCCESS == code);
		this.operation = operation;
	}

	public ResultData(String code, String info, String operation,Object data) {
		this.code = code;
		this.info = info;
		this.data = data;
		this.success = (SUCCESS == code);
		this.operation = operation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getOperation() {
		return operation;
	}

	public void setOperation(Object operation) {
		this.operation = operation;
	}

}
