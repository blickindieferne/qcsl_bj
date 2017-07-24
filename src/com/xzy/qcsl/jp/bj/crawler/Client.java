package com.xzy.qcsl.jp.bj.crawler;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;

import com.google.gson.Gson;
import com.xzy.qcsl.jp.bj.config.Config;
import com.xzy.qcsl.jp.bj.crawler.parse.PolicyEditPage;
import com.xzy.qcsl.jp.bj.exception.CookieInvalidException;
import com.xzy.qcsl.jp.bj.model.BJQueryResult;
import com.xzy.qcsl.jp.bj.model.BJQueryResult.Data;
import com.xzy.qcsl.jp.bj.model.BJQueryResult.Data.Record;
import com.xzy.qcsl.jp.bj.model.Pager;
import com.xzy.qcsl.jp.bj.model.Policy;
import com.xzy.qcsl.jp.bj.model.PolicyEditModel;
import com.xzy.qcsl.jp.bj.model.PolicyField;
import com.xzy.qcsl.jp.bj.model.PolicyFloor;
import com.xzy.qcsl.jp.bj.model.User;
import com.xzy.qcsl.jp.bj.util.StringUtil;

/**
 * @author xiangzy
 * @date 2015-6-21
 * 
 */
public class Client {
	private static final Logger log = Logger.getLogger(Client.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Gson gson = new Gson();
	
	public static String getPageContext(String url,String cookie, String charCode) throws ClientProtocolException, IOException {

		String context = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			
			HttpGet httpget = new HttpGet(url);
			
			
			
			httpget.setHeader("Cookie", cookie);

			response = httpclient.execute(httpget);
			
			

			HttpEntity entity = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
		
			
			log.info("status " + status);

			if (entity != null) {
				if(charCode!=null){
					context = EntityUtils.toString(entity,charCode);
				}else{
					context = EntityUtils.toString(entity);
				}
				
				EntityUtils.consume(entity);
			}
		}

		finally {
			try {
				if(response!=null){
					response.close();
				}
				if(httpclient!=null){
					httpclient.close();
				}
			} catch (IOException e) {
			
			}
		}

		return context;
	}
	/**
	 * 
	 * @param cookie
	 * @param queryPolicy
	 * @param pager 
	 * @return
	 * 
	 *  act:search
		key:
		maxId:0
		select_all:
		friendlyId:
		policyCode:
		cabin:
		flightcode:
		dpt:
		arr:
		startdate_ticket:yyyy-mm-dd
		startdate:yyyy-mm-dd
		policy:1
		status:3
		preOfficeNo:
		recordCount:10
		pageNo:1
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws CookieInvalidException 
	 * 
	 */
	public static String getPolicyListPage(String domain,String cookie,Policy queryPolicy, Pager pager ) throws ParseException, IOException, CookieInvalidException  {

		String context = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			
			HttpPost httpPost = new HttpPost(Config.getQunaerPolicyListUrl(domain));
			httpPost.setHeader("Cookie", cookie);
			
			List<NameValuePair> ps = new ArrayList<NameValuePair>();  
	        ps.add(new BasicNameValuePair("friendlyId", queryPolicy.getFriendlyId()));  
	        if(queryPolicy.getPolicyCode()!=null){
	        	String newString = new String(queryPolicy.getPolicyCode().getBytes("UTF-8"),"ISO_8859_1");
	        	ps.add(new BasicNameValuePair("policyCode", newString)); 
	        }
	        ps.add(new BasicNameValuePair("cabin", queryPolicy.getCabin())); 
	        ps.add(new BasicNameValuePair("flightcode", queryPolicy.getFlightcode())); 
	        ps.add(new BasicNameValuePair("dpt", queryPolicy.getDpt())); 
	        ps.add(new BasicNameValuePair("arr", queryPolicy.getArr())); 
	        ps.add(new BasicNameValuePair("startdate_ticket", queryPolicy.getStartdate_ticket())); 
	        ps.add(new BasicNameValuePair("startdate", queryPolicy.getStartdate())); 
	        ps.add(new BasicNameValuePair("policy", queryPolicy.getPolicy())); 
	        ps.add(new BasicNameValuePair("status", queryPolicy.getStatus())); 
	        
	        ps.add(new BasicNameValuePair("recordCount", pager.getRecordCount()+"")); 
	        ps.add(new BasicNameValuePair("pageNo", pager.getPageNo()+"")); 

	        httpPost.setEntity(new UrlEncodedFormEntity(ps)); 
			
			CloseableHttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
			if(status==477){
				throw new CookieInvalidException(Config.getQunaerPolicyListUrl(domain), status);
			}
			
			log.info("status " + status);

			if (entity != null) {
				context = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			}
			response.close();
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return context;
	}
	
