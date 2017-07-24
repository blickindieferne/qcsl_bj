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
<body style="height: 100%;">
<div class="menu_left">
		<div><a href="${path}/policy/list.do">政策查询</a></div>
	<div><a href="${path}/bj/taskManager.do">任务管理</a></div>
	<div><a href="${path}/policy/YPriceList.do">Y舱价格</a></div>
	</div>
	<div class="opt_right">
  <div id="search_box" class="search_box" >
    <form id="form1" name="form1" action="${path}/policy/saveYPrice.do" method="post">
     <div class="title" style="width: 82px;">添加Y舱价格</div>
     <div style="width: 82px;"><a class="opt_a" href="javascript:back()">返回列表</a></div>
    <div class="search_panel" style="margin-top: 10px;">
	      <table class="search_table">
	        <tr>
	          <td>航空公司：</td>
	          <td><input type="text" id="flightcode" name="flightcode" /></td>
	         <td>起飞城市：</td>
	          <td><input type="text" id="dpt" name="dpt" /></td>
	        </tr>
	        <tr>
	        	<td>到达城市：</td>
	          <td><input type="text" id="arr" name="arr"/></td>
	         <td>Y舱价格：</td>
	          <td><input type="text" id="price" name="price" /></td>
	        </tr>
	       
	       
	      </table>
	      <input type="button" value="保存" onclick="save()" style="margin-bottom: 10px;margin-left: 220px;"/>
		</div>

     
    </form>
  </div>
  </div>
 
  
  <script type="text/javascript">
  function back(){
	  
	  $portal.closePage(window._saveFlag);
  }
  function save(){
	  if(!/^[0-9]*[1-9][0-9]*$/.test($("#price").val())){
		  
		  alert(" Y舱价格是大于0的整数");
		  return;
	  }
	  $form.ajaxSubmit("form1",function(data){
			if(data.code){
				if(data.info=="has same record"){
					alert("已经存在相同记录");
				}else 
				if(data.data){
					window._saveFlag = true;
					alert("保存成功");
				}else{
					alert("保存失败");
				}
			}else{
				alert("保存失败");
			}
		});
  }

  </script>
</body>
</html>