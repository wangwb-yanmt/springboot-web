package com.wangwb.web.common.component;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wangwb.web.common.bean.Page;
import com.wangwb.web.common.exception.MyException;
import com.wangwb.web.common.myenum.ResultCode;
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
		//没有分页参数直接返回
		if("".equals(pageNo) || "".equals(limit)) {
			page.setSuccess(false);
			page.setCode(ResultCode.PAGE_PARAM_ERROR.getCode());
			page.setMsg(ResultCode.PAGE_PARAM_ERROR.getMessage());
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
				page.setSuccess(true);
				page.setCode(ResultCode.SUCCESS.getCode());
				page.setMsg(ResultCode.SUCCESS.getMessage());
				page.setCount(countNum);
				page.setList(list);
			}else {
				page.setSuccess(true);
				page.setCode(ResultCode.SUCCESS.getCode());
				page.setMsg(ResultCode.SUCCESS.getMessage());
				page.setCount(countNum);
			}
		} catch (Exception e) {
			//为了事物回滚，抛出RuntimeException
			throw new RuntimeException(e);
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
			page.setSuccess(false);
			page.setCode(ResultCode.PAGE_PARAM_ERROR.getCode());
			page.setMsg(ResultCode.PAGE_PARAM_ERROR.getMessage());
			return page;
		}
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
			page.setSuccess(true);
			page.setCode(0);
			page.setMsg("查询成功");
			page.setCount(countNum);
			page.setList(list);
		}else {
			page.setSuccess(true);
			page.setCode(0);
			page.setMsg("查询成功,无结果集");
			page.setCount(countNum);
		}
		return page;
	}
	
	
}
