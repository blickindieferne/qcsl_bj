<%@page import="com.xzy.qcsl.jp.bj.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${path}/resource/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${path}/resource/js/base.js"></script>
<link href="${path}/resource/css/main.css" rel="stylesheet" type="text/css"></link>
<title>启诚商旅-比价系统</title>

</head>
<body>
<div class="menu_left">
		<div><a href="${path}/policy/list.do">政策查询</a></div>
	<div><a href="${path}/bj/taskManager.do">任务管理</a></div>
	<div><a href="${path}/policy/YPriceList.do">Y舱价格</a></div>
	</div>
	<div class="opt_right">
  <div id="search_box" class="search_box">
    <form id="cond_search_form" name="cond_search_form" action="${path}/policy/YPriceList.do" method="post">
    <div class="title">Y舱价格</div>
    <div class="search_panel">
	      <table class="search_table">
	        <tr>
	          <td>航空公司：</td>
	          <td><input type="text" id="flightcode" name="flightcode" value="${flightcode}" /></td>
	          <td>起飞城市：</td>
	          <td><input type="text" id="dpt" name="dpt" value="${dpt}" /></td>
	        </tr>
	        <tr>
	          
	          <td>到达城市：</td>
	          <td colspan="3"><input type="text" id="arr" name="arr" value="${arr}" /></td>
	        </tr>
	      </table>
		      <input type="submit"  value="查询" style="margin-left: 100px;margin-bottom: 10px;"/>
		</div>

     
    </form>
  </div>
  <div id="list_box" class="list_box">
  	 <div class="toolBox" style="margin-top:10px;"> 
  	   		<input type="button" value="添加" onclick="add()"/>
          <!-- 
  		<input type="button" value="从Excel导入"/>
           -->
  	</div>
  	<div class="detailPanel" style="margin-top: 10px;">
  		<table height="100%" width="100%" class="form_table" cellpadding="0" cellspacing="0">
		      <tr>
		        <th>航空公司</th>
		        <th>起飞</th>
		        <th>到达</th>
		        <th>Y舱全价</th>
		        <th>操作</th>
		      </tr>
		      
		       <c:if test="${yPricelist.size()==0 }"><tr style="background-color: white;"><td align="center" colspan="5">无</td></tr></c:if>
               <c:if test="${yPricelist.size()!=0 }">
		      <c:forEach var="p" items="${yPricelist}" varStatus="status">
		        
		        	<tr style='${status.index%2 ==0?"background-color: white":"background-color: #f1f1f1"}'> 
			          <td align="center" width="20%">${p.flightcode}</td>
			          <td align="center" width="15%">${p.dpt}</td>
			          <td align="center" width="15%">${p.arr}</td>
			          <td align="center" width="20%">¥${p.price}</td>
                       
			          <td align="center" width="30%">
                        <a class="opt_a" href="javascript:edit('${path}/policy/editYPrice.do?flightcode=${p.flightcode}&dpt=${p.dpt}&arr=${p.arr}')">修改</a>
                        &nbsp;&nbsp;<a class="opt_a" href="javascript:del('${p.id}');">删除</a>
                      </td>
			        </tr>
		      </c:forEach>
		      </c:if>
		    </table>
  	</div>
		    
  </div>
  </div>
  <script type="text/javascript">
  function add(){
	  $portal.openPage("${path}/policy/addYPrice.do",function(flag){
		  if(flag){
			  location.reload();
		  }
	  });
	  //window.location.href="${path}/policy/addYPrice.do";
  }
  
  function edit(url){
	  $portal.openPage(url,function(flag){
		  if(flag){
			  location.reload();
		  }
	  });
	  
  }
  
  function del(id){
	  if(!confirm("确定要删除吗?")){
		  return;
	  }
	  $.ajax({
		  type:"post",
		  url:"${path}/policy/deleteYPrice.do?id="+id,
		  success:function(data){
			  location.reload();
			  /**
			  if(data){
				  if(data.code=="success"){
					  alert("删除成功");
					  location.reload();
				  }else{
					  alert("删除失败");
				  }
			  }else{
				  alert("操作失败失败");
			  }
			  **/
		  } ,
		  error:function(){
			  alert("操作失败失败");
		  }
	  })
  }
  </script>
</body>
</html>