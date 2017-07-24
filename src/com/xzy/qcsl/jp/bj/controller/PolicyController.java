package com.xzy.qcsl.jp.bj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xzy.qcsl.jp.bj.crawler.Client;
import com.xzy.qcsl.jp.bj.crawler.parse.PolicyListPage;
import com.xzy.qcsl.jp.bj.exception.CookieInvalidException;
import com.xzy.qcsl.jp.bj.model.ChangePriceLog;
import com.xzy.qcsl.jp.bj.model.Pager;
import com.xzy.qcsl.jp.bj.model.Policy;
import com.xzy.qcsl.jp.bj.model.PolicyFloor;
import com.xzy.qcsl.jp.bj.model.PolicyFloorLog;
import com.xzy.qcsl.jp.bj.model.User;
import com.xzy.qcsl.jp.bj.model.YPrice;
import com.xzy.qcsl.jp.bj.service.PolicyService;
import com.xzy.qcsl.jp.bj.util.StringUtil;

/**
 * @author xiangzy
 * @date 2015-6-24
 * 
 */

@Controller
@RequestMapping("/policy/*")
public class PolicyController {
	

	private final static Logger log = Logger.getLogger(PolicyController.class);

	private PolicyService policyService = PolicyService.getInstance();
	
	@RequestMapping("/list.do")
	public String policyList(HttpServletRequest request,Policy queryPolicy,Pager queryPager) {

		String context=null;
		try {
			context = Client.getPolicyListPage(request.getSession()
					.getAttribute("domain").toString(),request.getSession()
					.getAttribute("cookie").toString(),queryPolicy,queryPager);
		} catch (CookieInvalidException e) {
			 request.setAttribute("cookieInvalid", true);
			 return "/login"; 
		} catch (Exception e) {
			log.error("读取政策列表页面失败", e);
			return "/policy/policy_list";
		}
		
		//获得底价设置
		PolicyListPage policyListPage = new PolicyListPage(context,queryPolicy);
		List<Policy> list =policyListPage.getPolicyList();
		for (Policy policy : list) {
			PolicyFloor policyFloor = policyService.getPolicyFloorByPolicyId(policy.getId());
			if(policyFloor!=null && policyFloor.isStatus()){
				if(policyFloor.getType()==0){
					if(policyFloor.getFloorPrice()>0){
						policy.setFloor(policyFloor.getFloorPrice()+"");
					}
				}else{
					int point = policyFloor.getFloorPoint();
					if(point>0 && point<100){
						policy.setFloor(point+"%");
					}
				}
			}
		}
		
		request.setAttribute("policyList", list);
		if(queryPager.getRecordCount()!=10){
			policyListPage.getPager().setRecordCount(queryPager.getRecordCount());
		}
		request.setAttribute("pager1", policyListPage.getPager());
		request.setAttribute("queryPolicy", queryPolicy);
		return "/policy/policy_list";
	}
	
	@RequestMapping("/editPoicyFloor.do")
	public String editPolicyFloor(HttpServletRequest request,Policy p,String ids,String ptype){
		if(StringUtil.isNotEmptyOrNull(ids)){
			//批量设置底价
			request.setAttribute("policyIds", ids);
			PolicyFloor policyFloor = new PolicyFloor();
			policyFloor = new PolicyFloor();
			policyFloor.setNeedSetCPA(true);
			policyFloor.setStatus(true);
			policyFloor.setUnder(1);
			policyFloor.setPtype(ptype);

			request.setAttribute("policyFloor", policyFloor);
		}else{
			//设置单独的一个政策底价
			PolicyFloor policyFloor = policyService.getPolicyFloorByPolicyId(p.getId());
			if(policyFloor==null){
				policyFloor = new PolicyFloor();
				policyFloor.setPolicyId(p.getId());
				policyFloor.setNeedSetCPA(true);
				policyFloor.setStatus(true);
				policyFloor.setUnder(1);
			}else{
				//底价设置日志
				List<PolicyFloorLog> logList = new ArrayList<PolicyFloorLog>();
				logList = policyService.getPolicyFloorLogByPolicyFloorId(policyFloor.getId());
				request.setAttribute("logList", logList);
			}
			request.setAttribute("policy", p);
			request.setAttribute("policyFloor", policyFloor);

		}
		
		return "policy/edit_policy_floor";
	}

	
	@RequestMapping("/savePoicyFloor.do")
	@ResponseBody
	public PolicyFloor savePolicyFloor(HttpServletRequest request,PolicyFloor policyFloor){
		User user = (User)request.getSession().getAttribute("user");
		policyFloor.setLastUsername(user.getUsername());
		try {
			policyService.savePolicyFloor(policyFloor);
		} catch (Exception e) {
			log.error("保存底价失败", e);
		}
		PolicyFloorLog log = policyService.makePolicyFloorLog(policyFloor,user);
		policyService.savePolicyFloorLog(log);
		return policyFloor;
		//return new ModelAndView("redirect:/policy/editPoicyFloor.do?id="+policyFloor.getPolicyId()+"&ptype="+request.getParameter("ptype"));
	}

