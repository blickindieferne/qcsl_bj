package com.xzy.qcsl.jp.bj.util;

import java.sql.Statement;

/**
 * @author xiangzy
 * @date 2015-6-28
 *
 */
public class StatementUtil {

	public static void close(Statement stmt){
		if(stmt!=null){
			try{
				stmt.close();
			}catch(Exception e){
				
			}
		}
	}

}
