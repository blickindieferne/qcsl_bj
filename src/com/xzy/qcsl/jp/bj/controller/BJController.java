package com.xzy.qcsl.jp.bj.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzy.qcsl.jp.bj.model.User;
import com.xzy.qcsl.jp.bj.task.BJTask;
import com.xzy.qcsl.jp.bj.task.TaskManager;

@Controller
@RequestMapping("/bj/*")
public class BJController {
	

	
	@RequestMapping("/startBJTask.do")
	@ResponseBody
	public ResultData startTask(HttpServletRequest request,int interval,int intmin){
		
		Object obj = request.getSession().getAttribute("user");
		if(obj==null){
			return new ResultData(ResultData.ERROR, "session失效，请从新登陆", null,null);
		}
		String username = ((User)obj).getUsername();
		String cookie = request.getSession().getAttribute("cookie").toString();
		String domain = request.getSession().getAttribute("domain").toString();
		if(TaskManager.startTask(username,domain,cookie,interval,intmin)){
			return new ResultData(ResultData.SUCCESS, "启动任务成功", null,null);
		}else{
			return new ResultData(ResultData.SUCCESS, "任务正在运行", null,null);
		}
		
	}
	
	@RequestMapping("/stopBJTask.do")
	@ResponseBody
	public ResultData stopTask(HttpServletRequest request){
		Object obj = request.getSession().getAttribute("user");
		if(obj==null){
			return new ResultData(ResultData.ERROR, "session失效，请从新登陆", null,null);
		}
		String username = ((User)obj).getUsername();
		TaskManager.stopTask(username,true);
		
		return new ResultData(ResultData.SUCCESS, "停止任务成功", null,null);
	}
	
	@RequestMapping("/taskManager.do")
	public String taskManager(HttpServletRequest request){
		String page = "/bj/task_manager";
		Object obj = request.getSession().getAttribute("user");
		if(obj==null){
			return page;
		}
		String username = ((User)obj).getUsername();
		
		BJTask task = TaskManager.getTask(username);
		if(task==null){
			request.setAttribute("run", false);
			request.setAttribute("interval",0);
			request.setAttribute("intmin",1);
			return page;
		}
		
		request.setAttribute("interval", task.getInterval()); 
		request.setAttribute("intmin", task.getIntmin()); 
		request.setAttribute("run", task.isRun());
		request.setAttribute("pause", task.isPause());
		request.setAttribute("roundCount", task.getRoundCount());
		request.setAttribute("succedTaskCount", task.getSuccedTaskCount());
		request.setAttribute("changedCount", task.getChangedCount());
		request.setAttribute("invalidPolicyCount", task.getInvalidPolicyCount());
		request.setAttribute("noQuerySellPrice", task.getNoQuerySellPrice());
		request.setAttribute("failedTaskCount", task.getFailedTaskCount());
		request.setAttribute("roundTaskLogList", task.getRoundTaskLogList());
		
		
		return page;  
	}
}