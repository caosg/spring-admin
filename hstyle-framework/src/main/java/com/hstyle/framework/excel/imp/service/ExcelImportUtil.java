package com.hstyle.framework.excel.imp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.JDOMException;

import com.hstyle.framework.excel.imp.bean.ExcelData;
import com.hstyle.framework.excel.imp.bean.ExcelImportException;
import com.hstyle.framework.excel.imp.bean.ExcelStruct;
import com.hstyle.framework.excel.imp.bean.SimpleExcelData;
import com.hstyle.framework.excel.imp.util.ExcelDataReader;
import com.hstyle.framework.excel.imp.util.ExcelDataUtil;
import com.hstyle.framework.excel.imp.util.ParseXMLUtil;
import com.hstyle.framework.excel.test.ExcelUtilTest;

/**
 * 读取导入的Excel的内容
 * 模板要求：
 * （1）开始重复行与End行 有且只能有 一空行
 */
public class ExcelImportUtil
{
	    private static String DEFAULT_PATH="/excel-desc/";
        private ExcelImportUtil(){}
        
        // private static Logger log = Logger.getLogger(ExcelImportUtil.class);
        /**
         * @param xmlFile  默认放在 /excel-desc/目录下
         * @param importExcelStream
         * @return
         * @throws ExcelImportException
         */
        public static ExcelData readExcel(String xmlFile, InputStream importExcelStream) throws ExcelImportException{
        	InputStream descXml=ExcelImportUtil.class.getResourceAsStream(DEFAULT_PATH+xmlFile);
        	return readExcel(descXml, importExcelStream);
        }
        
        /**
         * 读取导入的Excel的文件内容
         * @param xmlFile                       描述被导入的Excel的格式的XML文件
         * @param importExcelStream     被导入的Excel文件
         * @return                                      Excel中需要导入的数据
         */
        public static ExcelData readExcel(InputStream xmlFile, InputStream importExcelStream) throws ExcelImportException
        {
             
                try
                {
                        // 1. 解析XML描述文件
                        ExcelStruct excelStruct = ParseXMLUtil.parseImportStruct(xmlFile);
                        // 2. 按照XML描述文件，来解析Excel中文件的内容
                        return ExcelDataReader.readExcel(excelStruct, importExcelStream, 0);
                }
                catch (FileNotFoundException e)
                {
                        e.printStackTrace();
                        // log.error("导入Excel失败 - XML描述文件未找到 : ", e);
                        throw new ExcelImportException("导入Excel失败 - XML描述文件未找到 : ", e);
                }
                catch (JDOMException e)
                {
                        e.printStackTrace();
                        // log.error("导入Excel失败 - 解析XML描述文件异常 : ", e);
                        throw new ExcelImportException("导入Excel失败 - 解析XML描述文件异常 : ", e);
                }
                catch (IOException e)
                {
                        e.printStackTrace();
                        // log.error("导入Excel失败 - IO异常 : ", e);
                        throw new ExcelImportException("导入Excel失败 - IO异常 : ", e);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        // log.error("导入Excel失败 : ", e);
                        throw new ExcelImportException("导入Excel失败 : ", e);
                }
        };
        
        /**
         * 读取导入的Excel的文件内容
         * @param xmlFile                       描述被导入的Excel的格式的XML文件
         * @param importExcelStream     被导入的XML文件
         * @return                                      Excel中需要导入的数据
         */
        public static SimpleExcelData simpleReadExcel(InputStream xmlFile, InputStream importExcelStream) throws ExcelImportException
        {
                ExcelData excelData = readExcel(xmlFile, importExcelStream);
                return ExcelDataUtil.changeExcelDataToSimple(excelData);
        }
}
