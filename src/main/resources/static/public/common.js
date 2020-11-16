//获取url带来的参数
function request(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null) return decodeURI(r[2]);
	return null; //返回参数值
}
//获取表单参数值
function getFormField(formId) {
	var params={};
	var formArray = $(formId).serializeArray();
	$.each(formArray, function(name, value) {
		params[this.name]=this.value;
	});
	return params;
}

var socket;
function openSocket(userId) {
	if(typeof(WebSocket) == "undefined") {
		console.log("您的浏览器不支持WebSocket");
	}else{
		console.log("您的浏览器支持WebSocket");
    	var userId = document.getElementById('userId').value;
    	var socketUrl="ws://192.168.0.231:22599/webSocket/"+userId;
    	if(socket!=null){
    		socket.close();
    		socket=null;
        }
    	socket = new WebSocket(socketUrl);
        //监听打开事件
    	socket.onopen = function() {
    		console.log("websocket已打开");
        };
        //监听获得消息事件
        socket.onmessage = function(msg) {
        	var serverMsg = "收到服务端信息：" + msg.data;
        };
        //关闭事件
        socket.onclose = function() {
            console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function() {
            console.log("websocket发生了错误");
        };
        socket.sendMsg = function(msg) {
        	socket.send(msg);
        };
        
    }
}
//设置token
function setToken(token) {
	if(!window.localStorage){
		alert("oo，您的浏览器不支持localStorage");
	}else{
		var localStorage = window.localStorage;
		localStorage.setItem("token",token);
	}
}
//取token
function getToken() {
	if(!window.localStorage){
		alert("oo，您的浏览器不支持localStorage");
	}else{
		var localStorage = window.localStorage;
		return localStorage.getItem("token");
	}
}
//清除token
function deleteToken() {
	if(!window.localStorage){
		alert("oo，您的浏览器不支持localStorage");
	}else{
		var localStorage = window.localStorage;
		localStorage.removeItem("token");
	}
}
//获取网关服务地址
function getwayUrl() {
	return "http://192.168.120.208:9101";
}


function sendAjax(url,params) {
	var resultData;
	$.ajax({
	    url:url,
	    data:params,
	    type:"Post",
	    dataType:"json",
	    success:function(data){
	    	if(data.success == true) {
	    		resultData = data;
	    	}else {
	    		layer.msg(data.msg);
	    	}
	    },
	    error:function(data){
	    	layer.msg("请求出错啦！");
	    }
	});
	return resultData;
}




	