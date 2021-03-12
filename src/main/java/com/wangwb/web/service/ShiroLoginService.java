package com.wangwb.web.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.wangwb.web.items.shiro.ShiroPermissions;
import com.wangwb.web.items.shiro.ShiroRole;
import com.wangwb.web.items.shiro.ShiroUser;



/**
 * 
 * @author wangwb
 *
 */
@Service
public class ShiroLoginService {
	
	/**
     * 	模拟数据库查询到的user
     * @param userName
     * @return
     */
    public ShiroUser getUserByName(){
    	//wangwb有query和add权限
    	Set<ShiroPermissions> permissionsSet = new HashSet<>();
        ShiroPermissions permissions1 = new ShiroPermissions("1","query");
        ShiroPermissions permissions2 = new ShiroPermissions("2","add");
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        //admin角色可以
        ShiroRole role = new ShiroRole("1","admin",permissionsSet);
        Set<ShiroRole> roleSet = new HashSet<>();
        roleSet.add(role);
        ShiroUser user = new ShiroUser("1","wangwb","123456",roleSet);

        return user;
    }

}
