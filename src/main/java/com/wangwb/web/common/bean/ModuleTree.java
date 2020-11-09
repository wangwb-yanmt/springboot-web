package com.wangwb.web.common.bean;

import java.util.List;

/**
 * 	菜单嵌套json数据
 * @author wangwb
 *
 */
public class ModuleTree {
	private String id;
    private String parentId;
    private String text;
    private String url;
    private String icon;
    private List<ModuleTree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<ModuleTree> getChildren() {
		return children;
	}
	public void setChildren(List<ModuleTree> children) {
		this.children = children;
	}
}