	@RequestMapping("/savePoicyFloorBatch.do")
	@ResponseBody
	public ResultData savePoicyFloorBatch(HttpServletRequest request,String policyIds,PolicyFloor policyFloor){
		String str[] = policyIds.split(",");
		User user = (User)request.getSession().getAttribute("user");
		for (String policyId : str) {
			System.out.println(policyId);
			PolicyFloor policyFloorTemp = policyService.getPolicyFloorByPolicyId(policyId);
			if(policyFloorTemp==null){//
				policyFloorTemp = new PolicyFloor();
			}
				policyFloorTemp.setPolicyId(policyId);
				policyFloorTemp.setNeedSetCPA(policyFloor.isNeedSetCPA());
				policyFloorTemp.setStatus(policyFloor.isStatus());
				policyFloorTemp.setUnder(policyFloor.getUnder());
				policyFloorTemp.setNeedSetCPC(policyFloor.isNeedSetCPC());
				policyFloorTemp.setFloorPrice(policyFloor.getFloorPrice());
				policyFloorTemp.setLastUsername(user.getUsername());
				policyFloorTemp.setType(policyFloor.getType());
				policyFloorTemp.setFloorPoint(policyFloor.getFloorPoint());
				policyFloorTemp.setPtype(policyFloor.getPtype());
				policyFloorTemp.setCompareCabin(policyFloor.isCompareCabin());
				policyFloorTemp.setCpaDcpc(policyFloor.getCpaDcpc());
				
				//保存底价
				try {
					policyService.savePolicyFloor(policyFloorTemp);
				} catch (Exception e) {
					log.error("保存底价失败", e);
					continue;
				}
				//保存底价日志
				PolicyFloorLog log = policyService.makePolicyFloorLog(policyFloorTemp,user);
				policyService.savePolicyFloorLog(log);
			//}
		}
		/*
		*/
		//跳转到编辑页面，相当于刷新
		return new ResultData(ResultData.SUCCESS, "保存成功");
	}
	

	@RequestMapping("/YPriceList.do")
	public String YPriceList(HttpServletRequest request,String flightcode,String dpt,String arr) throws Exception{
		List<YPrice> list = new ArrayList<YPrice>();
		list = policyService.getYPrice(flightcode, dpt, arr);
		request.setAttribute("yPricelist", list);
		request.setAttribute("flightcode", flightcode);
		request.setAttribute("dpt", dpt);
		request.setAttribute("arr", arr);
		return "policy/yPrice_list";
		
	}
	
	@RequestMapping("/addYPrice.do")
	public String addYPrice(HttpServletRequest request){
		return "policy/yPrice_add";
	}
	
	
	@RequestMapping("/editYPrice.do")
	public String editYPrice(HttpServletRequest request,String flightcode,String dpt,String arr) throws Exception{
		YPrice yPrice = policyService.getYPriceSingle(flightcode, dpt, arr);
		request.setAttribute("yPrice", yPrice);
		return "policy/yPrice_edit";
	}
	
	@RequestMapping("/saveYPrice.do")
	@ResponseBody
	public ResultData saveYPrice(HttpServletRequest request,YPrice yPrice) throws Exception{
		if(policyService.existYPrice(yPrice)){
			//已经存在相同记录
			/*private String code;
			private String info;
			private Object operation;
			private Object data;
			private boolean success;*/
			return new ResultData(ResultData.SUCCESS,"has same record");
		}else{
			try {
				yPrice = policyService.saveYPrice(yPrice);
				return new ResultData(ResultData.SUCCESS,null,null,yPrice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResultData(ResultData.ERROR);
			}
		}
		
	}
	
	@RequestMapping("/deleteYPrice.do")
	@ResponseBody
	public ResultData deleteYPrice(HttpServletRequest request,String id) throws Exception{
		if(policyService.deleteYPrice(id)){
			return new ResultData(ResultData.SUCCESS);
		}else{
			return new ResultData(ResultData.ERROR);
		}
	}
	
	@RequestMapping("/getChangePriceLogListByPolicy.do")
	public String getChangePriceLogListByPolicy(HttpServletRequest request,String id) throws Exception{
		List<ChangePriceLog> list = new ArrayList<ChangePriceLog>();
		list = policyService.getPriceLogByPolicyId(id);
		request.setAttribute("list", list);
		return "policy/changePriceLog_view";
	}
}
