package com.wangwb.web.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.wangwb.web.common.component.BaseDao;


@Repository
public class LoginDao {
	
	@Resource
	private BaseDao baseDao;

	public List<Map<String, Object>> queryUser(String corpName) {
		System.out.println(corpName);
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ID, REG_NO FROM T_ECIPP_CORP WHERE CORP_NAME = ? ");
		return baseDao.queryForList(sb.toString(), new Object[] {corpName});
	}
	
}
