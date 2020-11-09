package com.wangwb.web.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wangwb.web.common.bean.ModuleTree;




/**
 * 	菜单列表转为嵌套菜单
 * @author wangwb
 *
 */
public class ModuleTreeUtil {

	/**
	 * 菜单列表转换成嵌套菜单
	 * @param dataList
	 * @return
	 */
	public static List<ModuleTree> getModuleTree(List<Map<String,Object>> dataList) {
		List<ModuleTree> treeDataList = new ArrayList<ModuleTree>();
		List<ModuleTree> topTreeList = new ArrayList<ModuleTree>();
		for(Map<String,Object> dataMap : dataList) {
			ModuleTree moduleTree = new ModuleTree();
			moduleTree.setId(StringUtil.nullToEmpty(dataMap.get("MODULE_ID")));
			moduleTree.setParentId(StringUtil.nullToEmpty(dataMap.get("PARENT_MODULE_ID")));
			moduleTree.setText(StringUtil.nullToEmpty(dataMap.get("MODULE_NAME")));
			moduleTree.setUrl(StringUtil.nullToEmpty(dataMap.get("MODULE_URL")));
			moduleTree.setIcon(StringUtil.nullToEmpty(dataMap.get("MODULE_ICON")));
			if("".equals(moduleTree.getParentId())) {
				topTreeList.add(moduleTree);
			}
			treeDataList.add(moduleTree);
		}
		if(topTreeList.size()>0) {
			setChild(topTreeList,treeDataList);
		}
		return topTreeList;
		
		
	}
	
	public static void setChild(List<ModuleTree> parentTreeList,List<ModuleTree> treeDataList) {
		List<ModuleTree> childList = null;
		for(ModuleTree topTree : parentTreeList) {
			childList = new ArrayList<ModuleTree>();
			String MODULE_ID = StringUtil.nullToEmpty(topTree.getId());
			for(ModuleTree tree : treeDataList) {
				if(tree.getParentId().equals(MODULE_ID)) {
					childList.add(tree);
				}
			}
			topTree.setChildren(childList);
			if(childList.size()>0) {
				setChild(childList,treeDataList);
			}
		}
	}
	
	
}
