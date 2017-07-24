package com.xzy.qcsl.jp.bj.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangzy
 * @date 2015-7-2
 *
 */
public class PolicyEditModel {
	private String status;

	private List<PolicyField> fields = new ArrayList<PolicyField>();

	public List<PolicyField> getFields() {
		return fields;
	}

	public void setFields(List<PolicyField> fields) {
		this.fields = fields;
	}
	
	

	public void addField(String name,String value,boolean disabled){
		this.fields.add(new PolicyField(name, value,disabled));
	}
	
	/*
	 * 有可能有多个相同name的field， 返回第一个
	 */
	public String getFieldValue(String name){
		for (PolicyField f : fields) {
			if(name.equals(f.getName())){
				return f.getValue();
			}
		}
		return "";
		
	}
	
	public void setFieldValue(String name,String value){
		for (PolicyField f : fields) {
			if(name.equals(f.getName())){
				f.setValue(value);
				return;
			}
		}
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
