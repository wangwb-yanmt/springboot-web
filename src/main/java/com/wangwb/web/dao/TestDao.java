package com.wangwb.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wangwb.web.common.bean.Page;
import com.wangwb.web.common.util.StringUtil;
import com.wangwb.web.items.springjdbc.BaseDao;
import com.wangwb.web.items.springjdbc.BaseDaoTwo;

/**
 * 	测试dao
 * @author wangwb
 *
 */
@Repository
public class TestDao{

	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private BaseDaoTwo baseDaoTwo;

	/**
	 * 	查询列表
	 * @param paramsMap
	 * @return
	 */
	public Page getList(Map<String, Object> paramsMap) {
		String pageNo = StringUtil.nullToEmpty(paramsMap.get("pageNo"));
		String limit = StringUtil.nullToEmpty(paramsMap.get("limit"));
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT CORP_NAME,REG_NO FROM T_ECIPP_CORP ");
		return baseDao.queryForPage(sb.toString(), pageNo, limit);
	}

	/**
	 * 	测试更新数据发生异常
	 * @param paramsMap
	 */
	public void updateData(Map<String, Object> paramsMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO R$USER ");
		sql.append("  (USER_ID, DEPT_ID, USERNAME, PASSWORD) ");
		sql.append("VALUES ");
		sql.append("  (7, 2, 'ycwwb', '96e79218965eb72c92a549dd5a330112') ");
		baseDao.update(sql.toString());
	}
	
	/**
	 * 	测试更新数据发生异常
	 * @param paramsMap
	 */
	public void updateData2(Map<String, Object> paramsMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO R$USER ");
		sql.append("  (USER_ID, DEPT_ID, USERNAME, PASSWORD) ");
		sql.append("VALUES ");
		sql.append("  (8, 2, 'ycwwb', '96e79218965eb72c92a549dd5a330112') ");
		baseDao.update(sql.toString());
	}

	/**
	 * 查询树测试
	 * @return
	 */
	public List<Map<String, Object>> queryTree() {
		String sql = " SELECT ID,PARENT_ID AS PID,ITEM_NAME AS NAME FROM T_DICT_ECIPP_AREA ";
		return baseDao.queryForList(sql);
	}

	/**
	 * 查询菜单，顺便测试数据源
	 * @return
	 */
	public List<Map<String, Object>> queryModule() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PRIVILEGE_ID MODULE_ID, ");
		sql.append("       PARENT_PRIVILEGE_ID AS PARENT_MODULE_ID, ");
		sql.append("       NAME AS MODULE_NAME, ");
		sql.append("       URL AS MODULE_URL, ");
		sql.append("       '' AS MODULE_ICON ");
		sql.append("  FROM R$PRIVILEGE ");
		sql.append(" WHERE STATE = 'A' ORDER BY PRIVILEGE_ID ");
		return baseDaoTwo.queryForList(sql.toString());
	}

	public List<Map<String, Object>> queryLoginInfo(String loginId) {
		System.out.println("loginId:::::::"+loginId);
		String sql = " SELECT DISTINCT C.CORP_NAME FROM T_ECIPP_CORP C WHERE C.ID = ? ";
		return baseDao.queryForList(sql, new Object[] {loginId});
	}
	
}
