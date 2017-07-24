package com.xzy.qcsl.jp.bj.crawler.parse;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.xzy.qcsl.jp.bj.model.Policy;

/**
 * @author xiangzy
 * @date 2015-6-24
 * 
 */
public class LoginPage {

	private static final Logger log = Logger.getLogger(PolicyListPage.class);

	private String pageContext;

	public LoginPage(String pageContext) {
		this.pageContext = pageContext;
		
		try {
			Parser parser = new Parser(this.pageContext);

			//解析政策列表
//			<table class="policyList" id="qntb_policyList" cellspacing="0" cellpadding="0">
				NodeList list =parser.parse(new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					if(node instanceof TableTag){
						if("qntb_policyList".equals(((TableTag)node).getAttribute("id"))){
							return true;
						}
					}
					return false;
				}
			});
			
			if(list.size()>0){
				TableTag table =  (TableTag)list.elementAt(0);
				TableRow[] rows = table.getRows();
			}
		}catch(Exception e){
			log.error("解析登陆页面失败", e);
		}
	}

}