	public static String getPolicyEditPage(String domain,String cookie,String ptype,String id) throws ClientProtocolException, IOException{
		String url = Config.getQunaerPolicyEditUrl(domain).replace("${ptype}", ptype).replace("${id}", id);
		String context  = getPageContext(url, cookie,null);
		return context;
	}
	
	public static String getPolicyViewPage(String domain,String cookie,String ptype,String id) throws ClientProtocolException, IOException{
		String url = Config.getQunaerPolicyViewUrl(domain).replace("${ptype}", ptype).replace("${id}", id);
		String context  = getPageContext(url, cookie,null);
		return context;
	}
	
	/**
	 http://fuwu.qunar.com/npolicy/policy/action/save
		ptype:1
		id:110754244
		pfid:MNT150701113603PN0005
		domain:hxs.trade.qunar.com
		sourceNum:1
		policy:1
		//--------
		startdate_ticket:2015-07-31
		enfdate_ticket:2015-08-01
		startdate:2015-08-02
		enddate:2015-08-03
		deptTimeSlot:
		beforeValidDay:0
		earliestBeforeValidDay:0
		daycondition:1
		daycondition:2
		daycondition:3
		daycondition:4
		daycondition:5
		daycondition:6
		daycondition:7
		flightcondition:3U8754
		rtype:0
		returnpoint:2
		returnprice:100
		cpaPutInPriceType:0
		cpaPutInNormalPrice:
		cpcReturnPoint:1
		cpcReturnprice:50
		preOfficeNo:
		officeno:
		ticketTime:
		restCabin:
		facePriceType:1
		sellprice:2800
		discount:
		returnRule:
		changeRule:
		cpcReturnRule:
		cpcChangeRule:
		act:3
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	//ptype=1&id=110754243&pfid=MNT150701113603PN0004&domain=hxs.trade.qunar.com&sourceNum=1&policy=1&startdate_ticket=2015-07-31&enfdate_ticket=2015-08-01&startdate=2015-08-02&enddate=2015-08-03&deptTimeSlot=&beforeValidDay=0&earliestBeforeValidDay=0&daycondition=1&daycondition=2&daycondition=3&daycondition=4&daycondition=5&daycondition=6&daycondition=7&flightcondition=3U8754&rtype=0&returnpoint=3&returnprice=100&cpaPutInPriceType=0&cpaPutInNormalPrice=&cpcReturnPoint=2&cpcReturnprice=50&preOfficeNo=&officeno=&ticketTime=&restCabin=&facePriceType=1&sellprice=2800&discount=&returnRule=&changeRule=&cpcReturnRule=&cpcChangeRule=&act=3
	public  static String savePolicy(PolicyEditModel m,String cookie) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String newPolicyId = null;
		try{
				
			HttpPost httpPost = new HttpPost(Config.getQunaerSavePolicyUrl());
			httpPost.setHeader("Cookie", cookie);
			
			List<NameValuePair> ps = new ArrayList<NameValuePair>();
			List<PolicyField> list = m.getFields();
			for (PolicyField policyField : list) {
				if(!policyField.isDisabled()){
					ps.add(new BasicNameValuePair(policyField.getName(), policyField.getValue()));
				}
			}
	        httpPost.setEntity(new UrlEncodedFormEntity(ps));
			CloseableHttpResponse response = httpclient.execute(httpPost);
	
			int status = response.getStatusLine().getStatusCode();
			log.info("status " + status);
			
			try{
				Header[] headers = response.getHeaders("Location");
				if(headers.length>0){
		//			[Location: http://fuwu.qunar.com/npolicy/policy/action/view?act=0&ptype=2&id=110754720&domain=hxs.trade.qunar.com]
					String newurl = headers[0].getValue();
					int idIndex = newurl.indexOf("&id=");
					if(idIndex==-1){
						idIndex = newurl.indexOf("?id=");
					}
					newurl = newurl.substring(idIndex+4);
					newPolicyId = newurl.substring(0,newurl.indexOf("&"));
					
					response.close();
					if(newPolicyId.length()!=9){
						System.out.println(status);
						System.out.println(headers[0].getValue());
					}
					
				}
			}catch(Exception e){
				log.error("获取新的policyId失败",e);
			}
		
		}finally{
		
			httpclient.close();
		}
		
		
		return newPolicyId;
	}
	
	/**
	 * 
	 * @param cookie
	 * @param m
	 * @return 返回0表示没有可用低价，不需要做比较
	 */
	public static int getMinSellPrice(String domain,String cookie,PolicyEditModel m,PolicyFloor policyFloor) throws Exception{
		
		//航空公司、出发、到达
		String carrier = m.getFieldValue("flightcode");
		String dpt = m.getFieldValue("dpt");
		String arr = m.getFieldValue("arr");
		
		//日期
		java.util.Date startdate = sdf.parse(m.getFieldValue("startdate"));
		java.util.Date enddate= sdf.parse(m.getFieldValue("enddate"));
		java.util.Date today= sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
		String flightDate = "";
//		2015-07-04
//		2015-07-05 - 2015-07-08
//		航班日期必填，如果当天还没有到政策“旅行有效期”的开始时间，
//		“航班日期”为旅行有效期的开始时间； 如果当前日期在旅行有效期之间，
//		航班日期设置为明天；如果天超出了旅行有效期不做处理
		if(today.before(startdate)){
			flightDate = sdf.format(startdate);
		}else if(today.before(enddate)){
			flightDate = sdf.format(new Date(System.currentTimeMillis()+86400000));
		}else if(today.equals(enddate)){
			flightDate = sdf.format(enddate);
		}else{
			return 0;
		}
		
		//航班号
		String flightNumLimit = m.getFieldValue("flightNumLimit");
		String flightNo = "";
		String exceptFlightNo = "";
		if("1".equals(flightNumLimit)){
			flightNo = m.getFieldValue("flightcondition");
		}else if("2".equals(flightNumLimit)){
//			TODO 排除类型不做处理
//			exceptFlightNo = m.getFieldValue("flightcondition");
			return 0;
		}
		
		//舱位
		String cabin = "";
//		TODO
		if(policyFloor.isCompareCabin() &&  !"5".equals(m.getFieldValue("policy"))){
			//包机切位舱位不作为条件
			cabin = m.getFieldValue("cabin");
		}
		
		
		/**
		http://hxs.trade.qunar.com/tts/agent/tool/statistics/bidding
			?flightNo=
			&dpt=pek
			&arr=ctu
			&flightDate=2015-07-04
			&cabin=y
			&carrier=ca
			&_v=1435924283238
			&_=1435924283238
			&domain=hxs.trade.qunar.com;
		**/
		StringBuffer url = new StringBuffer(Config.getQunaerBJQueryUrl(domain));
		url.append("&flightNo=").append(flightNo);
		url.append("&dpt=").append(dpt);
		url.append("&arr=").append(arr);
		url.append("&flightDate=").append(flightDate);
		url.append("&cabin=").append(cabin);
		url.append("&carrier=").append(carrier);
		
		log.info("访问比价页面:"+url.toString());
		String context = getPageContext(url.toString(), cookie,null);
		
		log.info("解析比价查询结果json");
		
		BJQueryResult bjQueryResult = null;
		try{
			bjQueryResult = gson.fromJson(context, BJQueryResult.class);
		}catch(Exception e){
			log.error(context);
			throw e;
		}
		Data data = bjQueryResult.getData();
		if(data==null||data.getOrderList()==null){
			return 0;
		}
		List<Record> list = data.getOrderList();
		int minSellPrice = Integer.MAX_VALUE;
		for (Record r : list) {
			//如果有共享政策就忽略
//			 if(StringUtil.isNotEmptyOrNull(r.getShareCode())){
//				 continue;
//			 }
			 //如果是自有政策 忽略
			 if(r.isTipRed() && r.getSelfPrice()!=0){
				 continue;
			 }
			minSellPrice =  Math.min(minSellPrice, r.getSellPrice());
		}
		return minSellPrice;
		
	}
	
