package com.xzy.qcsl.jp.bj.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangzy
 * @date 2015-6-22
 * 
 */
public class Policy {
	
	private String id; //政策id，列表页跳转到编辑要用

	
	private String policy;// 政策类型0 1 2 ,3,4,5,6
	
	private String ptype;//父政策类型 0普通 1特价 3特殊(预付、申请、包机切位等）

	private String policyStr;// 政策类型显示

	private String friendlyId;// 政策号

	private String policyCode;// 政策代码

	private String status;// 状态

	private String flightcode;// 航空公司代码*

	private String cabin;// 舱位代码

	private String dptArr;// 起飞到达，列表页面显示使用
	private String dpt;// 起飞城市代码*
	private String arr;// 到达城市代码*

	private String flightNumLimit;// 航班限制类型：0全部航班、1适用航班、2不适用航班
	private String flightcondition;//航班号条件：一个或多个航班号

	private String startToEndDate_ticket;// 销售开始-结束日期，列表显示使用
	private String startdate_ticket;// 销售开始日期
	private String enfdate_ticket;// 销售结束日期
	
	private String startToEndDate;// 旅行开始-结束日期 ，列表页显示使用
	private String startdate;// 旅行开始日期
	private String enddate; // 旅行结束日期

	private String sellpriceOrdiscount;// 票面价或Y舱折扣,列表页面显示使用
	private String facePriceType;// 票面价类型
	private String sellprice;// 指定票面价
	private String discount;// Y舱折扣
	
	private String cpaPutInPriceType;//CPA投放类型
	private String cpaPutInNormalPrice;//CPA标价
	private String returnpoint; //cpa返点
	private String returnprice; //cpa留钱
	private String cpcReturnPoint;//cpc返点
	private String cpcReturnprice;//cpc留钱
	
	
	//编辑保存页面需要提交的（上面不包含的）---------
	private String pfid;
	private String domain;
	private String sourceNum;
	
	private String deptTimeSlot;//航班起飞时间
	private String beforeValidDay; //最晚提前出票时间
	private String earliestBeforeValidDay;//最早提前出票时间
	
	private List<String> dayconditions = new ArrayList<String>();//班期限制
	
	private String xcd;//是否提供行程单
	
	private String rtype;//政策返点留钱类型，目前只有一个可选项0

	private String preOfficeNo;//
	private String officeno;//
	private String ticketTime;//
	private String restCabin;//
	private String returnRule;//
	private String changeRule;//
	private String cpcReturnRule;//
	private String cpcChangeRule;//
	private String cpcEndorsement;
	private String act;//

	private String floor;//底价，冗余字段，用于列表显示

	private PolicyFloor policyFloorPrice; // 底价设置

	// 价钱被改动日志（只包括本工具自动修改的部分）
	private List<PolicyPriceLog> policyPriceLogs = new ArrayList<PolicyPriceLog>();

	public PolicyFloor getPolicyFloorPrice() {
		return policyFloorPrice;
	}

	public void setPolicyFloorPrice(PolicyFloor policyFloorPrice) {
		this.policyFloorPrice = policyFloorPrice;
	}

	public List<PolicyPriceLog> getPolicyPriceLogs() {
		return policyPriceLogs;
	}

	public void setPolicyPriceLogs(List<PolicyPriceLog> policyPriceLogs) {
		this.policyPriceLogs = policyPriceLogs;
	}



	public String getPolicyStr() {
		return policyStr;
	}

	public void setPolicyStr(String policyStr) {
		this.policyStr = policyStr;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFriendlyId() {
		return friendlyId;
	}

	public void setFriendlyId(String friendlyId) {
		this.friendlyId = friendlyId;
	}

	public String getPolicyCode() {
		return policyCode;
	}

	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}

	public String getFlightcode() {
		return flightcode;
	}

