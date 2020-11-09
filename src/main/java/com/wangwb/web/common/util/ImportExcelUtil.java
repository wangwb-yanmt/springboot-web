package com.wangwb.web.common.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 	主体表格导入（限制每次导入一张表格、第一个sheet页）
 * @author wangwb
 *
 */
@Service
public class ImportExcelUtil {
	
	public Map<String, Object> importExcel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	
		//返回的结果集
        Map<String,Object> resultMap = new HashMap<String,Object>();
        request.setCharacterEncoding("utf-8");
        try {
            List<FileItem> fileList = null;
            // 判断enctype属性是否为multipart/form-data
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if(!isMultipart) {
            	resultMap.put("success",false);
                resultMap.put("msg","enctype类型非multipart/form-data，请检查！");//表单提交方式有误，直接返回
                return resultMap;
            }
        	//创建一个解析器工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置编码格式
            upload.setHeaderEncoding("UTF-8");
            //文件最大值，设为-1表示不受限制
        	//upload.setSizeMax(-1);
            try {
            	//解析请求，将表单中每个输入项封装成一个FileItem对象
                fileList = upload.parseRequest(request);
            } catch ( FileUploadException e ) {
                e.printStackTrace();
            }

            Iterator<FileItem> itm = fileList.iterator();
            while (itm.hasNext()) {  
                FileItem item = itm.next();
                if (item.isFormField()) {// 普通输入项 
                	 //普通输入项 ,得到input中的name属性的值
                    String formFieldName = item.getFieldName();
                }else {//文件域
                	String fileName = java.net.URLDecoder.decode(item.getName(), "UTF-8");//获取文件名
                	long size = item.getSize();
                	if(size == 0) {
                		resultMap.put("success",false);
                        resultMap.put("msg",fileName+"文件大小为空，请检查！");//文件无内容直接返回（只传一个文件）
                        return resultMap;
                	}
                	//读取流
            		InputStream is  = item.getInputStream();
            		//获得Workbook
            		Workbook wb = Workbook.getWorkbook(is);
            		//得到工作表sheet数组
                    Sheet[] sheets = wb.getSheets();
                    
                    if (sheets == null || sheets.length == 0) {
                    	resultMap.put("success",false);
                        resultMap.put("msg","表格无sheet页，请检查！");//表格无sheet直接返回
                        return resultMap;
                    }
                    
                    //得到第一个sheet
                    Sheet sheet = sheets[0];
                    int sheetRows = sheet.getRows();//表格数据总行数
                    int sheetColumns = sheet.getColumns();//表格数据总列数
                    if(sheetRows<=1){
                        resultMap.put("success",false);
                        resultMap.put("msg","表格无需要导入的数据，请检查！");//没数据直接返回
                        return resultMap;
                    }
                    Cell cell = null;
                	//表头正确的名称数组
                	String[] shouldTitles = null;
                	//数据对应字段数组
                    String[] dataFields = null;
                    //必填项表头名称数组
                    String[] requiredTitles = null;
                    //必填项字段数组
                    String[] requiredFields = null;
                	//获取实际表头名称数组
                    String[] excelTitles = new String[sheetColumns];
                    for(int j=0; j<sheetColumns; j++){
                        cell = sheet.getCell(j,0);    
                        excelTitles[j] = cell.getContents().trim();
                    }
                    //校验模板表头字段
                	if(!Arrays.equals(excelTitles,shouldTitles)) {
                		resultMap.put("success",false);
                        resultMap.put("msg","表格模板字段有误，请检查！");//模板不对直接返回
                        return resultMap;
                	}
                	//存储表格所有数据
                	List<Map<String,Object>> excelDataList = new ArrayList<Map<String,Object>>();
                    //存储表格每一行的值
                    Map<String,Object> excelDataMap = null;
                    
                    Map<String,Object> existedDataMap = null;
                    
                    //存放问题数据
                    List<Map<String,Object>> errorDataList = new ArrayList<Map<String,Object>>();
                    //--------------------------------------行循环开始----------------------------------------------------------------------//
                    //记录表格行号
                    int rowNum = 2;
                    //统计数据总数
                    int dataCount = sheetRows-1;
                    //统计错误数据总数
                    int errorCount = 0;
                    //行循环，从1开始
                    for(int i=1; i<sheetRows; i++){
                    	boolean flag = true;
                    	excelDataMap = new HashMap<String, Object>();
                    	String errorContent = "";
                       //列循环
                       for(int j=0; j<sheetColumns; j++){
                           //获取第i行，第j列的值
                           cell = sheet.getCell(j,i); 
                           excelDataMap.put(dataFields[j], cell.getContents().trim());
                       }
                    	   for(int k=0; k<requiredFields.length; k++) {
                    		   String bbb = StringUtil.nullToEmpty(excelDataMap.get(requiredFields[k]));
                    		   if(StringUtil.isEmpty(bbb)) {
                    			   errorContent += "•["+requiredTitles[k]+"]为必填项，表格中值为空！\n";
                    			   flag = false;
                    		   }
                    	   }
                       try {
                    	   //判断数据是否已存在
                           String SUB_TYPE = StringUtil.nullToEmpty(excelDataMap.get("SUB_TYPE"));
                           String REG_NO = StringUtil.nullToEmpty(excelDataMap.get("REG_NO"));
                           existedDataMap = commonDao.checkObjIfExisted(SUB_TYPE,REG_NO);
                           if(existedDataMap != null && existedDataMap.size()>0) {//库中有重复数据
                        	   if("0".equals(ifdoubleValue)) {
                        		   //如果页面未选择覆盖重复数据，视为错误数据返回
                        		   errorContent += "•该主体已存在！\n";
                        		   flag = false;
                        	   }else {
                        		   //如果页面选择覆盖重复数据，做修改
                				   if("sp".equals(type)) {
                					   excelImportDao.updateFoodObj(existedDataMap,excelDataMap,loginUserId,orgId);
                				   }
                        	   }
                           }else {//无重复数据
                        	   //新增食品数据
                        	   if("sp".equals(type)) {
                        		   excelImportDao.addFoodObj(excelDataMap,loginUserId,orgId);
                        	   }
                           }
                           if(flag==false) {
                        	   errorCount++;
                           }
                           excelDataMap.put("errorReason", errorContent);
                           excelDataMap.put("excelRowNum", rowNum);
                           errorDataList.add(excelDataMap);
                           rowNum++;
        			   } catch (Exception e) {
        				   errorContent += "•格式错误！\n";
        				   errorCount++;
        				   excelDataMap.put("errorReason", errorContent);
                           excelDataMap.put("excelRowNum", rowNum);
                           errorDataList.add(excelDataMap);
                           rowNum++;
                           continue;
        			   }
                    }
                    //--------------------------------------行循环结束----------------------------------------------------------------------//
                    int successCount = dataCount-errorCount;
                    resultMap.put("success",true);
                    resultMap.put("errorDataList",errorDataList);//问题数据
                    resultMap.put("msg","导入完成！共"+dataCount+"条数据，成功"+successCount+"条，失败"+errorCount+"条");
                    
                    //导出错误数据为EXCEL
                    String[] headers = {"统一社会信用代码/注册号","主体名称","主体类型","许可证号","许可到期时间","法定代表人/负责人","联系方式","经营场所"};
                	String[] fields = {"REG_NO","CORP_NAME","SUB_TYPE_NAME","LIC_NO","LIC_TODATE_TEXT","OPER_MAN_NAME","OPER_MAN_TEL","BIZ_ADDR"};
                	String errorDataFileName = "错误数据列表.xls";
                	ExcelUtil.exportExcelXLS(headers, fields, errorDataList, errorDataFileName, request, response);
                }
            }
            //FileItem循环结束
                
        }catch (Exception e) {
        	resultMap.put("success",false);
            resultMap.put("msg","导入过程出现异常！");
		}
        return resultMap;
	
    }
	
	
	
}
