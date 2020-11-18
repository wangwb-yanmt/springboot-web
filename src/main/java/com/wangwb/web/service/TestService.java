package com.wangwb.web.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangwb.web.common.bean.JsonResult;
import com.wangwb.web.common.bean.ModuleTree;
import com.wangwb.web.common.bean.Page;
import com.wangwb.web.common.myenum.ResultCode;
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
	public Page getList(Map<String, Object> paramsMap) {
		Page page = null;
		try {
			page = testDao.getList(paramsMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return page;
	}

	/**
	 * 	测试更新
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public JsonResult updateData(Map<String, Object> paramsMap, String loginUserId) {
		JsonResult jsonResult = new JsonResult();
		try {
			testDao.updateData(paramsMap);
			testDao.updateData2(paramsMap);
			jsonResult.setSuccess(true);
			jsonResult.setCode(ResultCode.SUCCESS.getCode());
			jsonResult.setMsg(ResultCode.SUCCESS.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonResult;
	}

	/**
	 * 查询树测试
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public JsonResult queryTree(Map<String, Object> paramsMap, String loginUserId) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Map<String, Object>> dataList = testDao.queryTree();
			dataList = ZtreeKeyTransUtil.ZtreeKeyTrans(dataList);
			
			jsonResult.setSuccess(true);
			jsonResult.setCode(ResultCode.SUCCESS.getCode());
			jsonResult.setMsg(ResultCode.SUCCESS.getMessage());
			jsonResult.setData(dataList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonResult;
	}

	/**
	 * 查询菜单
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public JsonResult queryModule(Map<String, Object> paramsMap, String loginUserId) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Map<String, Object>> dataList = testDao.queryModule();
			List<ModuleTree> module = ModuleTreeUtil.getModuleTree(dataList);
			
			jsonResult.setSuccess(true);
			jsonResult.setCode(ResultCode.SUCCESS.getCode());
			jsonResult.setMsg(ResultCode.SUCCESS.getMessage());
			jsonResult.setData(module);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonResult;
	}
	
	
	public JsonResult queryLoginInfo(Map<String, Object> paramsMap, String loginId) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Map<String, Object>> data = testDao.queryLoginInfo(loginId);
			jsonResult.setSuccess(true);
			jsonResult.setCode(ResultCode.SUCCESS.getCode());
			jsonResult.setMsg(ResultCode.SUCCESS.getMessage());
			jsonResult.setData(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonResult;
	}
	
	public void exportExcel(Map<String, Object> paramsMap,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Page page = testDao.getList(paramsMap);
		String[] headers = {"企业名称","统一社会信用代码/注册号"};
		String[] fields = {"CORP_NAME","REG_NO"};
		String fileName = "企业列表.xls";
		ExcelUtil.exportExcelXLS(headers,fields,page.getList(),fileName,request,response);
	}


}
