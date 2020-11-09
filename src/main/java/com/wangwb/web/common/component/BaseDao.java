package com.wangwb.web.common.component;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.wangwb.web.common.bean.Page;
import com.wangwb.web.common.util.StringUtil;

/**
 * BaseDao
 * @author wangwb
 *
 */
@Repository
public class BaseDao{

	@Autowired
	@Qualifier("jdbcTemplate1")
	private JdbcTemplate jdbcTemplate; 
	
	/**
	 * list查询
	 */
	public List<Map<String, Object>> queryForList(String sql) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	/**
	 * list查询（预编译）
	 */
	public List<Map<String, Object>> queryForList(String sql,Object[] args) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,args);
		return list;
	}
	
	/**
	 * int查询
	 */
	public int queryForInt(String sql) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list.size();
	}
	
	/**
	 * int查询（预编译）
	 */
	public int queryForInt(String sql,Object[] args) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,args);
		return list.size();
	}
	
	/**
	 * update添加修改删除
	 */
	public int update(String sql) {
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * update添加修改删除（预编译
	 */
	public int update(String sql,Object[] args) {
		return jdbcTemplate.update(sql,args);
	}
	
	/**
	 * 	分页查询
	 * @param sql
	 * @param page 当前查询第几页
	 * @param limit 每页数据个数
	 * @return
	 */
	public Page queryForPage(String sql, String pageNo, String limit) {
		Page page = new Page();
		if("".equals(pageNo) || "".equals(limit)) {
			page.setMsg("查询失败,请传入正确的分页参数");
			return page;
		}
		try {
			List<Map<String, Object>> countList = this.queryForList("SELECT COUNT(*) AS COUNT FROM (" + sql + ")");
			String countString = StringUtil.nullToEmpty(countList.get(0).get("COUNT"));
			int countNum = Integer.parseInt(countString);
			//有结果集
			if(countNum > 0) {
				int pageNoInt = Integer.parseInt(pageNo);
				int limitInt = Integer.parseInt(limit);
				int start = (pageNoInt-1)*limitInt+1;
				int end = pageNoInt*limitInt;
				String querySql = "SELECT * FROM (SELECT TEMP.*,ROWNUM RN FROM ("+sql+") TEMP WHERE ROWNUM <= "+end+") WHERE RN >= "+start+"" ;
				List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
				page.setList(list);
				page.setMsg("查询成功");
				page.setCount(countNum);
			}else {
				page.setMsg("查询成功,无结果集");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
		return page;
	}
	
	/**
	 * 	分页查询（预编译）
	 * @param sql
	 * @param page 当前查询第几页
	 * @param limit 每页数据个数
	 * @param args 预编译参数
	 * @return
	 */
	public Page queryForPage(String sql, String pageNo, String limit, Object[] args) {
		Page page = new Page();
		if("".equals(pageNo) || "".equals(limit)) {
			page.setMsg("请传入正确的分页参数");
			return page;
		}
		try {
			List<Map<String, Object>> countList = this.queryForList("SELECT COUNT(*) AS COUNT FROM (" + sql + ")", args);
			String countString = StringUtil.nullToEmpty(countList.get(0).get("COUNT"));
			int countNum = Integer.parseInt(countString);
			//有结果集
			if(countNum > 0) {
				int pageNoInt = Integer.parseInt(pageNo);
				int limitInt = Integer.parseInt(limit);
				int start = (pageNoInt-1)*limitInt+1;
				int end = pageNoInt*limitInt;
				String querySql = "SELECT * FROM (SELECT TEMP.*,ROWNUM RN FROM ("+sql+") TEMP WHERE ROWNUM <= "+end+") WHERE RN >= "+start+"" ;
				List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql, args);
				page.setList(list);
				page.setCount(countNum);
			}else {
				page.setMsg("查询无结果集");
			}
		} catch (Exception e) {
			page.setMsg("查询时出现异常");
			throw new RuntimeException(e.getMessage());
		}
		return page;
	}
	
	
	
}
