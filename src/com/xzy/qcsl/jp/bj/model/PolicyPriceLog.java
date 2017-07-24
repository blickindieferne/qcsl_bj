package com.xzy.qcsl.jp.bj.model;

import java.util.Date;

/**
 * @author xiangzy
 * @date 2015-6-27
 * 
 * 政策价钱被自动修改的日志
 *
 */
public class PolicyPriceLog {
	private Date date;//修改时间
	
//	返点值修改范围是 0 至 99，百分比
//	残留值修改范围是 -999 至 9999
	
	private int oldCpcReturnPoint; //cpc返点旧值
	private int newCpcReturnPrice; //cpc返点新值
	private int oldCpaReturnPoint; //cpa返点旧值
	private int newCpaReturnPrice; //cpa返点新值
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getOldCpcReturnPoint() {
		return oldCpcReturnPoint;
	}
	public void setOldCpcReturnPoint(int oldCpcReturnPoint) {
		this.oldCpcReturnPoint = oldCpcReturnPoint;
	}
	public int getNewCpcReturnPrice() {
		return newCpcReturnPrice;
	}
	public void setNewCpcReturnPrice(int newCpcReturnPrice) {
		this.newCpcReturnPrice = newCpcReturnPrice;
	}
	public int getOldCpaReturnPoint() {
		return oldCpaReturnPoint;
	}
	public void setOldCpaReturnPoint(int oldCpaReturnPoint) {
		this.oldCpaReturnPoint = oldCpaReturnPoint;
	}
	public int getNewCpaReturnPrice() {
		return newCpaReturnPrice;
	}
	public void setNewCpaReturnPrice(int newCpaReturnPrice) {
		this.newCpaReturnPrice = newCpaReturnPrice;
	}


}
