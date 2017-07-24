package com.xzy.qcsl.jp.bj.crawler.parse;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

import com.xzy.qcsl.jp.bj.crawler.Client;
import com.xzy.qcsl.jp.bj.model.Policy;
import com.xzy.qcsl.jp.bj.model.PolicyEditModel;
import com.xzy.qcsl.jp.bj.model.PolicyField;

/**
 * @author xiangzy
 * @date 2015-7-1
 * 
 */
public class PolicyEditPage {

	private static final Logger log = Logger.getLogger(PolicyEditPage.class);

	private Policy policy;;
	private PolicyEditModel policyEditModel;

	public PolicyEditPage(String context) throws Exception {
		this.parseToPolicyEditModel(context);
		
	}



	private void parseToPolicyEditModel(String context) throws Exception{
		
			this.policyEditModel = new PolicyEditModel();
			Parser parser = new Parser(context);
			NodeList list = parser.parse(new HasAttributeFilter("id","searchForm"));
			list.visitAllNodesWith(new NodeVisitor() {
				public void visitTag(Tag tag) {
					String name = tag.getAttribute("name");
					if(name!=null){
						
						boolean disabled = "disabled".equals(tag.getAttribute("disabled"));
						
						
						if(tag instanceof InputTag){
							String type = tag.getAttribute("type");
							//check radio
							if("checkbox".equals(type)||"radio".equals(type)){
								if("checked".equals(tag.getAttribute("checked"))){
									policyEditModel.addField(name, tag.getAttribute("value"),disabled);
								}
							}else{
								//input
								policyEditModel.addField(name, tag.getAttribute("value"),disabled);
							}
						}else if(tag instanceof SelectTag){
							SelectTag st = (SelectTag)tag;
							for (OptionTag opt : st.getOptionTags()) {
								if("selected".equals(opt.getAttribute("selected"))){
									policyEditModel.addField(name, opt.getAttribute("value"),disabled);
									break;
								}
							}
						}else if(tag instanceof TextareaTag){
							//TODO 两个 name="flightcondition"都为可用，特殊处理
							String value = ((TextareaTag)tag).getChildrenHTML().trim();
							if(value.equals("") && name.equals("flightcondition")){
								return;
							}
							
							policyEditModel.addField(name, value,disabled);
							
						}
					}else if ("value state".equals((tag.getAttribute("class")))) {
						//状态
						String status = ((TableColumn) tag).getChildrenHTML().trim();
						//TODO 状态有效才处理，非有效时要设置底价无效
						policyEditModel.setStatus(status);
					}
						
				}
				
			});
			
			
	
	}
	
