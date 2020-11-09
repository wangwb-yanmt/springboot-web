package com.wangwb.web.common.util;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * excel工具类
 * @author wangwb@sparknet.com.cn
 *
 */
public class ExcelUtil {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	
	
	/**
	 * 列表导出EXCEL（.xls）浏览器下载
	 * @param headers   表头title数组
	 * @param fields    对应dataList中字段名数组
	 * @param dataList  需要导出的数据list
	 * @param fileName  导出的文件名
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public static void exportExcelXLS(String[] headers,String[] fields,List<Map<String, Object>> dataList,String fileName,HttpServletRequest request,HttpServletResponse response) throws Exception {
		int rowNum = 1;
		//创建表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet
	    HSSFSheet sheet = workbook.createSheet();
	    //在sheet中添加表头行
	    HSSFRow firstRow = sheet.createRow(0);
	    //表头设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        
	    //表头行添加内容
	    for(int i=0;i<headers.length;i++){
	    	//表头行添加单元格
            HSSFCell cell = firstRow.createCell(i);
            HSSFRichTextString headerText = new HSSFRichTextString(headers[i]);
            //单元格添加内容
            cell.setCellValue(headerText);
            cell.setCellStyle(style);
        }
	    //在数据放入对应的列
        for (Map<String, Object> map : dataList) {
        	//添加行
            HSSFRow dataRow = sheet.createRow(rowNum);
            for(int i=0;i<fields.length;i++) {
            	HSSFRichTextString dataText = new HSSFRichTextString(StringUtil.nullToEmpty(map.get(fields[i])));
            	dataRow.createCell(i).setCellValue(dataText);
            }
            rowNum++;
        }
        //设置表格默认宽度
        sheet.setDefaultColumnWidth(25);
        
        String agent = request.getHeader("User-Agent");
        String filenameEncoder = "";
        if (agent.contains("Firefox")) {
			// 火狐浏览器
			filenameEncoder = "=?utf-8?B?"
					+ Base64Util.encode(fileName.getBytes("utf-8")) + "?=";
		} else {
			// 其它浏览器
			filenameEncoder = URLEncoder.encode(fileName, "utf-8");				
		}
        
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="+filenameEncoder);
        //刷新缓冲
        response.flushBuffer();
        //workbook写入到response的输出流中
        workbook.write(response.getOutputStream());
	}
	
	/**
	 * 列表导出EXCEL（.xlsx）浏览器下载
	 * @param headers   表头title数组
	 * @param fields    对应dataList中字段名数组
	 * @param dataList  需要导出的数据list
	 * @param fileName  导出的文件名
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public static void exportExcelXlsx(String[] headers,String[] fields,List<Map<String, Object>> dataList,String fileName,HttpServletRequest request,HttpServletResponse response) throws Exception {
		int rowNum = 1;
		//创建表格
//		XSSFWorkbook workbook = new XSSFWorkbook();
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		//创建sheet
	    SXSSFSheet sheet = workbook.createSheet();
	    //在sheet中添加表头行
	    SXSSFRow firstRow = sheet.createRow(0);
	    //表头设置为居中加粗
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        
	    //表头行添加内容
	    for(int i=0;i<headers.length;i++){
	    	//表头行添加单元格
            SXSSFCell cell = firstRow.createCell(i);
            HSSFRichTextString headerText = new HSSFRichTextString(headers[i]);
            //单元格添加内容
            cell.setCellValue(headerText);
            cell.setCellStyle(style);
        }
	    //在数据放入对应的列
        for (Map<String, Object> map : dataList) {
        	//添加行
            SXSSFRow dataRow = sheet.createRow(rowNum);
            for(int i=0;i<fields.length;i++) {
            	HSSFRichTextString dataText = new HSSFRichTextString(StringUtil.nullToEmpty(map.get(fields[i])));
            	dataRow.createCell(i).setCellValue(dataText);
            }
            rowNum++;
        }
        //设置表格默认宽度
        sheet.setDefaultColumnWidth(25);
        
        String agent = request.getHeader("User-Agent");
        String filenameEncoder = "";
        if (agent.contains("Firefox")) {
			// 火狐浏览器
			filenameEncoder = "=?utf-8?B?"
					+ Base64Util.encode(fileName.getBytes("utf-8")) + "?=";
		} else {
			// 其它浏览器
			filenameEncoder = URLEncoder.encode(fileName, "utf-8");				
		}
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment;filename="+filenameEncoder);
        //刷新缓冲
        response.flushBuffer();
        //workbook写入到response的输出流中
        workbook.write(response.getOutputStream());
	}
	
	/**
	 * 解析excel，返回表格数据列表(poi)
	 * @param inputStream excel文件流
	 * @param titleMap 表格标题与字段名｛"姓名":"NAME"｝
	 * @return
	 * @throws Exception 
	 */
	public static List<Map<String, Object>> readExcel(InputStream inputStream, Map<String, String> titleMap) throws Exception {
		List<Map<String, Object>> dataList = new ArrayList<>();
		String[] titleArray = null;
		String[] fieldArray = null;
		Workbook workbook = null;
		try {
			//获取工作薄
			workbook = WorkbookFactory.create(inputStream);
			inputStream.close();
			//获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			if(sheet == null) {
				throw new Exception("sheet解析出错？"); 
			}
			//总行数
			int rowLength = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			//总列数
			int colLength = row.getLastCellNum();
			if(rowLength < 1 || colLength < 1) {
				throw new Exception("表格无内容？"); 
			}
			titleArray = new String[colLength];
			fieldArray = new String[colLength];
			//表格中标题数组
			Cell cell = null;
			for(int i=0;i<colLength;i++) {
				cell = row.getCell(i);
				if(cell != null) {
					cell.setCellType(CellType.STRING);
					String data = cell.getStringCellValue().trim();
					titleArray[i]=data;
					fieldArray[i]=titleMap.get(data);
				}
			}
			//正确的标题
			Set<String> rightTitle = titleMap.keySet();
			if(colLength != rightTitle.size()) {
				throw new Exception("表格列数不对吧，能检查下吗？"); 
			} 
			for(int i=0;i<colLength;i++) {
				String title = titleArray[i];
				if(!rightTitle.contains(title)) {
					throw new Exception("表格有标题名称不对吧，能检查下吗？"); 
				}
			}
			Map<String, Object> dataMap = null;
			//循环行
			for(int i=1;i<rowLength;i++) {
				dataMap = new HashMap<>();
				row = sheet.getRow(i);
				for(int j=0;j<colLength;j++) {
					cell = row.getCell(j);
					if(cell != null) {
						cell.setCellType(CellType.STRING);
						String data = cell.getStringCellValue().trim();
						dataMap.put(fieldArray[j], data);
					}
				}
				dataList.add(dataMap);
			}
		} catch (Exception e) {
			throw e;
		}
		return dataList;
		
	}
	
	
}
