//------------------------------------------树对应文本框的name要和树主体<ul>的id一样-------------------------------------------
$(function() {
	//树文本框绑定点击
	$(".treeFlag").on("click",function(e) {
		//先阻止冒泡，不然点击无反应
		e.stopPropagation();
		var name = $(this).attr("name");
		$("#"+name).toggle();
	})
	//文档绑定点击，隐藏树
	$(document).bind('click', function(e) {
		var e = e || window.event; //浏览器兼容性 
		var elem = e.target || e.srcElement;
		var flaelem = 0;
		while (elem) { //循环判断至跟节点，防止点击的是div子元素 
			if (elem.className && elem.className == 'ztree mytree') {
				flaelem = 1;
				return;
			}
			elem = elem.parentNode;
		}
		if (flaelem == 0) {
			$(".ztree").css("display","none");
		}
	});
});
//ztree渲染
function renderZtree(treeId,treeData) {
	var setting = {
			view: {
				nameIsHTML: true,
				showLine: false,
				showIcon: false,
			},
			check: {
				enable: true,
				chkboxType: {
					"Y": "ps",
					"N": "ps"
				}
			},
			callback: {
				onClick: function(event, treeId, treeNode) {
					var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
					zTreeObj.checkNode(treeNode, !treeNode.checked, true, true);//最后一个true表示触发onCheck
				},
				onCheck: function(event, treeId, treeNode) {
					console.log(treeNode);
					var checkedValue = [];
					var checkedName = [];
					var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
					var checkedNodes = zTreeObj.getCheckedNodes(true);
					for (var i = 0, l = checkedNodes.length; i < l; i++) {
						checkedValue.push(checkedNodes[i].id);
						checkedName.push(checkedNodes[i].name);
					}
					$("[name='" + treeId + "']").val(checkedName.join(","));
					$("[name='" + treeId + "']").attr('dataID', checkedValue.join(","));
				}
			},
			data: {
				simpleData: {
					enable: true, //设置是否启用简单数据格式（zTree支持标准数据格式跟简单数据格式，上面例子中是标准数据格式）
					idKey: "id", //设置启用简单数据格式时id对应的属性名称
					pidKey: "pId" //设置启用简单数据格式时parentId对应的属性名称,ztree根据id及pid层级关系构建树结构
				}
			}
	}
	$.fn.zTree.init($("#"+treeId), setting, treeData);
	//默认展开第一个节点
	var ztreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes =  ztreeObj.getNodes();
	if (nodes.length>0) {
		ztreeObj.expandNode(nodes[0]);
	}
}
//ztree单节点赋值
function checkZtreeNodes(treeId,value) {
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var node = zTreeObj.getNodeByParam("id",value);
	zTreeObj.checkNode(node, true, false, true);//3个true 表示是否勾选节点/是否根据设置的联动属性勾选/是否触发beforeCheck & onCheck 事件
	
}
//ztree重置
function resetZtree(treeId) {
	var zTreeobj = $.fn.zTree.getZTreeObj(treeId);
	zTreeobj.checkAllNodes(false);
	$("[name='"+treeId+"']").attr('dataID', '');
}