	private void parseToPolicy(String context){


		try {
			this.policy = new Policy();
			Parser parser = new Parser(context);
			NodeList list = parser.parse(new HasAttributeFilter("id",
					"searchForm"));

			// 政策状态 有效
			// 销售有效期 2014-12-09-2015-12-15
			// --------------------
			// 航班日期*
			// 航空公司*
			// 航班号
			// 出发*
			// 到达*
			// 舱位*（包机切位不用）
			// ------------------------
			// 票面价类型
			// 票面价/折扣
			// CPA返点、CAP留钱、CPC返点、CPC留钱
			//---------------------
			//其他需要提交的状态

			list.visitAllNodesWith(new NodeVisitor() {
				public void visitTag(Tag tag) {
//					if ("value state".equals((tag.getAttribute("class")))) {
//						//状态
//						String status = ((TableColumn) tag).getChildrenHTML()
//								.trim();
//						// 状态有效才处理，非有效时要设置底价无效
//						policy.setStatus(status);
//					}else 
						if("startdate_ticket".equals(tag.getAttribute("name"))){
						//销售开始日期
						policy.setStartdate_ticket(tag.getAttribute("value"));
					}else if("enfdate_ticket".equals(tag.getAttribute("name"))){
						//销售结束日期
						policy.setEnfdate_ticket(tag.getAttribute("value"));
					}else if("startdate".equals(tag.getAttribute("name"))){
						//旅行开始日期
						policy.setStartdate(tag.getAttribute("value"));
					}else if("enddate".equals(tag.getAttribute("name"))){
						//旅行结束日期
						policy.setEnddate(tag.getAttribute("value"));
					}else if("flightcode".equals(tag.getAttribute("name"))){
						//航空公司
						policy.setFlightcode(tag.getAttribute("value"));
					}else if("flightcode".equals(tag.getAttribute("name"))){
						//航空公司
						policy.setFlightcode(tag.getAttribute("value"));
						
					}else if("flightNumLimit_0".equals(tag.getAttribute("id")) && "checked".equals(tag.getAttribute("checked"))){
						//航班限制类型-全部
						policy.setFlightNumLimit("0");
					}else if("flightNumLimit_1".equals(tag.getAttribute("id"))&& "checked".equals(tag.getAttribute("checked"))){
						//航班限制类型-适用航班
						policy.setFlightNumLimit("1");
					}else if("flightNumLimit_2".equals(tag.getAttribute("id"))&& "checked".equals(tag.getAttribute("checked"))){
						//航班限制类型-不适用航班
						policy.setFlightNumLimit("2");
					}else if("flightcondition_1".equals(tag.getAttribute("id"))){
						//适用航班号
						if("1".equals(policy.getFlightNumLimit())){
							policy.setFlightcondition(((TextareaTag)tag).getChildrenHTML().trim());
						}
					}else if("flightcondition_2".equals(tag.getAttribute("id"))){
						//不适用航班号
						if("2".equals(policy.getFlightNumLimit())){
							policy.setFlightcondition(((TextareaTag)tag).getChildrenHTML().trim());
						}
					}
					
					else if("dpt".equals(tag.getAttribute("name"))){
						//起飞
						policy.setDpt(tag.getAttribute("value"));	
					}else if("arr".equals(tag.getAttribute("name"))){
						//到达
						policy.setArr(tag.getAttribute("value"));	
					}else if("cabin".equals(tag.getAttribute("name"))){
						//舱位
						policy.setCabin(tag.getAttribute("value"));	
					}else if("facePriceType".equals(tag.getAttribute("name"))){
						//票面价类型
						SelectTag st = ((SelectTag)tag);
						for (OptionTag opt : st.getOptionTags()) {
							if("selected".equals(opt.getAttribute("selected"))){
								policy.setFacePriceType(opt.getAttribute("value"));
								break;
							}
						}
					}else if("discount".equals(tag.getAttribute("name"))){
						//Y舱折扣
						policy.setDiscount((tag.getAttribute("value")));	
					}else if("sellprice".equals(tag.getAttribute("name"))){
						//票面价
						policy.setSellprice(tag.getAttribute("value"));	
					}else if("returnpoint".equals(tag.getAttribute("name"))){
						//CPA返点
						policy.setReturnpoint(tag.getAttribute("value"));	
					}else if("returnprice".equals(tag.getAttribute("name"))){
						//CPA留钱
						policy.setReturnprice(tag.getAttribute("value"));	
					}else if("cpcReturnPoint".equals(tag.getAttribute("name"))){
						//CPA返点
						policy.setCpcReturnPoint(tag.getAttribute("value"));	
					}else if("cpcReturnprice".equals(tag.getAttribute("name"))){
						//CPA留钱
						policy.setCpcReturnprice(tag.getAttribute("value"));	
					}
					
					//-------------------
//					ptype:1
//					id:110754244
//					pfid:MNT150701113603PN0005
//					domain:hxs.trade.qunar.com
//					sourceNum:1
//					policy:1
					else if("ptype".equals(tag.getAttribute("name"))){
						//
						policy.setPtype(tag.getAttribute("value"));	
					}else if("id".equals(tag.getAttribute("name"))){
						//
						policy.setId(tag.getAttribute("value"));	
					}else if("pfid".equals(tag.getAttribute("name"))){
						//
						policy.setPfid(tag.getAttribute("value"));	
					}else if("domain".equals(tag.getAttribute("name"))){
						//
						policy.setDomain(tag.getAttribute("value"));	
					}else if("sourceNum".equals(tag.getAttribute("name"))){
						//
						policy.setSourceNum(tag.getAttribute("value"));	
					}else if("policy".equals(tag.getAttribute("name"))){
						//
						policy.setPolicy(tag.getAttribute("value"));	
					}
					
					else if("deptTimeSlot".equals(tag.getAttribute("name"))){
						//航班起飞时间
						policy.setDeptTimeSlot(tag.getAttribute("value"));	
					}else if("beforeValidDay".equals(tag.getAttribute("name"))){
						//最早提前出票时间
						policy.setBeforeValidDay(tag.getAttribute("value"));	
					}else if("earliestBeforeValidDay".equals(tag.getAttribute("name"))){
						//最晚提前出票时间
						policy.setEarliestBeforeValidDay(tag.getAttribute("value"));	
					}
					
					
					else if("daycondition".equals(tag.getAttribute("name"))){
						//班期限制
						if("checked".equals(tag.getAttribute("checked"))){
							policy.getDayconditions().add(tag.getAttribute("value"));
						}
					}
					
					//<input name="xcd" disabled="disabled" type="radio" checked="checked" autocomplete="off" value="1"/>
					else if("xcd".equals(tag.getAttribute("name"))){
						//是否提供行程单
						if("checked".equals(tag.getAttribute("checked"))){
							policy.setXcd(tag.getAttribute("value"));
						}
					}
					//<input name="rtype" type="radio" checked="checked" autocomplete="off" value="0"/>
					else if("rtype".equals(tag.getAttribute("name"))){
						//政策返点留钱类型
						if("checked".equals(tag.getAttribute("checked"))){
							policy.setRtype(tag.getAttribute("value"));
						}
					}
					
					else if("cpaPutInPriceType".equals(tag.getAttribute("name"))){
						//cap投放类型
						if("checked".equals(tag.getAttribute("checked"))){
							policy.setCpaPutInPriceType(tag.getAttribute("value"));
						}
					}
					
					else if("cpaPutInNormalPrice".equals(tag.getAttribute("name"))){
						//CPA投放标价
						policy.setCpaPutInNormalPrice(tag.getAttribute("value"));	
					}
					
					//preOfficeNo  radio
					else if("preOfficeNo".equals(tag.getAttribute("name"))){
						if("checked".equals(tag.getAttribute("checked"))){
							policy.setPreOfficeNo(tag.getAttribute("value"));
						}
					}
					
//					officeno
					else if("officeno".equals(tag.getAttribute("name"))){
						policy.setOfficeno(tag.getAttribute("value"));	
					}
					
//					ticketTime
					else if("ticketTime".equals(tag.getAttribute("name"))){
						policy.setTicketTime(tag.getAttribute("value"));	
					}
					
//					returnRule:
					else if("returnRule".equals(tag.getAttribute("name"))){
						policy.setReturnRule(tag.getAttribute("value"));	
					}
					
//					changeRule:
					else if("changeRule".equals(tag.getAttribute("name"))){
						policy.setChangeRule(tag.getAttribute("value"));	
					}
//						
//					cpcReturnRule:
					else if("cpcReturnRule".equals(tag.getAttribute("name"))){
						policy.setCpcReturnRule(tag.getAttribute("value"));	
					}
					
//					cpcChangeRule:
					else if("cpcChangeRule".equals(tag.getAttribute("name"))){
						policy.setCpcChangeRule(tag.getAttribute("value"));	
					}
//						
//					cpcEndorsement:0   check
					else if("cpcEndorsement".equals(tag.getAttribute("name"))){
						if("checked".equals(tag.getAttribute("checked"))){
							policy.setCpcEndorsement(tag.getAttribute("value"));
						}
					}
					
//					act:
					else if("act".equals(tag.getAttribute("name"))){
						policy.setAct(tag.getAttribute("value"));	
					}


					
				}
			});

		} catch (Exception e) {
			policy = null;
			log.error("解析政策编辑页面失败", e);
		}
		
	
	}
	
