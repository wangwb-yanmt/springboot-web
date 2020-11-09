package com.wangwb.web.common.bean;

/**
 * 	shiro权限实体类
 * @author wangwb
 *
 */
public class ShiroPermissions {
	
	private String id;
	private String permissionsName;
	
	public ShiroPermissions(String id, String permissionsName) {
		setId(id);
		setPermissionsName(permissionsName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermissionsName() {
		return permissionsName;
	}

	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
	}

}
