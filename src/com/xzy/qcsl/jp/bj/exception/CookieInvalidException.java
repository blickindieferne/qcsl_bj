package com.xzy.qcsl.jp.bj.exception;

/**
 * @author xiangzy
 * @date 2015-7-5
 *
 */
public class CookieInvalidException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private int statusCode;
	
	
	/**
	 * @param url
	 * @param statusCode
	 */
	public CookieInvalidException(String url, int statusCode) {
		super();
		this.url = url;
		this.statusCode = statusCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
