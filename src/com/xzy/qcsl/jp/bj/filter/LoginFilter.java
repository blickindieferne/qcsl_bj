package com.xzy.qcsl.jp.bj.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub 
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		HttpServletResponse httpresponse = (HttpServletResponse)response;
		HttpSession session=httpRequest.getSession();
		Object cookie = session.getAttribute("cookie");
		Object user = session.getAttribute("user");
		if((cookie != null && user!=null) 
				|| httpRequest.getRequestURL().toString().indexOf("login.do")>0
				|| httpRequest.getRequestURL().toString().indexOf("logout.do")>0
				|| httpRequest.getRequestURL().toString().indexOf("doLogin.do")>0
				){
			//清理缓存
			httpresponse.setDateHeader("Expires", -1);
			httpresponse.setHeader("Cache-Control", "no-cache");
			httpresponse.setHeader("Pragma", "no-cache");
			chain.doFilter(request,response);
		}else{
			//超时或未登录
			String requestType = httpRequest.getHeader("X-Requested-With");  
			if("XMLHttpRequest".equals(requestType)){
				//AJAX异步请求
				PrintWriter out = httpresponse.getWriter();
				out.print("sessionMiss");
				return;
			}else{
				//普通请求
				java.io.PrintWriter out = response.getWriter(); 
			    out.println("<html>");  
			    out.println("<script>");  
			    out.println("window.open ('"+request.getServletContext().getContextPath()+"/logout.do','_top')");  
			    out.println("</script>");  
			    out.println("</html>");  
				httpresponse.setHeader("Pragma","No-cache"); 
				httpresponse.setHeader("Cache-Control","no-cache"); 
				httpresponse.setContentType("text/html; charset=utf-8");
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}