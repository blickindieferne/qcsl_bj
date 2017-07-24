package com.xzy.qcsl.jp.bj.model;

import java.util.List;

/**
 * @author xiangzy
 * @date 2015-7-4
 * 
 *       比价查询返回结果 json
 * 
 */
public class BJQueryResult {

	private boolean ret;
	private boolean hasFlightNo;
	private Data data;
	
	

	public boolean isRet() {
		return ret;
	}



	public void setRet(boolean ret) {
		this.ret = ret;
	}



	public boolean isHasFlightNo() {
		return hasFlightNo;
	}



	public void setHasFlightNo(boolean hasFlightNo) {
		this.hasFlightNo = hasFlightNo;
	}



	public Data getData() {
		return data;
	}



	public void setData(Data data) {
		this.data = data;
	}



	public static class Data {
		private List<Record> orderList;
		
		
		
		public List<Record> getOrderList() {
			return orderList;
		}



		public void setOrderList(List<Record> orderList) {
			this.orderList = orderList;
		}



		public static class Record {

			private String flightNo;
			private String dpt;
			private String arr;
			private String cabin;
			private int sellPrice;
			private String priceType;
			private String domain;
			private boolean tipRed;
			private String flightDate;
			private String policyTypeKey;
			private String pid;
			private boolean showPolicy;
			private boolean limitShowPrice;
			private int selfPrice;
			private boolean hasFlightNo;
			private String shareCode;
			private boolean ttsPrice;

			public String getFlightNo() {
				return flightNo;
			}

			public void setFlightNo(String flightNo) {
				this.flightNo = flightNo;
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

			public String getCabin() {
				return cabin;
			}

			public void setCabin(String cabin) {
				this.cabin = cabin;
			}

			public int getSellPrice() {
				return sellPrice;
			}

			public void setSellPrice(int sellPrice) {
				this.sellPrice = sellPrice;
			}

			public String getPriceType() {
				return priceType;
			}

			public void setPriceType(String priceType) {
				this.priceType = priceType;
			}

			public String getDomain() {
				return domain;
			}

			public void setDomain(String domain) {
				this.domain = domain;
			}

			public boolean isTipRed() {
				return tipRed;
			}

			public void setTipRed(boolean tipRed) {
				this.tipRed = tipRed;
			}

			public String getFlightDate() {
				return flightDate;
			}

			public void setFlightDate(String flightDate) {
				this.flightDate = flightDate;
			}

			public String getPolicyTypeKey() {
				return policyTypeKey;
			}

			public void setPolicyTypeKey(String policyTypeKey) {
				this.policyTypeKey = policyTypeKey;
			}

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public boolean isShowPolicy() {
				return showPolicy;
			}

			public void setShowPolicy(boolean showPolicy) {
				this.showPolicy = showPolicy;
			}

			public boolean isLimitShowPrice() {
				return limitShowPrice;
			}

			public void setLimitShowPrice(boolean limitShowPrice) {
				this.limitShowPrice = limitShowPrice;
			}

			public int getSelfPrice() {
				return selfPrice;
			}

			public void setSelfPrice(int selfPrice) {
				this.selfPrice = selfPrice;
			}

			public boolean isHasFlightNo() {
				return hasFlightNo;
			}

			public void setHasFlightNo(boolean hasFlightNo) {
				this.hasFlightNo = hasFlightNo;
			}

			public String getShareCode() {
				return shareCode;
			}

			public void setShareCode(String shareCode) {
				this.shareCode = shareCode;
			}

			public boolean isTtsPrice() {
				return ttsPrice;
			}

			public void setTtsPrice(boolean ttsPrice) {
				this.ttsPrice = ttsPrice;
			}

		}
	}

}
