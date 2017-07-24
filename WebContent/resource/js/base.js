var zfy={};
//}
zfy.component={}; 
zfy.util ={};

/** portal工具类相关封装 未做完**/
zfy.util.Portal = {
		getPortalWindow :function(){
			var p = window;
			while(p){
				if(p.isPortalWindow){
					return p;
				}
				if(p == p.parent){
					return null;
				}
				p = p.parent;
			}
			return null;

		},

		/**
		 * data.url :打卡的url
		 * data.callback :回调函数
		 */
		openPage: function(url, callback){
			$("input:focus").blur();
			var dlg = $("<div/>").appendTo("body");
			dlg.callback = callback;
			dlg.css({float:"left",zIndex:9000,width:'100%',height: document.body.scrollHeight,position:"absolute",top:0,left:0});
			dlg.append('<iframe src="'+url+'"width="100%" height="100%" border="0" marginWidth="0" marginHeight="0" frameBorder="no" scrolling="auto"/>');
			window._pageDialog = dlg;
		},

		
		/**
		 * data:传递给回调函数的参数
		 */
		closePage:function(data){
			try{
				window.top.document.getElementById('tobfocusforIE').focus();
			}catch(e){
			}
			var dlg = window.parent._pageDialog; 
			var callback = dlg.callback;
			if(callback){
				callback(data);
			}

			//TODO ie10 从编辑页返回列表页后无法获得焦点的问题
			var input = $(window.parent.document).find("input[data-type!='date']:visible:first"); 
			if(input && input[0]){
				input[0].focus();
			}
			dlg.remove();
		},
		
		
};

var $portal = zfy.util.Portal;

zfy.util.Form = {
		submit : function(id){
			$mask.show();
			setTimeout(function(){
				$("#" + id).submit();
			},1);
		},
		ajaxSubmit : function(id,callback){
			$("input:focus").blur();
			var form = $("#" + id);
			setTimeout(function(){
				$ajax.post(form.attr("action"),form.serialize(), function(data){
					if(callback){
						if(typeof data == "string"){
							eval("data="+data);
						}
						callback(data);
					}
				});
			},1);

		},
		
		
		resetForm : function(id,param){
			var url = $("#" + id).attr('action');
			if(param){
				url += "?"+param;
			}
			window.location.href=url;
		}
};
var $form = zfy.util.Form;

/** ajax 未做完**/
zfy.util.Ajax = {
		post : function(data,params,callbackFn){
			//$mask.show();
			if(typeof data == "object"){
				//JSON格式提交，相当于$.ajax
				$.ajax({ 
					type: data.type,
					url: data.url, 
					data:data.data,
					success: function(d){
					//	$mask.hide();
						data.success(d);
					},
					error:function(){
						/*$mask.hide();
						data.error("后台出错");*/
					}
				}); 
			}else{
				//相当于$.post
				$.ajax({ 
					type: "post",
					url: data, 
					data:params,
					success: function(d){
						if(d=="sessionMiss"){
							window.top.location.href='../';
							return;
						}
						callbackFn(d);
					},
					/*error:function(){
						$msg.showError("后台出错");
					}*/
				}); 
			}

		}

};
var $ajax = zfy.util.Ajax;

/** util **/
zfy.Util = {
		//设置cookie
		setCookie : function(cookieName,value,expiredays){
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + expiredays);
			document.cookie=cookieName+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
		},
		//获取cookie
		getCookie : function(cookieName){
			if(document.cookie.length>0){
				var start = document.cookie.indexOf(cookieName + "=");
				if (start!=-1){ 
					start += cookieName.length + 1;
					var end = document.cookie.indexOf(";",start);
					if (end == -1){
						end = document.cookie.length;
					} 
					return unescape(document.cookie.substring(start,end));
			    } 
			}
			return "";
		},
		
};
var $util = zfy.Util;

