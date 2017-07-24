package com.xzy.qcsl.jp.bj.task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xzy.qcsl.jp.bj.crawler.Client;
import com.xzy.qcsl.jp.bj.crawler.parse.PolicyEditPage;
import com.xzy.qcsl.jp.bj.model.ChangePriceLog;
import com.xzy.qcsl.jp.bj.model.PolicyEditModel;
import com.xzy.qcsl.jp.bj.model.PolicyFloor;
import com.xzy.qcsl.jp.bj.model.YPrice;
import com.xzy.qcsl.jp.bj.service.PolicyService;

/**
 * @author xiangzy
 * @date 2015-6-29
 * 
 */
public class BJTask implements Runnable {

	private String username;
	private String cookie;
	private String domain;
	private int interval; //每轮任务间隔分
	private int intmin; //每条政策暂停秒数

	private long roundCount = 0;
	private int invalidPolicyCount=0;	//政策失效
	private int noQuerySellPrice = 0; 	//没有查询到可用的同行底价
	private int failedTaskCount=0;		//异常、错误
	private int succedTaskCount=0;		//成功
	private int changedCount=0;	   //修改
	private LinkedList<String> roundTaskLogList = new LinkedList<String>();
	
	private Map<String,Integer> yPriceMap;

	private boolean run;
	private boolean pause;

	private PolicyService policyService = PolicyService.getInstance();
	
