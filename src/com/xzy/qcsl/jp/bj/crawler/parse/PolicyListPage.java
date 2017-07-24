
package com.xzy.qcsl.jp.bj.crawler.parse;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

import com.xzy.qcsl.jp.bj.model.Pager;
import com.xzy.qcsl.jp.bj.model.Policy;

/**
 * @author xiangzy
 * @date 2015-6-22
 *
 */
public class PolicyListPage {
	

	private Pager pager = new Pager();
	

	
	private List<Policy> policyList= new ArrayList<Policy>();  
	
	
	private Parser parser;
	private static final Logger log = Logger.getLogger(PolicyListPage.class);
	

	public  PolicyListPage(String pageContext ,Policy queryPolicy){
	
		try {
			 parser = new Parser(pageContext);
			 
			 this.parsePolicyTable();
			 parser.reset();
			 this.parsePager();
		} catch (Exception e) {
			log.error("解析政策页面出错", e);
		}
	}
	
	/**
	 * 解析分页条内容， 当前页，每页多少条等等
	 	<div class="pager">
                <a onclick="changePage(1)" href="#nogo">首页</a>&nbsp;
                <a onclick="changePage(1)" href="#nogo">上页</a>&nbsp;
                
                <span class="currPage">1</span>
                
                <a onclick="changePage(2)" href="#nogo">2
                </a>&nbsp;
                
                <a onclick="changePage(3)" href="#nogo">3
                </a>&nbsp;
            
                <a onclick="changePage(2)" href="#nogo">下页</a>&nbsp;
                <a onclick="changePage(18373)" href="#nogo">尾页</a>&nbsp;
                <span class="all">共183726条/18373页</span>
                <span class="ps">显示<select id="recordCountSelect">
                    <option value="10" selected="">10</option>
                    <option value="30">30</option>
                    <option value="60">60</option>
                </select>条/页 </span>
			</div>
	 */
	
	private void parsePager() throws Exception{
	
		NodeList list =parser.parse (new HasAttributeFilter("class", "pager"));
		if(list.size()==0){
			return;
		}
		NodeVisitor visitor = new NodeVisitor(){
			public void visitTag(Tag tag) {
				if("currPage".equals(tag.getAttribute("class"))){
					pager.setPageNo(Integer.parseInt(((Span)tag).getChildrenHTML().trim()));
				}else if("all".equals(tag.getAttribute("class"))){
					String str = ((Span)tag).getChildrenHTML().trim();
					//共183726条/18373页
					String strs[] = str.split("/");
					if(strs.length==2){
						pager.setTotalRecord(Integer.parseInt(strs[0].trim().replace("共", "").replace("条", "")));
						pager.setTotalPage(Integer.parseInt(strs[1].trim().replace("页", "")));
					}
				}
			}
			
			
		} ;
		list.visitAllNodesWith(visitor);
//		visitor.
		
		;
		
		
	}
	
	/**
	 * 
	 * @throws  解析政策列表
	 */
	private void parsePolicyTable() throws Exception{
//		<table class="policyList" id="qntb_policyList" cellspacing="0" cellpadding="0">
//			NodeList list =parser.parse(new NodeFilter() {
//			@Override
//			public boolean accept(Node node) {
//				if(node instanceof TableTag){
//					if("qntb_policyList".equals(((TableTag)node).getAttribute("id"))){
//						return true;
//					}
//				}
//				return false;
//			}
//		});
//			
		NodeList list =parser.parse (new HasAttributeFilter("id", "qntb_policyList"));
		if(list.size()==0){
			return;
		}
		TableTag table =  (TableTag)list.elementAt(0);
		TableRow[] rows = table.getRows();
		for (int i = 1; i < rows.length; i++) {
			Policy policy = new Policy();
			TableRow row = rows[i];
			TableColumn[] columns = row.getColumns();
			for (int j = 0; j < columns.length; j++) {
				TableColumn col = columns[j];
				if(j==1){
					//航空公司
					policy.setFlightcode((col.getChildrenHTML().trim()));
				}else if(j==2){
					//起飞、到达
					//CSX<br/>    URC
//						String str[] = col.getChildrenHTML().trim().split("<br/>");
//						if(str.length==2){
//							policy.setDpt(str[0].trim());
//							policy.setArr(str[1].trim());
//						}
					policy.setDptArr(col.getChildrenHTML().trim());
				}else if(j==3){
					// 航班 限制类型
					policy.setFlightNumLimit(col.getChildrenHTML());
				}else if(j==4){
					//舱位
					String cw = col.getChildrenHTML().trim();
					policy.setCabin(cw);
				}else if(j==5){
					//政策类型
					String value = col.getChildrenHTML().trim();
					policy.setPolicyStr(value);
				}else if(j==6){
					//有效
					String value = col.getChildrenHTML().trim();
					policy.setStatus(value);
				}else if(j==7){
					//旅行有效期 开始、结束
//						String values[] = col.getChildrenHTML().trim().split("<br/>");
//						if(values.length==2){
//							policy.setStartdate(values[0]);
//							policy.setEnddate(values[1]);
//						}
					policy.setStartToEndDate(col.getChildrenHTML());
				}else if(j==8){
					//销售有效期 开始、结束
//						String values[] = col.getChildrenHTML().trim().split("<br/>");
//						if(values.length==2){
//							policy.setStartdate_ticket(values[0]);
//							policy.setEnfdate_ticket((values[1]));
//						}
					policy.setStartToEndDate_ticket(col.getChildrenHTML());
				}else if(j==10){
					//票面价/Y舱折扣
					//&yen;2800
					//0.7折
					String value = col.getChildrenHTML().trim();
					policy.setSellpriceOrdiscount(value);
				}else if(j==11){
					//政策代码
					String value = col.getChildrenHTML().trim();
					policy.setPolicyCode(value);
				}else if(j==12){
//						<a href="http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=3&ptype=2&id=102371318">修改</a>	                        
//                        <br>
//                        <a href="http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=0&ptype=2&id=102371318">查看</a><br>
//                         <a href="http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=1&ptype=2&id=102371318">复制</a>
					//操作：修改 查看 复制
					String strs[] = col.getChildrenHTML().trim().split("<br>");
//						String id = str.substring(str.indexOf("&id=")+4,str.indexOf("\">修改"));
					if(strs.length==3 && strs[1].contains("查看")){
						String str1 = strs[1].trim();
						int pos = str1.indexOf("ptype=");
						policy.setPtype(str1.substring(pos+6,pos+7));
						String id = (str1.substring(str1.indexOf("id=")+3,str1.indexOf("\">查看")));
						policy.setId(id);
					}

				}
			}
			policyList.add(policy);
		}
		
	}
		

	public List<Policy> getPolicyList() {
		return policyList;
	}





	public Pager getPager() {
		return pager;
	}
	
	
}
