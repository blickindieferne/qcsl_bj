<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${path}/resource/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${path}/resource/js/base.js"></script>
<%-- <link href="${path}/resource/css/main.css" rel="stylesheet" type="text/css"></link> --%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>启诚商旅-比价系统</title>
<style type="text/css">
.steps {
	font-size: 14px;
	color: #000;
	font-weight: 700;
}
</style>
</head>
<body style="width: 100%;height:100%; font-size: 14px;overflow-x:hidden">
<table style="width:100%;height:100%">
<tr><td>&nbsp;</td><td></td></tr>
<tr><td align="center">

 <div class="login_box" style="background-color: #f4f4f4; padding: 10px; width: 650px;">
      <form action="${path}/doLogin.do" method="post" id="myform">
        <table style="width:100%">
          <tr>
            <td><c:if test="${cookieInvalid}">
                <span style="color: red">cookie失效</span>
              </c:if></td>
            <td></td>
          </tr>
          <tr>
            <td colspan="2" align="center"><font style="font-size: 16px; color: #000; font-weight: 700;">启诚商旅 - 去哪儿比价系统（1.2.2版）</font></td>
           
          </tr>
          <tr><td align="left">请输入去哪儿网Cookie</td><td></td></tr>
          <tr>
            <td><textarea name="cookie" id="cookie" style="width: 100%; height: 300px"></textarea></td>
            <td></td>
          </tr>
          <tr>
            <td colspan="2" align="center">
           
           <br/>
             <input id="domain1" name="domain" type="radio" value="hxs.trade.qunar.com"/>hxs
       
             <input id="domain2" name="domain" type="radio" value="wmd.trade.qunar.com"/>wmd
          &nbsp;
             <button type="submit" onclick="login()" style="font-size: 14px; color: #000; font-weight: 700;">&nbsp;登&nbsp;录&nbsp;</button>
            </td>

          </tr>
         
         <tr><td>&nbsp;</td><td></td></tr>
          <tr>
            <td colspan="2" align="left" >请使用Chrome浏览器，或360浏览器的极速模式</td>
          </tr>
         <tr><td></td><td></td></tr>
          <tr>
            <td colspan="2" align="left"><a style="text-decoration: underline;  cursor: pointer;" href="javascript:showHelp();">如何获取去哪儿Cookie ？</a>
            </td>
          </tr>
        </table>
      </form>
    </div>
    <div class="opt_show" style="display: none;">
      <div>
        <font style="">获取去哪儿网cookie示例：</font>
      </div>
      <div class="steps" style="margin-top: 10px;">
        <table>
          <tr>
            <td>1、首先，打开Goole Chrome浏览器，登录去哪儿网站，进入网站页面： <br /> <img src="${path }/resource/image/step_1.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>2、鼠标右键点击网页，在出现的菜单中选择最后一项“审查元素（N）”，看到如下页面： <br /> <img src="${path }/resource/image/step_2.png" style="margin-top: 10px; border: 1px solid gray;"></br> <img src="${path }/resource/image/step_3.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>

          <tr>
            <td>3、刷新网页： <br /> <img src="${path }/resource/image/step_4.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>4、再看“Network”面板，鼠标滚动右侧滚动条到最上端，点击第一条“Name Path”： <br /> <img src="${path }/resource/image/step_5.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>5、在右侧显示面板里，找到Cookie一项，选中Cookie内容，并复制： <br /> <img src="${path }/resource/image/step_6.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>6、打开比价系统登录页面，把复制的内容张贴到框里： <br /> <img src="${path }/resource/image/step_7.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>7、点击“进入比价系统”按钮，登录成功。完成。 <br /> <img src="${path }/resource/image/step_8.png" style="margin-top: 10px; border: 1px solid gray;">
            </td>
          </tr>
        </table>
      </div>
    </div>

</td></tr></table>

   



</body>
<script type="text/javascript">
	function showHelp() {
		if($(".opt_show").is(':visible')){
			$(".opt_show").hide();
		}else{
			$(".opt_show").show();
		}
		
	}

	//点击登录，把填写的cookie记到网页cookie里面
	function login() {
		var cookieValue = $("#cookie").val();
		var domain = $("input[name='domain']:checked").val();
		if(!domain){
			alert("请选择一个平台");
			return;
		}
		$util.setCookie("qner_cookie", cookieValue, 30);
		$util.setCookie("domain", domain, 30);
		$form.submit("myform");
	}

	$(function() {
		//读取cookie
		var cookieValue = $util.getCookie("qner_cookie");
		var domain =  $util.getCookie("domain");
		if (cookieValue != null) {
			$("#cookie").val(cookieValue);
		}
		if(domain!=null){
			$("input[name='domain']").each(function(){
				if(domain==this.value){
					this.checked="checked";
				}
				
			});
			
		}
	})
</script>
</html>