	private static final Logger log = Logger.getLogger(BJTask.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Thread thread;
	
	public BJTask(String username,String domain,String cookie,int interval,int intmin) {
		this.username = username;
		this.cookie = cookie;
		this.interval = interval;
		this.domain = domain;
		this.intmin = intmin;
	}
	
	

	public boolean isPause() {
		return pause;
	}



	public boolean isRun() {
		return this.run;
	}

	public void stop() {
		this.run = false;

	}
	


	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getIntmin() {
		return intmin;
	}



	public void setIntmin(int intmin) {
		this.intmin = intmin;
	}



	public boolean start() {
		if(this.run){
			return false;
		}

		this.roundCount = 0;
		//this.roundTaskLogList.clear();

		this.thread = new Thread(this);
		this.thread.start();
		this.run = true;
		return true;
	}

	@Override
	public void run() {
		while (this.run) {
			log.info("==================开始第"+(++roundCount)+"轮比价...");
			this.succedTaskCount=0;
			this.failedTaskCount=0;
			this.changedCount=0;
			this.noQuerySellPrice = 0;
			this.invalidPolicyCount = 0;
			
			List<PolicyFloor> policyFloors = null;
			try {
				policyFloors = policyService.getPolicyFloorListByUser(this.username);
			} catch (Exception e1) {
				this.stop();
				log.error("从数据库获取底价列表失败，任务停止，username:"+ this.username,e1);
				return;
			}
			
			if(policyFloors.size()==0){
				this.stop();
				log.error("没有可用的底价，任务停止");
				this.roundTaskLogList.addFirst("没有可用的底价，任务停止");
				return;
			}
			
			try {
				this.buildYPriceMap();
			} catch (Exception e) {
				this.stop();
				log.error("从数据库获取Y舱全价列表失败，任务停止，username:"+ this.username,e);
				this.stop();
				this.roundTaskLogList.addFirst("从数据库获取Y舱全价列表失败，任务停止，username:"+ this.username+"\n"+e.getMessage());
				return;
			}
			
			roundTask(policyFloors);
			
			String roundTaskLog = sdf.format(new Date())+"， 第"+roundCount+"轮比价结束，成功比价"+succedTaskCount+
					"条，其中改价"+changedCount+"条；失效政策"+invalidPolicyCount+"条；未查询到同行低价"+
					noQuerySellPrice+"条；失败"+failedTaskCount+"条";
			log.info(roundTaskLog);
			if(changedCount>0){
				roundTaskLog = "<span style='color:red'>"+roundTaskLog+"<span/>";
			}
			this.roundTaskLogList.addFirst(roundTaskLog);
			if(this.roundTaskLogList.size()>100){
				this.roundTaskLogList.removeLast();
			}
			
			
			try{
				//TODO 
				Thread.sleep(1000);
				this.pause = true;
				for(int i=0;this.run && i<this.interval*10;i++){
					Thread.sleep(1000*6);//暂停时，每6秒判断一下run
				}
				this.pause = false;
			}catch(Exception e){
				
			}
		}
		this.run = false;
	}
	
	public void roundTask(List<PolicyFloor> policyFloors){

		for (int i = 0; this.run && i < policyFloors.size(); i++) {
			PolicyFloor policyFloor = policyFloors.get(i);
			try{
				int type = policyFloor.getType();
				int floorPrice = policyFloor.getFloorPrice();
				int floorPoint = policyFloor.getFloorPoint();
				
				int under = policyFloor.getUnder();
				log.info((i+1)+" ------------开始政策比价，policyId:"+policyFloor.getPolicyId()
						+"底价类型: "+type
						+" 底价："+floorPrice+"底价百分比: "+floorPoint+" under: "+under
						+(policyFloor.isNeedSetCPA()?" CPA ":"")+(policyFloor.isNeedSetCPC()?" CPC ":""));
				
				if(type==0 && floorPrice<=0){
					log.info("底价设置为0，不做处理");
					continue;
				}
				
				if(type==1 && (floorPoint<=0 || floorPoint>=100)){
					log.info("底价百分比设置为0或100，不做处理");
					continue;
				}
		
				//通过查看页面获得状态
				 // <td class="value state">   删除    </td>
				String viewContext;
				try {
					viewContext = Client.getPolicyViewPage(domain,cookie, policyFloor.getPtype(), policyFloor.getPolicyId());
				} catch (Exception e) {
					log.error("获取查看页面失败，policyId:"+ policyFloor.getPolicyId(),e);
					this.failedTaskCount++;
					continue;
				}
				int statusIndex = viewContext.lastIndexOf("value state");
				if(statusIndex==-1){
					policyFloor.setStatus(false);
					policyService.savePolicyFloor(policyFloor);
					this.invalidPolicyCount++;
					continue;
				}
				String status = viewContext.substring(statusIndex+13);
				status = status.substring(0,status.indexOf("</td>")).trim();
				log.info("政策状态："+status);
				if(!"有效".equals(status)){
					policyFloor.setStatus(false);
					policyService.savePolicyFloor(policyFloor);
					this.invalidPolicyCount++;
					continue;
				}
				
				//通过编辑页面获得政策字段
				String editContext;
				try {
					editContext = Client.getPolicyEditPage(domain,cookie, policyFloor.getPtype(), policyFloor.getPolicyId());
				} catch (Exception e) {
					log.error("获取编辑页面失败，policyId:"+ policyFloor.getPolicyId(),e);
					this.failedTaskCount++;
					continue;
				}
				
				PolicyEditPage page;
				try {
					page = new PolicyEditPage(editContext);
				} catch (Exception e) {
					log.error("解析编辑页面失败，policyId:"+ policyFloor.getPolicyId(),e);
					this.failedTaskCount++;
					continue;
				}
				PolicyEditModel policyEditModel = page.getPolicyEditModel();
				
				//获得同行外放最低价
				int minSellPrice = 0;
				try {
					minSellPrice = Client.getMinSellPrice(domain,cookie, policyEditModel,policyFloor);
				} catch (Exception e) {
					log.error("获取同行外放低价失败，policyId:"+ policyFloor.getPolicyId(),e);
					this.failedTaskCount++;
					continue;
				}
				if(minSellPrice ==0){
					log.info("没有查询到可用的同行外放低价");
					this.noQuerySellPrice++;
					continue;
				}
				log.info("同行外放低价："+minSellPrice);
				
				//自有价
				//当票面价格类型是指定票面价的时候， 自有政策外放价 = 票面价格 ×（1 – 返点%）＋　留钱
				//当票面价格类型是Y舱折扣的时候，   自有政策外放价 = Y舱全价×Y舱折扣×（1 – 返点%）＋　留钱，
				// 其中Y舱全价从Y舱全价维护表格中读取，如果读不到则不做处理
				int sellPrice = this.getSellPrice(policyEditModel);//票面价
				
				if(type==1){
					//底价为百分比类型时，计算底价
					floorPrice = sellPrice * floorPoint/100;
				}
				
				
				if("0".equals(policyEditModel.getFieldValue("facePriceType"))){
					//y舱折扣计算后的价格
					sellPrice = Math.round(sellPrice * Float.parseFloat(policyEditModel.getFieldValue("discount")));
				}
				if(sellPrice==0){
					log.info("没有查询到Y舱全价或者全价为0");
					continue;
				}
				float returnpoint =  Float.parseFloat(policyEditModel.getFieldValue("returnpoint"));
				int returnprice = Integer.parseInt(policyEditModel.getFieldValue("returnprice"));
				float cpcReturnPoint =  Float.parseFloat(policyEditModel.getFieldValue("cpcReturnPoint"));
				int cpcReturnprice = Integer.parseInt(policyEditModel.getFieldValue("cpcReturnprice"));	
				int cpaPrice = Math.round(sellPrice *(1-returnpoint/100) +returnprice);//cpa外放价
				int cpcPrice = Math.round(sellPrice *(1-cpcReturnPoint/100) +cpcReturnprice);//cpc外放价
				log.info("票面价:"+sellPrice+" cpa外放价："+cpaPrice+" cpc外放价："+cpcPrice);
				
				
				ChangePriceLog changePriceLog = new ChangePriceLog();
				changePriceLog.setUser_name(username);
				changePriceLog.setLog_date(new Timestamp(System.currentTimeMillis()));
				changePriceLog.setOld_returnpoint(returnpoint+"");
				changePriceLog.setOld_returnprice(returnprice+"");
				changePriceLog.setOld_cpc_returnpoint(cpcReturnPoint+"");
				changePriceLog.setOld_cpc_returnprice(cpcReturnprice+"");
				
				//同行外放最低价 < 自有政策外放价>预设底价 
				boolean isChanged =false;
				int newPrice = Math.max(floorPrice, minSellPrice-under);//应该设置的价格
				int diffPrice = sellPrice-newPrice;//差价
				//sellPrice ×（1 – returnpoint/100）＋ returnprice  = newPrice
				//返点值修改范围是 0 至 99，百分比
				//残留值修改范围是 -999 至 9999
				boolean cpaChanged = true;
				if(policyFloor.isNeedSetCPA() && 
						(minSellPrice<cpaPrice && cpaPrice>floorPrice
//						||
//						cpaPrice < minSellPrice -under //20151125 陈飞要求不往上改价
						)
						){
					isChanged = true;
					float new_returnpoint = 0;
					int new_returnprice = 0;
					if(diffPrice<=999){//2000-1500 = 500
						new_returnprice = newPrice-sellPrice; //-500
					}else{//2000-995 = 1005
						new_returnpoint = (int)(100* diffPrice /sellPrice );
						new_returnprice = (int)(newPrice - sellPrice*(1-new_returnpoint/100));
					}
					//修改模型价格
					policyEditModel.setFieldValue("returnpoint", new_returnpoint+"");
					policyEditModel.setFieldValue("returnprice", new_returnprice+"");
					log.info("新的 returnpoint：" +new_returnpoint+" returnprice："+new_returnprice);
					
					changePriceLog.setNew_returnpoint(new_returnpoint+"");
					changePriceLog.setNew_returnprice(new_returnprice+"");
					
					cpaChanged = true;
					
				}
				
				
				int cpaDcpc = policyFloor.getCpaDcpc();
				 //设置cpa和cpc差价
				newPrice = newPrice + cpaDcpc;
				diffPrice = sellPrice-newPrice;
					
				
				if(policyFloor.isNeedSetCPC() && 
						(minSellPrice+cpaDcpc<cpcPrice && cpcPrice>floorPrice+cpaDcpc
//						|| cpcPrice  < minSellPrice+cpaDcpc-under		//20151125 陈飞要求不往上改价
								
								)
						
						){
					isChanged = true;
					float new_cpcReturnpoint = 0;
					int new_cpc_Returnprice = 0;
					if(diffPrice<=999){//2000-1500 = 500
						new_cpc_Returnprice = newPrice-sellPrice; //-500
					}else{//2000-995 = 1005
						new_cpcReturnpoint = (int)(100* diffPrice /sellPrice );
						new_cpc_Returnprice = (int)(newPrice - sellPrice*(1-new_cpcReturnpoint/100));
					}
					//修改模型价格
					policyEditModel.setFieldValue("cpcReturnPoint", new_cpcReturnpoint+"");
					policyEditModel.setFieldValue("cpcReturnprice", new_cpc_Returnprice+"");
					log.info("新的 cpcReturnpoint：" +new_cpcReturnpoint+" cpc_Returnprice："+new_cpc_Returnprice);
				
					changePriceLog.setNew_cpc_returnpoint(new_cpcReturnpoint+"");
					changePriceLog.setNew_cpc_returnprice(new_cpc_Returnprice+"");
					
				}
				
				if(isChanged){
					try{
						Thread.sleep(1000);
						//修改政策
						String newPolicyId = Client.savePolicy(policyEditModel, cookie);
						log.info("newPolicyId"+newPolicyId);
						if(newPolicyId==null){
							//TODO
							this.stop();
							log.error("获取不到新的政策id，停止任务");
							return;
						}
//						if(newPolicyId.length()!=9){
//							this.stop();
//							log.error("新的id长度不为9，停止任务 "+newPolicyId);
//							return;
//						}
						
						String oldPolicyId = policyFloor.getPolicyId();
						
						//修改已有改价日志的政策id
						policyService.updateChangePriceLogPolicyId(oldPolicyId, newPolicyId);
						log.info("修改已有改价日志id");
						
						//添加改价日志
						changePriceLog.setPolicy_id(newPolicyId);
						policyService.insertPriceLog(changePriceLog);
						log.info("添加改价日志");
						
						//修改底价的政策id
						policyFloor.setPolicyId(newPolicyId);
						int rowCount = policyService.savePolicyFloor(policyFloor);
						if(rowCount==0){
							log.error("修改底价的政策id失败，任务停止 "+oldPolicyId+" "+newPolicyId);
							
							this.stop();
							return;
						}
						
						this.changedCount++;
					}catch(Exception e){
						log.error("修改政策失败",e);						
						this.invalidPolicyCount++;
						
//						TODO
						this.run= false;
						log.error("获取不到新的政策id，停止任务");
						return;
						
//						continue;
					}
					
				
				}
			
				
				log.info("政策比价成功，policyId:"+policyFloor.getPolicyId());
				this.succedTaskCount++;
				
				if(this.intmin!=0){
					Thread.sleep(this.intmin*1000);
				}
			}catch(Exception e){
				
				log.info("政策比较失败，policyId:"+policyFloor.getPolicyId());
				e.printStackTrace();
				this.failedTaskCount++;
			}
			
		}
	
	}
	
	
	/**
	 * 
	 * @param m
	 * @return 返回0表示没有可用价格
	当票面价格类型是指定票面价的时候， 自有政策外放价 = 票面价格 ×（1 – 返点%）＋　留钱
	当票面价格类型是Y舱折扣的时候，   自有政策外放价 = Y舱全价×Y舱折扣×（1 – 返点%）＋　留钱，
              其中Y舱全价从Y舱全价维护表格中读取，如果读不到则不做处理

	 */
	private int getSellPrice(PolicyEditModel m){
		String facePriceType = m.getFieldValue("facePriceType");
		int price = 0;
		if("1".equals(facePriceType)){
			//票面价
			price = Integer.parseInt(m.getFieldValue("sellprice"));
		}else if("0".equals(facePriceType)){
			//Y舱折扣
			Integer p = this.yPriceMap.get((m.getFieldValue("flightcode")+m.getFieldValue("dpt")+m.getFieldValue("arr")).toUpperCase());
			if(p==null){
				return 0;
			}
			price = p;
			
			
		}
		
		return price;
	
	}
	


	
	public int getInterval() {
		return interval;
	}



	private void  buildYPriceMap() throws Exception{
		this.yPriceMap= new HashMap<String, Integer>();
		List<YPrice> list = policyService.getYPrice(null, null, null);
		for (YPrice p : list) {
			this.yPriceMap.put(p.getKey(), p.getPrice());
		}
	}
	
	
	
	public long getRoundCount() {
		return roundCount;
	}



	public int getInvalidPolicyCount() {
		return invalidPolicyCount;
	}



	public int getFailedTaskCount() {
		return failedTaskCount;
	}



	public int getSuccedTaskCount() {
		return succedTaskCount;
	}



	public int getChangedCount() {
		return changedCount;
	}



	public int getNoQuerySellPrice() {
		return noQuerySellPrice;
	}








	public Thread getThread() {
		return thread;
	}



	public List<String> getRoundTaskLogList() {
		return roundTaskLogList;
	}



	public static void main(String[] args) throws InterruptedException {
		long t1 = System.currentTimeMillis();
		Thread.sleep(1000);
		Thread.sleep(1000);
		Thread.sleep(1000);
		long t2 = System.currentTimeMillis();
		
		System.out.println(t2-t1);
		
	}
}
