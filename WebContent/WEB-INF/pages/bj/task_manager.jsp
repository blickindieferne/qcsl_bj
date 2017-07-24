<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="10">
<script type="text/javascript" src="${path}/resource/js/jquery-2.1.4.min.js"></script>
<link href="${path}/resource/css/main.css" rel="stylesheet" type="text/css"></link>
<title>启诚商旅-比价系统</title>

</head>
<body>

	<div class="menu_left">
		<div><a href="${path}/policy/list.do">政策查询</a></div>
	<div><a href="${path}/bj/taskManager.do">任务管理</a></div>
	<div><a href="${path}/policy/YPriceList.do">Y舱价格</a></div>
	</div>
  
	<div class="opt_right" style="border-top: 1px solid #ccc;border-bottom: 1px solid #ccc;background: #f4f4f4;margin-bottom: 10px;padding: 0 10px;
	padding-bottom: 10px;">
  <div class="title" style="width: 90px;">任务管理</div>
		<br /> <br /> 每执行完成一轮比价任务暂停 <input style="width: 50px" id="interval"
			name="interval" value="${interval}" />分钟，每条政策暂停 <input style="width: 50px" id="intmin" name="intmin" value="${intmin}" />秒，只会对本人设置的底价进行比较 <br /> <br />
		<button onclick="startTask()" ${run?'disabled="disabled"':''}>开始比价任务</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button onclick="stopTask()" ${run?'':'disabled="disabled"'}>停止比价任务</button>
		<br /> <br />

		<table>

			<tr>
				<td>任务状态 ：${run?'运行中':'停止'}&nbsp;&nbsp;${run&&pause?'暂停':'' }</td>
				<td>
					<button onclick="location.reload(true)">&nbsp;&nbsp;刷新&nbsp;&nbsp;</button>&nbsp;每隔10秒自动刷新一次
				</td>
			</tr>
			<tr>
				<td style="color: red">本轮任务信息</td>
				<td></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;成功比价数：${succedTaskCount}</td>
				<td>&nbsp;&nbsp;其中改价数：${changedCount}</td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;失效政策数：${invalidPolicyCount}</td>
				<td></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;未查询到同行低价数：${noQuerySellPrice}</td>
				<td></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;失败条数：${failedTaskCount}</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>

		<table>
			<tr>
				<td style="color: red">最近100轮比价日志（红色表示有改价；停止任务后，轮数从0开始计算）</td>
			</tr>
			<c:forEach var="log" items="${roundTaskLogList}" varStatus="status">
				<tr>
					<td>&nbsp;&nbsp;${log}</td>
				</tr>
			</c:forEach>

		</table>
	</div>


	<script>
		function startTask() {
			var interval = $("#interval").val();
			if (!/^(0|[1-9]\d*)$/.test(interval)) {
				alert("时间间隔应大于或等于0的整数");
				return;
			}
			
			var intmin = $("#intmin").val();
			if (!/^(0|[1-9]\d*)$/.test(intmin)) {
				alert("时间间隔应大于或等于0的整数");
				return;
			}
			$.ajax({
				url : '${path}/bj/startBJTask.do',
				data : {
					interval : interval,
					intmin:intmin
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					location.reload(true);
				},
				error : function(data) {
					alert(data.status + "\n" + data.statusText);
				}
			});

		}
		function stopTask() {
			$.ajax({
				url : '${path}/bj/stopBJTask.do',
				data : {},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					location.reload(true);
				},
				error : function(data) {
					alert(data.status + "\n" + data.statusText);
				}
			});

		}
	</script>
</body>
</html>