	public Policy getPolicy() {
		return this.policy;
	}
	
	
	public PolicyEditModel getPolicyEditModel() {
		return policyEditModel;
	}

	public static void main(String[] args) throws Exception {
		String urlOk = "http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=0&ptype=1&id=110754309";
		String urlDel = "http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=0&ptype=2&id=110445763";
		String url3 ="http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=3&ptype=2&id=110754444";
		String context = Client
				.getPageContext(
						url3,
						"QN99=4856; QunarGlobal=192.168.0.81_3f04fa8f_142c74d5fac_27a6|1386324306682; QN1=wKgZEVKhoVImTFm7WVNvAg==; RcGlobal=9bd89621f030321000d6f56684a02e0d30; QN73=2011-2012; __utma=183398822.107014862.1427944322.1427944322.1427944322.1; __utmz=183398822.1427944322.1.1.utmcsr=qunar.com|utmccn=(referral)|utmcmd=referral|utmcct=/; s_fid=69CD93D6B2DCC126-1C939B0FAD33C692; _ga=GA1.2.107014862.1427944322; __ag_cm_=1434881347840; QN29=998d4b7569814e8aaf9455b8f2afc055; Hm_lvt_75154a8409c0f82ecd97d538ff0ab3f3=1434546039,1434977658,1435028330,1435334238; _jzqa=1.46726033670667736.1427944311.1435028332.1435334241.7; _jzqx=1.1427944311.1435334241.1.jzqsr=qunar%2Ecom|jzqct=/.-; ag_fid=T3B95tLWywjABWgF; _mdp=%0A%13%14%00z%01%02t; QN238=zh_cn; JSESSIONID=AF0EA3A1AF5BAF60E0348F10DCA5329B; QN25=eafed3bc-fc02-47d1-ba9c-80da65bdd39f-9f992f90; _i=RBTjekX6fmUxSIuTi3bDwURlnbMx; _challenge=c72978928bfe4609a408110aeb732bc3; QN43=2; QN42=zdih6947; _q=U.oqsu3751; _t=23924278; csrfToken=gbXJsuJRx0n7tgXXOproidkmnEDQlbo1; _v=sbbUS-DZOBv549RRb6_jQEG84eqM0dJnfNAossmzTCv4QYVOa14ZfyAesMC3Ii6Iuao_H4N-5D8s9RtyHgQgrGDm99OTWbxlt6IIMeNKPbnRqXCU6l0Sq46Kt1kXdjZWYkJhL3HSfGpFJavkiOmYUMeXW2-Jabp5fztadGKf95uv; _vi=RLNCUuWbdyS_JvhhJ6PJF0Z7rd8kipW1wYuzmIrXsCNaSCSTNgcbCSKGzmIokHc-kfvr1wBiFl4UFYRGDn7mukyR4Eib8RrHjdPguluPfDRsgqJK222Ctu6Ypd-yMj0qQexZHE5LVhfNehbPuW-RDLWluexy5u2xP0MvlcEu0Wo1",null);

		// System.out.println(context);
		Long t1 = System.currentTimeMillis();
		PolicyEditPage page = new PolicyEditPage(context);
		Long t2 = System.currentTimeMillis();
		System.out.println("耗时："+(t2 - t1));
		
		PolicyEditModel m = page.getPolicyEditModel();
		System.out.println(m.getStatus());
		List<PolicyField> fields = m.getFields();
		for (PolicyField f : fields) {
			if(!f.isDisabled()){
				System.out.println(f.getName()+":"+f.getValue());
			}else{
				System.out.println(f.getName()+"\t\t"+f.getValue());
			}
		}
		
		/**
		Policy p = page.getPolicy();
		// 政策状态 有效
					// 销售有效期 2014-12-09-2015-12-15
					// --------------------
					// 航班日期*
					// 航空公司*
					// 航班号
					// 出发*
					// 到达*
					// 舱位*（包机切位不用）
					// ------------------------
					// 票面价类型
					// 票面价/折扣
					// CPA返点、CAP留钱、CPC返点、CPC留钱
		System.out.println("------------------");
		System.out.println("状态\t-"+ p.getStatus());
		System.out.println("销售日期\t-"+p.getStartdate_ticket()+" "+p.getEnfdate_ticket());
		System.out.println("旅行日期\t-"+p.getStartdate()+" "+p.getEnddate());
		System.out.println("航空公司\t-"+p.getFlightcode());
		System.out.println("航班号限制\t-"+p.getFlightNumLimit());
		System.out.println("航班号\t-"+p.getFlightcondition());
		System.out.println("出发\t-"+p.getDpt());
		System.out.println("到达\t-"+p.getArr());
		System.out.println("舱位\t-"+p.getCabin());
		System.out.println("票面价类型\t-"+p.getFacePriceType());
		System.out.println("Y舱折扣\t-"+p.getDiscount());
		System.out.println("指定票面价\t-"+p.getSellprice());
		System.out.println("cpa返点\t-"+p.getReturnpoint());
		System.out.println("cap留钱\t-"+p.getReturnprice());
		System.out.println("cpc返点\t-"+p.getCpcReturnPoint());
		System.out.println("cpc留钱\t-"+p.getCpcReturnprice());
//		ptype:1
//		:110754244
//		:MNT150701113603PN0005
//		:hxs.trade.qunar.com
//		:1
//		:1
		System.out.println("-----------");
		System.out.println("ptype\t-"+p.getPtype());
		System.out.println("id\t-"+p.getId());
		System.out.println("pfid\t-"+p.getPfid());
		System.out.println("domain\t-"+p.getDomain());
		System.out.println("sourceNum\t-"+p.getSourceNum());
		System.out.println("policy\t-"+p.getPolicy());
		
//		deptTimeSlot:
//		beforeValidDay:0
//		earliestBeforeValidDay:0
		
		System.out.println("deptTimeSlot\t-"+p.getDeptTimeSlot());
		System.out.println("beforeValidDay\t-"+p.getBeforeValidDay());
		System.out.println("earliestBeforeValidDay\t-"+p.getEarliestBeforeValidDay());
		System.out.print("dayconditions:\t-");
		for (String string : p.getDayconditions()) {
			System.out.print(string+" ");
		}
		System.out.println();
		
		System.out.println("xcd\t-"+p.getXcd());
		System.out.println("政策返点留钱类型\t-"+p.getRtype());
		System.out.println("cpa投放类型\t-"+p.getCpaPutInPriceType());
		System.out.println("cpa标价\t-"+p.getCpaPutInNormalPrice());
		
		System.out.println("preOfficeNo\t-"+p.getPreOfficeNo());
		
		//....
		System.out.println(p.getAct());
		**/
	}
}
