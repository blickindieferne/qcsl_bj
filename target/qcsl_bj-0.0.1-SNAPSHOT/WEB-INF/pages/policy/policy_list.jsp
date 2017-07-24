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

<script type="text/javascript" src="${path}/resource/js/My97DatePicker/WdatePicker.js"></script>
<link href="${path}/resource/css/main.css" rel="stylesheet" type="text/css"></link>
<title>启诚商旅-比价系统</title>

</head>
<body>
	<div class="menu_left">
		<div><a href="${path}/policy/list.do">政策查询</a></div>
	<div><a href="${path}/bj/taskManager.do">任务管理</a></div>
	<div><a href="${path}/policy/YPriceList.do">Y舱价格</a></div>
	</div>
    <span  style="float:right;color:blue;font:bold;font-size: 18px"><c:out value="${user.username}"/>&nbsp; <c:out value="${domain}"/>&nbsp;<a href="${path}/">退出</a></span>
	<div class="opt_right">
  	<div id="search_box" class="search_box">
    <form id="cond_search_form" name="cond_search_form" action="${path}/policy/list.do" method="post">
    <div class="title">政策查询</div>
    <div class="search_panel">
   
	      <table class="search_table">
            
	        <tr>
	          <td>政策号：</td>
	          <td><input type="text" id="friendlyId" name="friendlyId" value="${queryPolicy.friendlyId}" /></td>
	          <td>政策代码：</td>
	          <td><input type="text" id="policyCode" name="policyCode" value="${queryPolicy.policyCode}" />
	          	&nbsp;
	        </tr>
	        <tr>
	          <td>舱位代码：</td>
	          <td><input type="text" id="cabin" name="cabin" value="${queryPolicy.cabin}" /></td>
	          <td>航空公司代码：</td>
	          <td><input type="text" id="flightcode" name="flightcode" value="${queryPolicy.flightcode}" /></td>
	        </tr>
	        <tr>
	          <td>起飞城市：</td>
	          <td><input type="text" id="dpt" name="dpt" value="${queryPolicy.dpt}" /></td>
	          <td>到达城市：</td>
	          <td><input type="text" id="arr" name="arr" value="${queryPolicy.arr}" /></td>
	        </tr>
	        <tr>
	          <td>销售日期：</td>
	          <td>
	          <input type="text" id="startdate_ticket" name="startdate_ticket" value="${queryPolicy.startdate_ticket}" 
	           onClick="WdatePicker()"/></td>
	          <td>旅行日期：</td>
	          <td><input type="text" id="startdate" name="startdate" value="${queryPolicy.startdate}" onClick="WdatePicker()"/></td>
	        </tr>
	        <tr>
	        
	          <td>政策类型：</td>
	          <td><select id="policy" name="policy">
	              <option label="全部" value=""></option>
	              <option label="普通 政策" value="0" ></option>
	              <option label="特价 政策" value="1"></option>
	              <option label="申请产品 政策" value="3"></option>
	              <option label="包机切位 政策" value="5"></option>
	              <option label="预付产品 政策" value="6"></option>
	
	          </select></td>
	          <td>状态：</td>
	          <td><select id="status" name="status">
	              <option selected="selected" label="有效" value="3"></option>
	          </select></td>
	        </tr>
          
          <!-- 
	        <tr>
	          <td>预设底价</td>
	          <td><select id="hasYsdj" name="hasYsdj">
	              <option label="全部" value="2"></option>
	              <option label="已设" value="1"></option>
	              <option label="未设" value="0"></option>
	          </select></td>
	          <td>是否改动：</td>
	          <td><select id="sfgd" name="sfgd">
	              <option label="全部" value="2"></option>
	              <option label="是" value="1"></option>
	              <option label="否" value="0"></option>
	          </select></td>
	        </tr>
           -->
          
	      </table>
	      
	       	<input type="hidden" id="recordCount" name="recordCount" value="${pager1.recordCount}" /> 
		      <input type="hidden" id="pageNo" name="pageNo" value="${pager1.pageNo}" /> 
		      <input type="button"  value="查询" onclick="changePage(1)" style="margin-left: 100px;margin-bottom: 10px;"/>
           <span style="float:right;color:blue">如果没有数据，请确认平台和Cookie是否正确</span>
		</div>

     
    </form>
  </div>
  <div id="list_box" class="list_box">
  <!-- 
  	<div>使用如下四种之一的组合查询政策，才能使用全选所有政策功能</div>
  	<ol>
  		<li>
  			<span class="hightlight">政策代码 +</span>
  			其他检索条件；
  		</li>
  		<li>
  			<span class="hightlight">起飞城市 + 到大城市</span>
  			其他检索条件；
  		</li>
  		<li>
  			<span class="hightlight">航空公司代码 + 舱位</span>
  			其他检索条件；
  		</li>
  		<li>
  			<span class="hightlight">单一政策类型 + </span>
  			其他检索条件。
  		</li>
  	</ol>
   -->
  	<div class="detailPanel" style="border: 1px solid #ccc;margin-bottom: 10px;">
  		<table height="100%" width="100%" class="form_table" cellpadding="0" cellspacing="0">
		      <tr>
		      	<th>
		      		<input type="checkbox" id="checkAll"/>
		      	</th>
		        <th>航空<br />公司
		        </th>
		        <th>起飞<br />到达
		        </th>
		        <th>航班</th>
		        <th>舱位</th>
		        <th>政策类型</th>
		        <th>政策状态</th>
		        <th>旅行有效期</th>
		        <th>销售有效期</th>
		        <th>票面价<br />/Y舱折扣
		        </th>
		        <th>政策代码</th>
		        <th><a>预设底价</a></th>
		        <th><a>改价日志</a></th>
		      </tr>
		      <c:forEach var="p" items="${policyList}" varStatus="status">
		        
		        	<tr  style='${status.index%2 ==0?"background-color: white":"background-color: #f1f1f1"}'> 
		         	  <td align="center" >
		         	  	<input type="checkbox" value="${p.id}" class="checkItem" ptype="${p.ptype}" policyType="${p.policyStr}"/>
		         	  </td>
			          <td align="center">${p.flightcode}</td>
			          <td align="center">${p.dptArr}</td>
			          <td align="center">${p.flightNumLimit}</td>
			          <td align="center">${p.cabin}</td>
			          <td align="center">${p.policyStr}</td>
			          <td align="center">${p.status}</td>
			          <td align="center">${p.startToEndDate}</td>
			          <td align="center">${p.startToEndDate_ticket}</td>
			          <td align="center">${p.sellpriceOrdiscount}</td>
			          <td align="center">${p.policyCode}</td>
			          <td align="right">
                        <c:if test="${p.ptype!='0'}"> <!-- 普通政策不设置 -->
                            <c:if test='${p.floor!=""}'>
                              <span style="color:red">${p.floor}</span>&nbsp;
                            </c:if>
                            <a href="javaScript:setDJ('${p.id }','${p.ptype }')">设置</a>
                        </c:if>
                      </td>
			          <td align="center" width="60px">
                      <c:if test="${p.ptype!='0'}"> <!-- 普通政策不设置 -->
                        <a href="${path}/policy/getChangePriceLogListByPolicy.do?id=${p.id }">查看</a>
                      </c:if>
                      </td>
			        </tr>
		      </c:forEach>
		      <tr>
		      <td></td>
		      <td align="left"><input type="button" id="batchSetBtn" value="批量设置" onclick="setAll()"/></td>
		      <td colspan="11" align="right">
		      	<div class="pager" style="padding: 10px;float: right;">
				    <a onclick="changePage(1)" href="javascript:void(0)">首页</a>&nbsp; 
				    <a onclick="changePage(${pager1.pageNo}-1)" href="javascript:void(0)">上页</a>&nbsp; 
				    <span>第${pager1.pageNo}页</span> 
				    <a onclick="changePage(${pager1.pageNo}+1)" href="javascript:void(0)">下页</a>&nbsp; 
				    <a onclick="changePage(${pager1.totalPage})" href="javascript:void(0)">末页</a>&nbsp; 
				    <span>共${pager1.totalRecord}条/${pager1.totalPage}页</span> 
				    <span>每页
				    	<select id="sel_recordCount" name="sel_recordCount" onchange="changeRecordCount()">
				    		<option label="10" value="10"></option>
				    		<option label="30" value="30"></option>
				    		<option label="60" value="60"></option>
				    	</select>
				   	 条 </span>
		  		</div>
		      </td>
		      </tr>
		    </table>
  	</div>
		    
  </div>
  </div>
  
  <script type="text/javascript">
     function changePage(num) {
    	 var totalpage = '${pager1.totalPage}';
         if(num<1){
           num=1;
         }else if(num>totalpage){
           num = ${pager1.totalPage};
         }
         var frm = document.getElementById('cond_search_form');
         frm.pageNo.value = num;
  
         $("#cond_search_form").submit();
     }
     
     var policyIds = "";//用于选中政策，进行批量设置底价
     var itemCounts = 0;//查询出来的政策数目
     
     $(function(){
    	 itemCounts = ${policyList.size()};
    	//政策类型下拉
    	 var rcd = '${pager1.recordCount}';
    	 $("#sel_recordCount").find("option[value="+rcd+"]").attr("selected",true);
    	 
    	 if('${queryPolicy.policy}'!=''){
    		//政策类型下拉
        	 var ply = '${queryPolicy.policy}';
        	 $("#policy").find("option[value="+ply+"]").attr("selected",true);
    	 }
    	 
    	 // chkAll全选事件
         $("#checkAll").bind("click", function () {
        	 if($(this)[0].checked){
        		 $("[class='checkItem']").each(function(){
        			this.checked = true; 
        		 });
        		 /* //批量设置按钮可用
           		$("#batchSetBtn").attr('disabled',false); */
        	 }else{
        		 $("[class='checkItem']").removeAttr("checked");
        		 //批量按钮不可用
           		//$("#batchSetBtn").attr('disabled',true);
        	 }
        
         });
    	 
         $(".checkItem").bind("click", function () {
        	 
        	 var selItemCount = checkSelItem();
        	 /* if(selItemCount>1){
        		 //批量设置按钮可用
           		$("#batchSetBtn").attr('disabled',false);
        	 }else{
        		 //批量设置按钮可用
           		$("#batchSetBtn").attr('disabled',true);
        	 }
        	  */
        	 //没有宣布选择的时候，取消全选按钮的选中
        	 if(itemCounts!=selItemCount){
        		 $("[id='checkAll']").removeAttr("checked");
        	 }else{
        		 $("[id='checkAll']").checked = true; 
        	 }
         })
        
     })
    
     function checkSelItem(){
    	 var itemCount = 0;
    	 $("[class='checkItem']:checkbox").each(function(){
	         	if ($(this).is(':checked')) { 
	         		itemCount++;
	         	} 
     	 })
     	 
     	 return itemCount;
     }
    function changeRecordCount(){
    	var val = $("#sel_recordCount").val();
    	$("#recordCount").val(val);
    	$("#cond_search_form").submit();
    }
    
    //批量设置低价
    function setAll(){
    	selItemCount = checkSelItem();
    	
    	var policyTypes = [];//被选中的所有政策的类型
    	var ptype = "";
    	 $("[class='checkItem']:checkbox").each(function(){
	         if ($(this).is(':checked')) { 
	         	policyIds += $(this).attr('value')+',';
	         	$(this).attr('policyType');
	         	policyTypes.push($(this).attr('policyType'));
	         	ptype = $(this).attr('ptype');
	         }
    	 }
    	 )
    	 var url = "${path}/policy/editPoicyFloor.do?ids="+policyIds+"&queryPolicy=${queryPolicy}&page=${page1}&ptype="+ptype; 
    	 if(policyIds==null || policyIds=="" || selItemCount<2){
    		 alert("批量修改请至少选择两条政策");
    		 return ;
    	 }
    	
    	 //判断批量选择的政策是否是同一类型
    	 if(selItemCount>1){
    		for (var i = 0; i < policyTypes.length-1; i++) {
				var firstLx = policyTypes[i];
				for (var k = i+1; k < policyTypes.length-1; k++) {
					var tempLx = policyTypes[k];
					if(firstLx!=tempLx){
						alert("批量修改时必须选择同一类型的政策");
						return;
					}
				}
			}
    	 }
    	 $portal.openPage(url,function(){
    		 changeRecordCount();
 		});
    }
    
    
    //设置底价（单独一条）
    function setDJ(id,ptype){
    	var url = "${path}/policy/editPoicyFloor.do?id="+id+"&ptype="+ptype;
    	$portal.openPage(url,function(){
    		changeRecordCount();
		});
    }
   
     </script>

</body>
</html>