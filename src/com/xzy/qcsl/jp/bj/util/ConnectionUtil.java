package com.xzy.qcsl.jp.bj.util;

import java.sql.Connection;

/**
 * @author xiangzy
 * @date 2015-6-28
 *
 */
public class ConnectionUtil {

	public static void close(Connection conn){
		if(conn!=null){
			try{
			conn.close();
			}catch(Exception e){
				
			}
		}
		
	}

}
