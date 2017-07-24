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
    <div id="search_box" class="search_box">
      <div class="title" style="width: 82px;">改价日志</div>
           <div style="width: 82px;"><a class="opt_a" href="javascript:history.back();">返回列表</a></div>
      <div style="padding: 5px; border:">
        <table height="100%" width="100%" class="form_table" cellpadding="0" cellspacing="0">
          <tr>
            <th>修改时间</th>
            <th>修改人 </th>
            <th>CPA返点</th>
            <th>CPA留钱</th>
            <th>CPC返点</th>
            <th>CPC留钱</th>
           
          </tr>
          <c:forEach items="${list}" var="li" varStatus="status">
            <tr style='${status.index%2 ==0?"background-color: white":"background-color: #f1f1f1"}'>
              <td align="center">${li.log_date }</td>
              <td align="center">${li.user_name }</td>
              <td align="center"><c:choose>
                  <c:when test="${li.new_returnpoint != '' and li.new_returnpoint != li.old_returnpoint}">
                    <span style="color:red">${li.old_returnpoint} -> ${li.new_returnpoint}</span>
                  </c:when>
                  <c:otherwise>
                    ${li.old_returnpoint}
                  </c:otherwise>
                </c:choose></td>
              <td align="center"><c:choose>
                  <c:when test="${li.new_returnprice != '' and li.new_returnprice != li.old_returnprice}">
                     <span style="color:red">${li.old_returnprice} -> ${li.new_returnprice}</span>
                  </c:when>
                  <c:otherwise>
                    ${li.old_returnprice}
                  </c:otherwise>
                </c:choose></td>
              <td align="center"><c:choose>
                  <c:when test="${li.new_cpc_returnpoint != '' and li.new_cpc_returnpoint != li.old_cpc_returnpoint}">
                    <span style="color:red">${li.old_cpc_returnpoint} -> ${li.new_cpc_returnpoint}</span>
                  </c:when>
                  <c:otherwise>
                    ${li.old_cpc_returnpoint}
                  </c:otherwise>
                </c:choose></td>
              <td align="center"><c:choose>
                  <c:when test="${li.new_cpc_returnprice != '' and li.new_cpc_returnprice != li.old_cpc_returnprice}">
                     <span style="color:red">${li.new_cpc_returnprice} -> ${li.new_returnprice}</span>
                  </c:when>
                  <c:otherwise>
                    ${li.old_cpc_returnprice}
                  </c:otherwise>
                </c:choose></td>

            </tr>
          </c:forEach>
        </table>
      </div>

    </div>
  </div>

  <script type="text/javascript">
			$(function() {
				$("#form1 :input").change(function() {
					$("#form1").data("changed", true);
				});

			})
			function save() {
				//判断是否修改值
				if ($("#form1").data("changed")) {
					$form.ajaxSubmit("form1", function(data) {
						if (data.code) {
							if (data.info == "has same record") {
								alert("已经存在相同记录");
							} else if (data.data) {
								alert("保存成功");
							} else {
								alert("保存失败");
							}
						} else {
							alert("保存失败");
						}
					});
				} else {
					alert("信息未发生修改");
				}

			}

			function backList() {
				history.back();
			}
		</script>
</body>
</html>