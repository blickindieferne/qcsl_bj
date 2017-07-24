package com.xzy.qcsl.jp.bj.model;

/**
 * @author xiangzy
 * @date 2015-7-5
 *
 */
public class YPrice {

	private String id;
	private String flightcode; //航空公司
	private String dpt; //起飞
	private String arr; //到达
	private int price;  //Y舱全价
	public String getFlightcode() {
		return flightcode;
	}
	public void setFlightcode(String flightcode) {
		this.flightcode = flightcode;
	}
	public String getDpt() {
		return dpt;
	}
	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	public String getArr() {
		return arr;
	}
	public void setArr(String arr) {
		this.arr = arr;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getKey(){
		return (this.flightcode+this.dpt+this.arr).toUpperCase();
	}
	

}
