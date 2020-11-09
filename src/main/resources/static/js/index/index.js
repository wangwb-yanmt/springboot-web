//使用的模块
var element;
var layer;
var $;
layui.use(['layer', 'element', 'jquery'], function(){
	element = layui.element;//Tab的切换功能，切换事件监听等，需要依赖element模块
	layer = layui.layer;
	$ = layui.jquery;
	
	//监听tab选项卡的切换事件
	element.on('tab(homeTab)', function(data){
		//获取tab的id
		var id=$(this).attr('lay-id');
	});
	//监听导航菜单点击事件
	element.on('nav(moduleTree)',function (elem) {
		var moduleName=$(this).text();//获取菜单的名称
		var moduleUrl=$(this).attr('lay-href');//获取菜单的URL
		var moduleId=$(this).attr('lay-id');//获取菜单的ID
		if(moduleUrl == "" || moduleUrl == undefined) {
			//layer.msg("没有url");
		}else {
			var exist=$("li[lay-id='"+moduleId+"']").length; //判断是否存在tab
			//如果没有就添加tab
			if(exist==0){
				element.tabAdd('homeTab',{
				  title: moduleName,
				  content: '<iframe src="'+moduleUrl+'" frameborder="0" width="100%" height="100%" ></iframe>', //支持传入html
				  id: moduleId,//tab的ID
				});
			}
			element.tabChange('homeTab',moduleId);//切换tab
		}
	});
});

//退出系统
function loginOut() {
	layer.msg("退出系统");
}
//点击主页按钮
function goHome() {
	var exist=$("li[lay-id='0']").length; //判断是否存在tab
	//如果没有就添加tab
	if(exist==0){
		element.tabAdd('homeTab',{
		  title: '主页',
		  content: '<iframe src="/html/layuiCard.html" frameborder="0" width="100%" height="100%" ></iframe>', //支持传入html
		  id: '0',//tab的ID
		});
	}
	element.tabChange('homeTab',0);//切换到主页TAB
}
//查询菜单
function queryModule() {
	$.ajax({
	    url:"http://10.66.1.74:7777/jdjc-web/",
	    data:{'id':id},
	    headers: {'token':"123456789"},
	    type:"Post",
	    dataType:"json",
	    success:function(data){
	      console.log(data);
	    },
	    error:function(data){
	        $.messager.alert('错误',data.msg);
	    }
	});
}
function lookUserInfo() {
	layer.msg("点击了基本资料");
}
function SetSecurity() {
	layer.msg("点击了安全设置");
}