	public static User getUserInfo(String cookie,String domain){
		try{
		String context = getPageContext("http://fuwu.qunar.com/index.do?domain="+domain, cookie,"utf-8");
		if(context==null){
			return null;
		}
		Parser parser = new Parser(context);
		NodeList list = parser.parse(new HasAttributeFilter("class","username"));
		if(list.size()>0){
			TagNode tag = (TagNode)list.elementAt(0);
			String username = tag.getAttribute("title");
			User user = new User();
			user.setRealName(username);
			user.setUsername(username);
			return user;
		
		}
				
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
				
		return null;
	}
	
	/*
	 https://user.qunar.com/passport/loginx.jsp
		loginType:0
		ret:http://fuwu.qunar.com/userpass/clean
		username:18982174566
		password:s1234567@3
		remember:0
		vcode:3ddc
		answer:2aBOyQc5hd3lImDKA0cA4Y8kcJ35TDGYD08A4Qsl1cTlUzGLI3hUg5h5oV44Oj2LqjPZ75vkyUTlEWnKxPBNYBR21B3xFODZt70A58uuyVX2VvmKlX8BidR2cpX2sfjOQvwAf1wjc52lBXTRASsR18B548W5VXWM-CBMeE8ks82mY7WMCitMlZx4gAD4FCmM-OsB
	 */
	public static void doLogin() throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
			//TODO 无法登陆
			HttpPost httpPost = new HttpPost("http://user.qunar.com/passport/loginx.jsp");
		
			
			List<NameValuePair> ps = new ArrayList<NameValuePair>();  
	        ps.add(new BasicNameValuePair("loginType", "0"));  
	        ps.add(new BasicNameValuePair("ret", "http://fuwu.qunar.com/userpass/clean"));  
	        ps.add(new BasicNameValuePair("username", "18982174566"));  
	        ps.add(new BasicNameValuePair("password", "s1234567@3"));  
	        ps.add(new BasicNameValuePair("remember", "0"));  
	        ps.add(new BasicNameValuePair("vcode", "AEK5"));  
	        ps.add(new BasicNameValuePair("answer", "2aBOyQc5hd3lImDKA0cA4Y8kcJ35TDGYD08A4Qsl1cTlUzGLI3hUg5h5oV44Oj2LqjPZ75vkyUTlEWnKxPBNYBR21B3xFODZt70A58uuyVX2VvmKlX8BidR2cpX2sfjOQvwAf1wjc52lBXTRASsR18B548W5VXWM-CBMeE8ks82mY7WMCitMlZx4gAD4FCmM-OsB"));  
	        httpPost.setEntity(new UrlEncodedFormEntity(ps)); 
			System.out.println("1");
			CloseableHttpResponse response = httpclient.execute(httpPost);
			System.out.println("2");
			HttpEntity entity = response.getEntity();
			String context = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			
			System.out.println(context);
	
	}
	
