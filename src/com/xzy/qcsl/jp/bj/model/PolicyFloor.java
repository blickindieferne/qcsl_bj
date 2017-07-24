package com.xzy.qcsl.jp.bj.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangzy
 * @date 2015-6-27
 *
 *政策底价配置
 */
public class PolicyFloor {
	
	private String id;
	
	private String policyId; //通过政策id管理 

	private int floorPrice;//底价,0代表没有设置
	
	private boolean needSetCPA;//是否需要设置CPA
	 
	private boolean needSetCPC;//是否需要设置CPC
	
	private int under; //比同行低多少
	
	private boolean status;
	
	private boolean isPriceChanged;//价格是否被自动修改过
	
	private String lastUsername;//最后一个修改者，冗余字段
	
	private String ptype; //政策父类型，冗余字段
	
	private int floorPoint;// 底价（百分比)
	
	private int type;//底价类型，0底价 1百分比
	
	private boolean compareCabin;//是否比较舱位
	
	private int cpaDcpc;//cpa,cpc差价，正数表示cpc高于cpa

	public boolean isCompareCabin() {
		return compareCabin;
	}

	public void setCompareCabin(boolean compareCabin) {
		this.compareCabin = compareCabin;
	}

	private  List<PolicyFloorLog> policyFloorPriceLogs = new ArrayList<PolicyFloorLog>();

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getFloorPrice() {
		return floorPrice;
	}

	public void setFloorPrice(int floorPrice) {
		this.floorPrice = floorPrice;
	}

	public boolean isNeedSetCPA() {
		return needSetCPA;
	}

	public void setNeedSetCPA(boolean needSetCPA) {
		this.needSetCPA = needSetCPA;
	}

	public boolean isNeedSetCPC() {
		return needSetCPC;
	}

	public void setNeedSetCPC(boolean needSetCPC) {
		this.needSetCPC = needSetCPC;
	}

	public boolean isPriceChanged() {
		return isPriceChanged;
	}

	public void setPriceChanged(boolean isPriceChanged) {
		this.isPriceChanged = isPriceChanged;
	}

	public List<PolicyFloorLog> getPolicyFloorPriceLogs() {
		return policyFloorPriceLogs;
	}

	public void setPolicyFloorPriceLogs(
			List<PolicyFloorLog> policyFloorPriceLogs) {
		this.policyFloorPriceLogs = policyFloorPriceLogs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public int getUnder() {
		return under;
	}

	public void setUnder(int under) {
		this.under = under;
	}

	public String getLastUsername() {
		return lastUsername;
	}

	public void setLastUsername(String lastUsername) {
		this.lastUsername = lastUsername;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public int getFloorPoint() {
		return floorPoint;
	}

	public void setFloorPoint(int floorPoint) {
		this.floorPoint = floorPoint;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCpaDcpc() {
		return cpaDcpc;
	}

	public void setCpaDcpc(int cpaDcpc) {
		this.cpaDcpc = cpaDcpc;
	}
	
	
	
	
}
