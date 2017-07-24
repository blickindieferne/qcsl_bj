package com.xzy.qcsl.jp.bj.listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.h2.tools.Server;

/**
 * @author xiangzy
 * @date 2015-6-27
 * 
 */
public class H2DBServerStartListener implements ServletContextListener {

	// H2数据库服务器启动实例
	private Server server;
	
	private static Logger log = Logger.getLogger(H2DBServerStartListener.class);

	/*
	 * Web应用初始化时启动H2数据库
	 */
	public void contextInitialized(ServletContextEvent sce) {
		
		try {
			log.info("正在启动h2数据库...");
			// 使用org.h2.tools.Server这个类创建一个H2数据库的服务并启动服务，由于没有指定任何参数，那么H2数据库启动时默认占用的端口就是8082
			server = Server.createTcpServer().start();
			log.info("h2数据库启动成功...");
		} catch (SQLException e) {
			log.error("启动h2数据库出错：",e);
			throw new RuntimeException(e);
		}
	}

	/*
	 * Web应用销毁时停止H2数据库
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		if (this.server != null) {
			// 停止H2数据库
			this.server.stop();
			this.server = null;
		}
	}
}