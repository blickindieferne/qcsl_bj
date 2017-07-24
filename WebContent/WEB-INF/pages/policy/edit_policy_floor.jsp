<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-y: hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${path}/resource/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${path}/resource/js/base.js"></script>
<link href="${path}/resource/css/main.css" rel="stylesheet" type="text/css"></link>
<title>启诚商旅-比价系统</title>

</head>


<body style="margin-top: 10px;">
  <div class="menu_left">
    <div>
      <a href="${path}/policy/list.do">政策查询</a>
    </div>
    <div>
      <a href="${path}/bj/taskManager.do">任务管理</a>
    </div>
    <div>
      <a href="${path}/policy/YPriceList.do">Y舱价格</a>
    </div>
  </div>
  <div class="opt_right">

    <div class="opt_panel">
      <div class="title" style="width: 90px;">底价操作日志</div>
      <div style="margin-top: 10px; margin-bottom: 10px;">
        <a href="javaScript:backHistory();">返回到列表</a>
      </div>
      <c:if test="${policyIds==null }">
        <form id="form1" action="${path}/policy/savePoicyFloor.do" method="post">
          <!-- 非批量 -->
      </c:if>
      <c:if test="${policyIds!=null }">
        <form id="form1" action="${path}/policy/savePoicyFloorBatch.do" method="post">
          <!-- 批量 -->
      </c:if>
      <input id="policyId" name="policyId" value="${policyFloor.policyId}" type="hidden" /> 
      <input id="id" name="id" value="${policyFloor.id}" type="hidden" />
      <input id="policyIds" name="policyIds" value="${policyIds}" type="hidden" /> 
      <input id="ptype" name="ptype" value="${policy.ptype}" type="hidden" /> 
      <table cellpadding="5">
        <tr>
          <td><input id="type0" name="type" type="radio" value="0" ${policyFloor.type==0?"checked='checked'":""} />固定底价<br/><input id="type1" name="type" type="radio" value="1" ${policyFloor.type==1?"checked='checked'":""} />折扣底价</td>
          <td><input id="floorPrice" name="floorPrice" value="${policyFloor.floorPrice}" style="width:60px" /><input id="floorPoint" name="floorPoint" value="${policyFloor.floorPoint}" style="width:60px" /><span id="floorPointSpan">%</span></td>
          <td> 低于同行多少: <input id="under" name="under" value="${policyFloor.under}" style="width:60px" /></td>
          <td><input id="needSetCPA" name="needSetCPA" type="checkbox" ${policyFloor.needSetCPA?"checked='checked'":""} /><span id="cpaLabel">CPA</span><br><input id="needSetCPC" name="needSetCPC" type="checkbox" ${policyFloor.needSetCPC?"checked='checked'":""} /><span id="cpcLabel">CPC</span></td>
          <td>CPC差价:<input id="cpaDcpc" name="cpaDcpc" value="${policyFloor.cpaDcpc}" style="width:60px"/></td>
          <td><input id="compareCabin" name="compareCabin" type="checkbox" ${policyFloor.compareCabin?"checked='checked'":""} />比较舱位</td>
          <td><input id="status1" name="status" type="radio" value="true" ${policyFloor.status?"checked='checked'":""} />有效<br/><input id="status0" name="status" type="radio" value="false" ${policyFloor.status?"":"checked='checked'"} />无效</td>
          <td><input type="button" value="保存" onclick="_save()" /></td>
        </tr>

      </table>

      
      </form>
    </div>
    <div class="log_panel">
      <div style="border: 1px solid #ccc; margin-bottom: 10px; width: 700px; min-width: 600px;">
        <table height="100%" width="100%" class="form_table" cellpadding="0" cellspacing="0">
          <tr>
            <th width="150px">时间</th>
            <th>操作者</th>
            <th>动作</th>
            <th>旧值</th>
            <th>新值</th>
          </tr>
          <c:if test="${logList.size()==0 }">
            <tr style="background-color: white;">
              <td align="center" colspan="5">无</td>
            </tr>
          </c:if>
          <c:if test="${logList.size()!=0 }">
            <c:forEach items="${logList }" var="log" varStatus="status">
              <tr style='${status.index%2 ==0?"background-color: white":"background-color: #f1f1f1"}'>
                <td align="center"><fmt:formatDate value="${log.date}" type="time" pattern="yyyy-MM-dd H:m:s" /></td>
                <td align="center">${log.username }</td>
                <td align="center">${log.action==0?'新增':'修改' }</td>
                <td align="center">${log.oldValue }</td>
                <td align="center">${log.newValue }</td>
              </tr>
            </c:forEach>
          </c:if>
        </table>
      </div>
      <!--   <a href="javaScript: location.reload() ;">重新加载</a> -->
    </div>

    <div class="show_panel">
      <iframe id="ifrm" width="100%" height="400" src="http://fuwu.qunar.com/npolicy/policy/action/view?domain=${domain}&act=0&ptype=${policy.ptype}&id=${policy.id}" style="border: 0"> </iframe>
    </div>
  </div>
  <script>
	$(function(){
		if('${policyIds}'!=null && '${policyIds}'!='null' && '${policyIds}'!='' ){
			$(".show_panel").hide();
			$(".log_panel").hide();
		}
		
		if(${policyFloor.type}==0){
			 $("#floorPoint").hide();
			 $("#floorPointSpan").hide();
		}else{
			$("#floorPrice").hide();
		}
		
		 $(":input[name='type']").click(function(){
			 if(this.id=="type0"){
				 $("#floorPrice").show();
				 $("#floorPoint").hide();
				 $("#floorPointSpan").hide();
			 }else{
				 $("#floorPrice").hide();
				 $("#floorPoint").show();
				 $("#floorPointSpan").show();
			 }
		 });
		
	})
	function _save(){
		var dj = $("#floorPrice").val();
		var under = $("#under").val();
		var isCPA = $("#needSetCPA")[0].checked;
		var isCPC = $("#needSetCPC")[0].checked;
		var status = $("input[name='status']:checked").val();
		var floorPoint= $("#floorPoint").val();
		var type = $("input[name='type']:checked").val();
		var compareCabin = $("#compareCabin")[0].checked;
		var cpaDcpc = $("#cpaDcpc").val();
		//保存底价时，如果所有值都没有变化，不应该修改以及增加修改日志
		if(('${policyFloor.id}'!='' && 
				 ('${policyFloor.floorPrice}'!= dj 
					|| '${policyFloor.under}'!= under 
					|| ${policyFloor.needSetCPA}!= isCPA
					|| ${policyFloor.needSetCPC}!= isCPC
					|| '${policyFloor.status}'!= status
					|| ${policyFloor.type}!= type
					|| ${policyFloor.floorPoint}!= floorPoint
					|| ${policyFloor.compareCabin}!=compareCabin
					|| ${policyFloor.cpaDcpc}!=cpaDcpc
				 )
			) || '${policyFloor.id}'==''){
			 var ok = true;
				if(!$("#needSetCPA")[0].checked && !$("#needSetCPC")[0].checked){
					/* $("#note_content").show(); */
					$("#cpaLabel").css("background-color","#FF0000");
					$("#cpcLabel").css("background-color","#FF0000");
					$("#cpaLabel").attr("title","CPA、CPC必选其一");
					$("#cpcLabel").attr("title","CPA、CPC必选其一");
					ok = false;
				}
				
				if(!/^[0-9]+[0-9]*]*$/.test($("#floorPrice").val())){
					$("#floorPrice").css("background-color","#FF0000");
					$("#floorPrice").attr("title","请输入大于等于0的整数");
					ok = false;
				}else{
					$("#floorPrice").css("background-color","");
				}
				
				if(!/^[0-9]([0-9])?$/.test($("#floorPoint").val()) ){
					$("#floorPoint").css("background-color","#FF0000");
					$("#floorPoint").attr("title","请输0到99的整数");
					ok = false;
				}else{
					$("#floorPoint").css("background-color","");
				}
				
				if(!/^[1-9]+[0-9]*]*$/.test($("#under").val())){
					$("#under").css("background-color","#FF0000");
					$("#under").attr("title","请输入大于0的整数");
					ok = false;
				}else{
					$("#under").css("background-color","");
				}
				
				if(!/^(-)?\d+$/.test(cpaDcpc)){
					$("#cpaDcpc").css("background-color","#FF0000");
					$("#cpaDcpc").attr("title","请输入一个整数");
					ok = false;
				}else{
					$("#cpaDcpc").css("background-color","");
				}
				
				
				if(ok){
					//保存
					$form.ajaxSubmit("form1",function(data){
						if(data){
							/**
							alert("保存成功");
							<c:if test="${policyIds==null }">
								location.reload() ;
							</c:if>
							**/
							alert("保存成功");
							backHistory();
						}else{
							alert("保存失败");
						}
					});
				}
		}else{
			return ;
		}
	 
	}
	
	//返回列表页
	function backHistory(){
		$portal.closePage();
	}
	
	
</script>
</body>
</html>