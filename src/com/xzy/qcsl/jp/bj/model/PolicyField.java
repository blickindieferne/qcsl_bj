package com.xzy.qcsl.jp.bj.model;


/**
 * @author xiangzy
 * @date 2015-7-2
 *
 */
public class PolicyField {

	private String name;
	private String value;
	private boolean disabled;
	
	
	/**
	 * @param name
	 * @param value
	 */
	public PolicyField(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	
	/**
	 * @param name
	 * @param value
	 * @param disable
	 */
	public PolicyField(String name, String value, boolean disabled) {
		super();
		this.name = name;
		this.value = value;
		this.disabled = disabled;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisable(boolean disabled) {
		this.disabled = disabled;
	}
	
	

}