	public static void main(String[] args) {
		/**
		String newurl = "http://fuwu.qunar.com/npolicy/policy/action/view?id=110754720&act=0&ptype=2&domain=hxs.trade.qunar.com";
		int idIndex = newurl.indexOf("&id=");
		if(idIndex==-1){
			idIndex = newurl.indexOf("?id=");
		}
		newurl = newurl.substring(idIndex+4);
		String newPolicyId = newurl.substring(0,newurl.indexOf("&"));
		System.out.println(newPolicyId);
		
		if(1==1){
			return;
		}
		
		String cookie = "QN99=4856; QunarGlobal=192.168.0.81_3f04fa8f_142c74d5fac_27a6|1386324306682; QN1=wKgZEVKhoVImTFm7WVNvAg==; RcGlobal=9bd89621f030321000d6f56684a02e0d30; QN73=2011-2012; __utma=183398822.107014862.1427944322.1427944322.1427944322.1; __utmz=183398822.1427944322.1.1.utmcsr=qunar.com|utmccn=(referral)|utmcmd=referral|utmcct=/; s_fid=69CD93D6B2DCC126-1C939B0FAD33C692; _ga=GA1.2.107014862.1427944322; __ag_cm_=1434881347840; QN29=998d4b7569814e8aaf9455b8f2afc055; ag_fid=T3B95tLWywjABWgF; _mdp=%0A%13%14%00z%01%02t; QN43=2; QN42=zdih6947; _q=U.oqsu3751; _t=23934992; csrfToken=N9U4ptjyJMPtNTpjjrhGjv9CgsOnnoAj; _v=aqIrZYmbwfiUxzTcIUibiZEAgBtppkqkU0cUomvZfRDF1AB0XxUnD0yjfFSMywPtPxyb_S6EJ-i9EzQrSg5d2iishXNZw8m7Arkk5up9UCTNAJHnx6pFfs6UTrVISgSPy0dRxdc3C8Wri1GP9NlDe0exut8TkQXTT5HYpa5-GJyP; QN44=oqsu3751; QN163=0; Hm_lvt_75154a8409c0f82ecd97d538ff0ab3f3=1434977658,1435028330,1435334238,1436276833; Hm_lpvt_75154a8409c0f82ecd97d538ff0ab3f3=1436276833; PHPSESSID=h7knjncsqgdq3h2ub3psvl2k46; _i=RBTjekX6fmUxSIuTi3bDwURlnbMx; _vi=ofty3wlGde1f50are6qi6tkfWSresc3vnmQUsD4QiVqZZLgmZGML9aqMNv2ZIhJPtSo_hKcoPDPdZYt_nTiMN6Y_s1UNEiBtVZ-yCrnwguKbBouOyGdVlTPbn7CBY8WeoDxwp6qaSe3ypGPgzJt1c73XNyzy0xGzYgxp-IZNdTZM; _jzqa=1.46726033670667736.1427944311.1435334241.1436276835.8; _jzqc=1; _jzqx=1.1427944311.1436276835.1.jzqsr=qunar%2Ecom|jzqct=/.-; _jzqckmp=1; _jzqb=1.1.10.1436276835.1; RT=s=1436276852896&r=http%3A%2F%2Fwww.qunar.com%2F; JSESSIONID=E58CA1B3AD74BB1768D24CD3633A59D9";
		
		Config.qunaerPolicyEditUrl = "http://fuwu.qunar.com/npolicy/policy/action/view?domain=hxs.trade.qunar.com&act=3&ptype=${ptype}&id=${id}";
		String policyId ="113338362";
		int returnprice = 0;
		
		while(returnprice<100){
			returnprice++;
			System.out.println(returnprice +"------------------");
			
			
			String editContext;
			try {
				editContext = Client.getPolicyEditPage(cookie, "2", policyId);
			} catch (Exception e) {
				log.error("获取编辑页面失败，policyId:"+ policyId,e);
	
				return;
			}
			
			PolicyEditPage page;
			try {
				page = new PolicyEditPage(editContext);
			} catch (Exception e) {
				log.error("解析编辑页面失败，policyId:"+ policyId,e);
				return;
			}
			
			
			PolicyEditModel m = page.getPolicyEditModel();
			m.setFieldValue("returnprice", returnprice+"");
			try {
				System.out.println("old:----"+policyId);
				policyId = Client.savePolicy(m, cookie);
				System.out.println("new:----"+policyId);
				if(policyId.equals("0")){
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		
		}
		
**/
	
	}
}
