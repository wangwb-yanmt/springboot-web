package com.wangwb.web.items.shiro;

import java.util.Set;

/**
 * 	shiro用户实体类
 * @author wangwb
 *
 */
public class ShiroUser {

	private String id;
	private String name;
	private String password;
	private Set<ShiroRole> roles;
	
	public ShiroUser(String id, String name, String password, Set<ShiroRole> roles) {
		setId(id);
		setName(name);
		setPassword(password);
		setRoles(roles);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ShiroRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<ShiroRole> roles) {
		this.roles = roles;
	}
	
}
