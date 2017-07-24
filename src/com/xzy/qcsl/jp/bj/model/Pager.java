package com.xzy.qcsl.jp.bj.model;

/**
 * @author xiangzy
 * @date 2015-6-26
 * 
 * 分页相关信息，当前多少页，每页多少条，一共多少页等等
 *
 */
public class Pager {

	private int pageNo=1; //当前页
	
	private int recordCount=10;//每页记录数
	
	private int totalPage=1; //共多少页
	private int totalRecord=0;//共多少条

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	

}
