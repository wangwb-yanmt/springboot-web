//使用的模块
var element;
var layer;
var form;
var $;
var tree;
layui.use(['layer', 'element', 'jquery','form','tree'], function(){
	element = layui.element;//Tab的切换功能，切换事件监听等，需要依赖element模块
	layer = layui.layer;
	form = layui.form;
	$ = layui.jquery;
	tree = layui.tree;
	
	//查询左侧菜单
	queryModule();
	
	//监听导航菜单点击事件
	element.on('nav(moduleTree)',function (elem) {
		var moduleName=$(this).text();//获取菜单的名称
		var moduleUrl=$(this).attr('lay-href');//获取菜单的URL
		var moduleId=$(this).attr('lay-id');//获取菜单的ID
		if(moduleUrl == "" || moduleUrl == undefined) {
			
		}else {
			$('iframe').attr('src',moduleUrl);
		}
	});
	element.render();
	element.init();
	
	queryLoginInfo();
	
	
});

//退出系统
function loginOut() {
	window.location.href="/LoginOutController/loginOut";
}
//点击主页按钮
function goHome() {
	$('iframe').attr('src',"/html/test.html");
}
//查询顶层菜单
function queryModule() {
	$.ajax({
	    url:"/TestController/queryModule",
	    data:{},
	    headers: {},
	    type:"Post",
	    dataType:"json",
	    success:function(data){
	    	var data = data.body.data;
	    	var html = '';
	    	//遍历顶层菜单
	    	for(var i=0;i<data.length;i++) {
	    		// 判断是否存在子菜单
	    		if(data[i].children!=null&&data[i].children.length>0){
	    			html += '<li class="layui-nav-item">'
	    				+'<a href="javascript:;"><i class="layui-icon" >&#xe653;</i>'+data[i].text+'</a>'
	    				+'<dl class="layui-nav-child">';
	    			// 遍历获取子菜单
	    			for(var k=0; k<data[i].children.length;k++){
	    				html += getChildMenu(data[i].children[k],0);
	                }
	    			html += '</dl></li>';
	    		}else {
	    			html += '<li class="layui-nav-item"><a href="'+data[i].url+'"><i class="layui-icon" >&#xe66c;</i>'+data[i].text+'</a></li>';
	    		}
		 	}
	    	$("#moduleTree").html(html);
	    	//重新渲染
	    	element.render();
	    },
	    error:function(data){
	    	layer.msg("请求错误");
	    }
	});
}
//递归生成子菜单
function getChildMenu(subMenu,num) {
    num++;
    var subStr = '';
    if(subMenu.children!=null&&subMenu.children.length>0){
        subStr += '<dd><a style="margin-Left:'+num*15+'px" href="javascript:;"><i class="layui-icon" >&#xe653;</i>'+subMenu.text+'</a>' +
                    '<dl class="layui-nav-child">\n';
        for( var j = 0; j <subMenu.children.length; j++){
            subStr+=getChildMenu(subMenu.children[j],num);
        }
        subStr+='</dl></dd>';
    }else{
        subStr+='<dd><a style="margin-Left:'+num*15+'px" href="'+subMenu.url+'"><i class="layui-icon" >&#xe66c;</i>'+subMenu.text+'</a></dd>';
    }
    return subStr;
}

function lookUserInfo() {
	var index = layer.open({
		type: 2,
		title: "用户信息",
		area: ['60%', '60%'],
		btn: ['提交', '重置', '关闭'],
		btnAlign: 'c',
		closeBtn: 0,
		content: "/html/leftRight/left1right1.html"
	});
}
function SetSecurity() {
	layer.msg("点击了安全设置");
}

function queryLoginInfo() {
	$.ajax({
	    url:"/TestController/queryLoginInfo",
	    data:{},
	    headers: {},
	    type:"Post",
	    async: false,
	    dataType:"json",
	    success:function(data){
	    	var dataArray = data.data;
	    	$("#loginName").text(dataArray[0].CORP_NAME);
	    },
	    error:function(data){
	    	layer.msg("请求错误");
	    }
	});
}


