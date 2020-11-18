var localStorage = window.localStorage;
//layui组件
var carousel;
var table;
var form;
var laydate;
var $;
//使用组件
layui.use(['carousel','table','form','laydate','jquery'], function(){
	carousel = layui.carousel;
	table = layui.table;
	form = layui.form;
	laydate = layui.laydate;
	$ = layui.jquery;
	
	//根据文档高度，设置列表面板的高度
//	var docHeight = $(document).height();
//	var searchCardHeigth = $("#searchCard").height();
//	$("#dataCard").height(docHeight-searchCardHeigth);	
	
	//日期
	laydate.render({
		elem: '#date'
	});
	
	//轮播页渲染
	carousel.render({
		elem: '#test1',
		width: '100%',
		height: '600px',
		interval: 3000,
		arrow: 'always', //始终显示箭头
	});
	
	//ztree
	queryTree();
	
	//自定义验证规则
	//内置required（必填项）phone（手机号）email（邮箱）url（网址）number（数字）date（日期）identity（身份证）
	form.verify({
		title: function(value){
			if(value.length < 5){
				return '长度至少得5个字符';
			}
	    }
	});
	
	//监听表单提交
	form.on('submit(searchButton)', function(data){
		console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
		layer.msg("提交表单");
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	
	//进入页面执行查询列表
	queryList();
	
	//监听表格复选框点击事件
	table.on('checkbox(test)', function(obj){
		var checked = obj.checked;//点击之后的选中状态 true/false
		var data = obj.data;//点击行的相关数据
		var type = obj.type;//点击的是不是全选框 all/one
	});
	//监听头部左工具栏事件
	table.on('toolbar(test)', function(obj){
		var checkStatus = table.checkStatus(obj.config.id);
		var data = checkStatus.data;
		switch(obj.event){
	    	case 'getCheckData':
    		if(data.length==0) {
    			layer.msg('未选择任何数据');
    		}else {
    			layer.alert(JSON.stringify(data), {
    				title: '选择的行数据：'
    			});
    		}
    		break;
		};
	});
	//监听表格行单击事件
//	table.on('row(test)', function(obj){searchCard
//		var data = obj.data;
//		layer.alert(JSON.stringify(data), {
//			title: '当前行数据：'
//	    });
//	});
	//监听表格行双击事件
	table.on('rowDouble(test)', function(obj){
	  //obj 同上
	});
	//监听行工具事件
	table.on('tool(test)', function(obj){
		var data = obj.data;
	    if(obj.event === 'del'){
	      layer.confirm('真的删除行吗？',{icon: 3, title:'提示'},function(index){
	        //执行删除。。。
	        layer.close(index);
	      });
	    }
	});
	
	
});
//列表查询渲染
function queryList() {
	table.render({
		elem: '#demo',
		page: true,
		title: '检查任务列表',
//		height: '500',	
		toolbar: '#toolbarDemo',
		defaultToolbar: [],
		//异步接口参数
		method: 'post',
		limit: 20,
		url:"/TestController/getList",
		where: {},	//接口的其他参数
		headers: {token: localStorage.getItem("token")},
		parseData: function(res) { //res即为原始返回的数据
			return {
				"code": res.code, //解析接口状态
				"msg": res.msg, //解析提示文本
				"count": res.count, //解析数据长度
				"data": res.list //解析数据列表
			};
		},
		request: {		//用于对分页请求的参数重新设定名称
			pageName: 'pageNo',
			limitName: 'limit'
		},
		cols: [[
			{type:'checkbox'},
			{type:'numbers',title:'序号',width:'5%'},
			{field:'CORP_NAME',title:'企业名称',width:'30%',align:'center'},
			{field:'REG_NO',title:'注册号',width:'30%',align:'center'},
			{title:'操作1',width:'15%',align:'center',
				templet: function(d) {
					return '<a href="javascript:void(0);" onclick="modifyContent('+d.CONTENT_ID+')">修改</a> '
					+'&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteContent('+d.CONTENT_ID+')">删除</a>';
				}
			},
			{title:'操作2',width:'15%',align:'center',toolbar: '#barDemo'},
    	]],
	});
}
//打开弹窗
function addContent(CONTENT_ID) {
	var index = layer.open({
		type: 2,
		title: "发布内容",
		area: ['50%', '95%'],
		closeBtn: 0,
		content: "tanchuang.html?openType=add&CONTENT_ID="+CONTENT_ID
	}); 
}

function queryTree() {
	$.ajax({
	    url:"/TestController/queryTree",
	    data:{},
	    type:"Post",
	    dataType:"json",
	    success:function(data){
	    	if(data.success == true){
	    		var zNodes = data.data;
	    		renderZtree("treeDemo",zNodes);
	    	}else {
	    		layer.msg(data.msg);
	    	}
	    },
	    error:function(data){
	    	layer.msg("请求出错啦！");
	    }
	});
}

function resetForm() {
	$("#searchForm")[0].reset();
	resetZtree("treeDemo");
}
