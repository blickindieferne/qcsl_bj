package com.xzy.qcsl.jp.bj.task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangzy
 * @date 2015-6-29
 *
 */
public class TaskManager {

	 private static Map<String, BJTask> taskMap = new HashMap<String, BJTask>();
	 
	 
	 public static BJTask getTask(String username){
	
		return taskMap.get(username);
	 }


	public static boolean stopTask(String username) {
		
		return stopTask(username,false);
	}
	
	public static boolean stopTask(String username,boolean force) {
		BJTask task = taskMap.get(username);
		if(task!=null){
			task.stop();
			if(force && task.isPause()){
				Thread t = task.getThread();
				if(t!=null){
					try{
						t.interrupt();
					}catch(Exception e){
						e.printStackTrace();
					}
			
				}
			}
		}
		
	
		
		return true;
		
	}
	
	public static boolean startTask(String username,String domain,String cookie, int interval, int intmin) {
		BJTask task = taskMap.get(username);
		
		 if(task==null){
			 task = new BJTask(username,domain,cookie,interval,intmin);
			 taskMap.put(username, task);
		 }
		 task.setInterval(interval);
		 task.setIntmin(intmin);
		 if(!task.isRun()){
			 task.start();
			 return true;
		 }else{
			 return false;
		 }
		
	}
}
