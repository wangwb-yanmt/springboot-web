package com.wangwb.web.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangwb.web.common.bean.ModuleTree;
import com.wangwb.web.common.bean.Page;
import com.wangwb.web.common.component.TimerTask;
import com.wangwb.web.common.exception.MyException;
import com.wangwb.web.common.util.ExcelUtil;
import com.wangwb.web.common.util.ModuleTreeUtil;
import com.wangwb.web.common.util.ZtreeKeyTransUtil;
import com.wangwb.web.dao.TestDao;

/**
 * 	测试service
 * @author wangwb
 *
 */
@Service
@Transactional
public class TestService {
	
	@Resource
	private TestDao testDao;
	
	private Logger logger = LoggerFactory.getLogger(TestService.class);

	/**
	 * 	测试查询list
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> getList(Map<String, Object> paramsMap) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			Page page = testDao.getList(paramsMap);
			bodyMap.put("data", page.getList());
			bodyMap.put("count", page.getCount());
			
			resultMap.put("success", true);
			resultMap.put("code", 0);
			resultMap.put("msg", page.getMsg());
			resultMap.put("body", bodyMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(-1,"出现异常");
		}
		return resultMap;
	}

	/**
	 * 	测试更新
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> updateData(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			testDao.updateData(paramsMap);
			testDao.updateData2(paramsMap);
			resultMap.put("code", 0);
			resultMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(-1,"出现异常");
		}
		return resultMap;
	}

	/**
	 * 查询树测试
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> queryTree(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> dataList = testDao.queryTree();
			dataList = ZtreeKeyTransUtil.ZtreeKeyTrans(dataList);
			
			bodyMap.put("data", dataList);
			
			resultMap.put("code", 0);
			resultMap.put("success", true);
			resultMap.put("msg", "查询成功");
			resultMap.put("body", bodyMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(-1,"出现异常");
		}
		return resultMap;
	}

	/**
	 * 查询菜单
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> queryModule(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> dataList = testDao.queryModule();
			List<ModuleTree> module = ModuleTreeUtil.getModuleTree(dataList);
			
			bodyMap.put("data", module);
			
			resultMap.put("code", 0);
			resultMap.put("success", true);
			resultMap.put("msg", "查询成功");
			resultMap.put("body", bodyMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(-1,"出现异常");
		}
		return resultMap;
	}
	
	
	public Map<String, Object> queryLoginInfo(Map<String, Object> paramsMap, String loginId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> data = testDao.queryLoginInfo(loginId);
			resultMap.put("code", 0);
			resultMap.put("success", true);
			resultMap.put("msg", "查询成功");
			resultMap.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(-1,"查询出现异常");
		}
		return resultMap;
	}
	
	public void exportExcel(Map<String, Object> paramsMap,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Page page = testDao.getList(paramsMap);
		String[] headers = {"企业名称","统一社会信用代码/注册号"};
		String[] fields = {"CORP_NAME","REG_NO"};
		String fileName = "企业列表.xls";
		ExcelUtil.exportExcelXLS(headers,fields,page.getList(),fileName,request,response);
	}


}