	public void setFlightcode(String flightcode) {
		this.flightcode = flightcode;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
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

	public String getFlightNumLimit() {
		return flightNumLimit;
	}

	public void setFlightNumLimit(String flightNumLimit) {
		this.flightNumLimit = flightNumLimit;
	}

	public String getStartdate_ticket() {
		return startdate_ticket;
	}

	public void setStartdate_ticket(String startdate_ticket) {
		this.startdate_ticket = startdate_ticket;
	}

	public String getEnfdate_ticket() {
		return enfdate_ticket;
	}

	public void setEnfdate_ticket(String enfdate_ticket) {
		this.enfdate_ticket = enfdate_ticket;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getSellpriceOrdiscount() {
		return sellpriceOrdiscount;
	}

	public void setSellpriceOrdiscount(String sellpriceOrdiscount) {
		this.sellpriceOrdiscount = sellpriceOrdiscount;
	}

	public String getFacePriceType() {
		return facePriceType;
	}

	public void setFacePriceType(String facePriceType) {
		this.facePriceType = facePriceType;
	}

	public String getSellprice() {
		return sellprice;
	}

	public void setSellprice(String sellprice) {
		this.sellprice = sellprice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDptArr() {
		return dptArr;
	}

	public void setDptArr(String dptArr) {
		this.dptArr = dptArr;
	}

	public String getStartToEndDate_ticket() {
		return startToEndDate_ticket;
	}

	public void setStartToEndDate_ticket(String startToEndDate_ticket) {
		this.startToEndDate_ticket = startToEndDate_ticket;
	}

	public String getStartToEndDate() {
		return startToEndDate;
	}

	public void setStartToEndDate(String startToEndDate) {
		this.startToEndDate = startToEndDate; 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getFlightcondition() {
		return flightcondition;
	}

	public void setFlightcondition(String flightcondition) {
		this.flightcondition = flightcondition;
	}

	public String getReturnpoint() {
		return returnpoint;
	}

	public void setReturnpoint(String returnpoint) {
		this.returnpoint = returnpoint;
	}

	public String getReturnprice() {
		return returnprice;
	}

	public void setReturnprice(String returnprice) {
		this.returnprice = returnprice;
	}

	public String getCpcReturnPoint() {
		return cpcReturnPoint;
	}

	public void setCpcReturnPoint(String cpcReturnPoint) {
		this.cpcReturnPoint = cpcReturnPoint;
	}

	public String getCpcReturnprice() {
		return cpcReturnprice;
	}

	public void setCpcReturnprice(String cpcReturnprice) {
		this.cpcReturnprice = cpcReturnprice;
	}

	public String getBeforeValidDay() {
		return beforeValidDay;
	}

	public void setBeforeValidDay(String beforeValidDay) {
		this.beforeValidDay = beforeValidDay;
	}

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(String sourceNum) {
		this.sourceNum = sourceNum;
	}

	public String getDeptTimeSlot() {
		return deptTimeSlot;
	}

	public void setDeptTimeSlot(String deptTimeSlot) {
		this.deptTimeSlot = deptTimeSlot;
	}

	public String getEarliestBeforeValidDay() {
		return earliestBeforeValidDay;
	}

	public void setEarliestBeforeValidDay(String earliestBeforeValidDay) {
		this.earliestBeforeValidDay = earliestBeforeValidDay;
	}

	public List<String> getDayconditions() {
		return dayconditions;
	}

	public void setDayconditions(List<String> dayconditions) {
		this.dayconditions = dayconditions;
	}

	public String getXcd() {
		return xcd;
	}

	public void setXcd(String xcd) {
		this.xcd = xcd;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public String getCpaPutInPriceType() {
		return cpaPutInPriceType;
	}

	public void setCpaPutInPriceType(String cpaPutInPriceType) {
		this.cpaPutInPriceType = cpaPutInPriceType;
	}

	public String getCpaPutInNormalPrice() {
		return cpaPutInNormalPrice;
	}

	public void setCpaPutInNormalPrice(String cpaPutInNormalPrice) {
		this.cpaPutInNormalPrice = cpaPutInNormalPrice;
	}

	public String getPreOfficeNo() {
		return preOfficeNo;
	}

	public void setPreOfficeNo(String preOfficeNo) {
		this.preOfficeNo = preOfficeNo;
	}

	public String getOfficeno() {
		return officeno;
	}

	public void setOfficeno(String officeno) {
		this.officeno = officeno;
	}

	public String getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(String ticketTime) {
		this.ticketTime = ticketTime;
	}

	public String getRestCabin() {
		return restCabin;
	}

	public void setRestCabin(String restCabin) {
		this.restCabin = restCabin;
	}

	public String getReturnRule() {
		return returnRule;
	}

	public void setReturnRule(String returnRule) {
		this.returnRule = returnRule;
	}

	public String getChangeRule() {
		return changeRule;
	}

	public void setChangeRule(String changeRule) {
		this.changeRule = changeRule;
	}

	public String getCpcReturnRule() {
		return cpcReturnRule;
	}

	public void setCpcReturnRule(String cpcReturnRule) {
		this.cpcReturnRule = cpcReturnRule;
	}

	public String getCpcChangeRule() {
		return cpcChangeRule;
	}

	public void setCpcChangeRule(String cpcChangeRule) {
		this.cpcChangeRule = cpcChangeRule;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getCpcEndorsement() {
		return cpcEndorsement;
	}

	public void setCpcEndorsement(String cpcEndorsement) {
		this.cpcEndorsement = cpcEndorsement;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	
}
