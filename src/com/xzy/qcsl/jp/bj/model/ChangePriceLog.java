package com.xzy.qcsl.jp.bj.model;

import java.sql.Timestamp;



/**
 * @author xiangzy
 * @date 2015-7-6
 * 
 */
public class ChangePriceLog {

	private String id;
	private String policy_id;
	private Timestamp log_date;

	private String old_returnpoint="";//cpa返点
	private String new_returnpoint="";
	private String old_returnprice="";//cpa留钱
	private String new_returnprice="";

	private String old_cpc_returnpoint="";//cpc返点
	private String new_cpc_returnpoint="";
	private String old_cpc_returnprice="";//cpc留钱
	private String new_cpc_returnprice="";
	
	private String user_name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPolicy_id() {
		return policy_id;
	}
	public void setPolicy_id(String policy_id) {
		this.policy_id = policy_id;
	}
	public Timestamp getLog_date() {
		return log_date;
	}
	public void setLog_date(Timestamp log_date) {
		this.log_date = log_date;
	}
	public String getOld_returnpoint() {
		return old_returnpoint;
	}
	public void setOld_returnpoint(String old_returnpoint) {
		this.old_returnpoint = old_returnpoint;
	}
	public String getNew_returnpoint() {
		return new_returnpoint;
	}
	public void setNew_returnpoint(String new_returnpoint) {
		this.new_returnpoint = new_returnpoint;
	}
	public String getOld_returnprice() {
		return old_returnprice;
	}
	public void setOld_returnprice(String old_returnprice) {
		this.old_returnprice = old_returnprice;
	}
	public String getNew_returnprice() {
		return new_returnprice;
	}
	public void setNew_returnprice(String new_returnprice) {
		this.new_returnprice = new_returnprice;
	}
	public String getOld_cpc_returnpoint() {
		return old_cpc_returnpoint;
	}
	public void setOld_cpc_returnpoint(String old_cpc_returnpoint) {
		this.old_cpc_returnpoint = old_cpc_returnpoint;
	}
	public String getNew_cpc_returnpoint() {
		return new_cpc_returnpoint;
	}
	public void setNew_cpc_returnpoint(String new_cpc_returnpoint) {
		this.new_cpc_returnpoint = new_cpc_returnpoint;
	}
	public String getOld_cpc_returnprice() {
		return old_cpc_returnprice;
	}
	public void setOld_cpc_returnprice(String old_cpc_returnprice) {
		this.old_cpc_returnprice = old_cpc_returnprice;
	}
	public String getNew_cpc_returnprice() {
		return new_cpc_returnprice;
	}
	public void setNew_cpc_returnprice(String new_cpc_returnprice) {
		this.new_cpc_returnprice = new_cpc_returnprice;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	
	
	
	
	
}
