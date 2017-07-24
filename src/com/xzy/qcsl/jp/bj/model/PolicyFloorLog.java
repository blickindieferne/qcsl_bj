package com.xzy.qcsl.jp.bj.model;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * @author xiangzy
 * @date 2015-6-27
 * 
 * 政策底价被修改的日志
 *
 */
public class PolicyFloorLog {

	private String id;
	private String policyFloorId;//底价编号
	private Timestamp date;//修改时间
	private String username;//修改者
	private int action; //0 新增， 1修改
	private String oldValue;//旧值
	private String newValue;//新值
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPolicyFloorId() {
		return policyFloorId;
	}
	public void setPolicyFloorId(String policyFloorId) {
		this.policyFloorId = policyFloorId;
	}
	

}
