/**
 * 
 */
package com.hstyle.framework.excel.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;

import com.hstyle.framework.excel.exp.module.ExcelModule;
import com.hstyle.framework.excel.exp.service.ExcelExpUtil;
import com.hstyle.framework.excel.imp.bean.ExcelData;
import com.hstyle.framework.excel.imp.bean.ImportCellDesc;
import com.hstyle.framework.excel.imp.service.ExcelImportUtil;
import com.hstyle.framework.excel.imp.util.ExcelDataUtil;
import com.hstyle.framework.excel.imp.util.StringUtil;


/**
 * excel导入、导出通用工具类测试
 * 
 * @author Administrator
 * 
 */
public class ExcelUtilTest {
	
	public static void main(String[] args) throws Exception {
		//impBhc();
		impPhd();
	}
    /*public static void impBhc() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
   	
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
    	String url = "jdbc:oracle:thin:@192.168.1.198:1521:wms"; 
    	Connection con = DriverManager.getConnection(url, "wms", "HSTYLEwms2012");
    	Statement stmt = con.createStatement(); 
    	File file=new File("F:\\BHC");
    	if(file.isDirectory()){
    		File[] excelFiles=file.listFiles();
    		for (int i = 0; i < excelFiles.length; i++) {
    			ExcelData data=impExcel(excelFiles[i]);
    			List<Map<String, ImportCellDesc>> rows = data.getRepeatData();
    			for (Map<String, ImportCellDesc> row : rows) {
    				if (row != null && row.size() > 0) {
    					ImportCellDesc kwcode = row.get("ckCode");
    					ImportCellDesc kwname = row.get("ckName");
    					ImportCellDesc procode = row.get("spCode");
    					ImportCellDesc proname = row.get("spName");
    					ImportCellDesc ggh = row.get("spGgh");
    					ImportCellDesc ggname = row.get("spGgName");
    					ImportCellDesc barcode = row.get("spBarCode");
    					ImportCellDesc realnum = row.get("newNum");
    					String sqlString="insert into tmp_bhc_fail_product(WHCODE,WHNAME,PROCODE,PRONAME,SPCTNCODE,SPCTNNAME,SPCTNBARCODE,"+
    					   "PROOID,FSECTIONNAME,FSECTIONCODE,PROCOUNT) VALUES('002','备货仓','"+procode.getFieldValue()
    					     +"','"+proname.getFieldValue()+"','"+ggh.getFieldValue()+"','"+ggname.getFieldValue()
    					     +"','"+barcode.getFieldValue()+"','"+barcode.getFieldValue()+"','"+kwname.getFieldValue()
    					     +"','"+kwcode.getFieldValue()+"',"+Float.parseFloat(StringUtils.isEmpty(realnum.getFieldValue())?"0":realnum.getFieldValue())+")";
    					System.out.println(sqlString);
    					stmt.execute(sqlString);
    				}
    			}
			}
    		stmt.close();
    		con.close();
    	}
    }*/
	public static void impPhd(){
		File file=new File("F:\\二次分拣.xls");
		ExcelData data=impExcel(file);		
		 List<Map<String, ImportCellDesc>> rows = data.getRepeatData();
		 for (Map<String, ImportCellDesc> row : rows) {
				if (row != null && row.size() > 0) {
					ImportCellDesc kwName = row.get("kwName");					
					ImportCellDesc spNum = row.get("spNum");
					ImportCellDesc spCode = row.get("spCode");
					ImportCellDesc spGgName = row.get("spGgName");
					//ImportCellDesc spNum = row.get("spNum");
					ImportCellDesc detail = row.get("detail");
					ImportCellDesc spGgh = row.get("spGgh");
					System.out.println(kwName.getFieldValue()+"--"+spCode.getFieldValue()+"--"+detail.getFieldValue());
					List<String[]> backsList=getBack(detail.getFieldValue());
					for (Iterator<String[]> iterator = backsList.iterator(); iterator
							.hasNext();) {
						String[] back = iterator.next();
						System.out.println(back[0]+"=="+back[1]);
						
					}
					
					
					
				}
			}
	}
	private static List<String []> getBack(String str){
		List<String []> result = new ArrayList<String[]>();
		
		for(String s : str.split(";")){
			if(s.indexOf(":") == -1)
				continue;
			
			String [] arr = s.split(":");
			
			result.add(new String [] {arr[0].replace("(", "").replace(")", ""),arr[1]});
		}
		
		return result;
	}
    private static ExcelData impExcel(File excel){
    	String xmlFile = "ecfj_desc.xml";
    	ExcelData data=null;
    	System.out.println("开始导入"+excel.getName()+"----------------------");
    	try {
			InputStream importExcelStream=new FileInputStream(excel);
			 data = ExcelImportUtil.readExcel(xmlFile, importExcelStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return data;
    }
	/**
	 * 转换库位商品excel，拆分多库位，每个库位增加一条记录
	 * 
	 * @return
	 * @throws Exception
	 */
	private static ExcelData impBhcKwSpData() throws Exception {
		
		String xmlFile = "kwsp_desc.xml";
		String excelFileName = "bh-kwsp.xls";
		InputStream importExcelStream = ExcelUtilTest.class
				.getResourceAsStream(excelFileName);
		ExcelData data = ExcelImportUtil.readExcel(xmlFile, importExcelStream);
		List<Map<String, ImportCellDesc>> rows = data.getRepeatData();
		List<Map<String, String>> newRows = new ArrayList<Map<String, String>>();
		for (Map<String, ImportCellDesc> row : rows) {
			if (row != null && row.size() > 0) {
				ImportCellDesc kwCellDesc = row.get("kwName");
				String kwString = kwCellDesc.getFieldValue();
				String[] kw = kwString.split(",");
				for (int i = 0; i < kw.length; i++) {
					if (StringUtil.isEmpty(kw[i]))
						continue;
					Map<String, String> tmp = new HashMap<String, String>();
					for (String key : row.keySet()) {
						if (key.equals("kwName"))
							continue;
						tmp.put(key, row.get(key).getFieldValue());
					}
					tmp.put("spId", row.get("spBarCode").getFieldValue());
					tmp.put(kwCellDesc.getFieldName(), kw[i]);
					tmp.put("kwCode", kw[i] + row.get("ckCode").getFieldValue()); 
					tmp.put("spNum", "0");
					newRows.add(tmp);    
				}

			}
		}
		ExcelModule excelModule = new ExcelModule(newRows);
		ExcelExpUtil.expExcel(excelModule, "exp_templete_kwsp.xls",
				excelFileName);
		System.out.println("-------------结束--------------------");
		return data;
	}

	private static ExcelData impZycKwSpData() throws Exception {
		String xmlFile = "kwsp_desc.xml";
		String excelFileName = "zyc-kwsp.xls";
		InputStream importExcelStream = ExcelUtilTest.class
				.getResourceAsStream(excelFileName);
		ExcelData data = ExcelImportUtil.readExcel(xmlFile, importExcelStream);
		List<Map<String, ImportCellDesc>> rows = data.getRepeatData();
		List<Map<String, String>> newRows = new ArrayList<Map<String, String>>();
		for (Map<String, ImportCellDesc> row : rows) {
			if (row != null && row.size() > 0) {

				Map<String, String> tmp = new HashMap<String, String>();
				for (String key : row.keySet()) {

					tmp.put(key, row.get(key).getFieldValue());
				}
				String spBarCodeString=row.get("spBarCode").getFieldValue();
				if(spBarCodeString.equals("NULL"))
					spBarCodeString=row.get("spCode").getFieldValue()+row.get("spGgh").getFieldValue();
				tmp.put("spId", spBarCodeString);
				tmp.put("spBarCode", spBarCodeString);
				tmp.put("kwCode",
						row.get("kwName").getFieldValue()
								+ row.get("ckCode").getFieldValue());
				String numString = row.get("spNum").getFieldValue();
				
				if(StringUtils.isNotBlank(numString))
				  tmp.put("spNum", StringUtils.left(numString,numString.length()-2));
				newRows.add(tmp);

			}
		}
		ExcelModule excelModule = new ExcelModule(newRows);
		ExcelExpUtil.expExcel(excelModule, "exp_templete_kwsp.xls",
				excelFileName);
		System.out.println("-----------------结束--------------------");
		return data;
	}

	/**
	 * 测试excel导入
	 */
	/*
	 * private static ExcelData testExcelImp() throws Exception { String xmlFile
	 * = "simple_desc.xml"; InputStream importExcelStream =
	 * ExcelUtilTest.class.getResourceAsStream("excel_simple.xls"); ExcelData
	 * data = ExcelImportUtil.readExcel(xmlFile, importExcelStream);
	 * 
	 * System.out.println(data); return data; }
	 *//**
	 * 测试excel导入
	 */
	/*
	 * private static ExcelData testAdvanceExcelImp() throws Exception { String
	 * xmlFile = "advance_desc.xml"; InputStream importExcelStream =
	 * ExcelUtilTest.class.getResourceAsStream("excel_advance.xls"); ExcelData
	 * data = ExcelImportUtil.readExcel(xmlFile, importExcelStream);
	 * System.out.println(data); return data; }
	 */

	/**
	 * 测试excel导出
	 */
	private static void testExcelExp(List<Map<String, String>> sheet_data_all)
			throws Exception {
		ExcelModule excelModule = new ExcelModule(sheet_data_all);
		// 导出excel时使用的模板：exp_templete.xls
		// String templeteFile = new File(System.getProperty("user.dir"),
		// "exp_templete.xls").getAbsolutePath();

		// 导出的文件名：exp_out.xls
		ExcelExpUtil.expExcel(excelModule, "exp_templete.xls", "exp_out.xls");
	}
}
