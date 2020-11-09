package com.wangwb.web.common.bean;

import java.util.Set;

/**
 * 	shiro角色实体类
 * @author wangwb
 *
 */
public class ShiroRole {
	
	private String id;
	private String roleName;
	private Set<ShiroPermissions> permissions;
	
	public ShiroRole(String id, String roleName, Set<ShiroPermissions> permissions) {
		setId(id);
		setRoleName(roleName);
		setPermissions(permissions);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<ShiroPermissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<ShiroPermissions> permissions) {
		this.permissions = permissions;
	}

}
