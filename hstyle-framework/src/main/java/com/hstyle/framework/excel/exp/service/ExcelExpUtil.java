package com.hstyle.framework.excel.exp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.hstyle.framework.excel.exp.module.ExcelModule;
import com.hstyle.framework.excel.exp.util.DateUtil;
import com.hstyle.framework.excel.imp.util.StringUtil;

/**
 * Excel导出工具类,使用POI的API操作Excel 
 * <pre>
 * (1)sheet中, 
 *              a.可以为空,
 *              b.可以只有#colName#,如:客户名称:#CUST_NAME# 
 *              c.可以只有%colName%,如:产品名称:%PRO_NAME%
 *              e.可以只有普通的文本,而没有 #colName#、%colName% 
 *              f.可以都有 
 *              g.可以设置筛选功能 
 *              h.可以给单元格设置公式
 * </pre>
 */
@SuppressWarnings("deprecation")
public class ExcelExpUtil
{
        private final static Pattern p1 = Pattern.compile("^.*#(.+)#.*$");
        private final static Pattern p2 = Pattern.compile("^.*%(.+)%.*$");
        private final static String STRNUM = "%STRNUM%";
        private static HSSFCellStyle cellStyle; // 自动换行的样式
        private static String DEFAULT_PATH="/excel-desc/";//模板文件默认路径
        private ExcelExpUtil()
        {
        }
        /**
         * 导出Excel到输出流
         * @param excelModule 输出时所需要的数据
         * @param templeteFile 模板文件名 ,放在默认路径下
         * @param os 输出流
         * @throws Exception 
         */
        public static void expExcel(ExcelModule excelModule, String templeteFile,OutputStream os) throws Exception{
        	if(StringUtil.isEmpty(templeteFile)||excelModule==null)
            {
                   return;
            }
        	expExcel(excelModule,templeteFile,os,false);
        }
        
        /**
         * 生成Excel文件
         * 通常情况下，不需要知道最终生成的临时文件存放在哪里，WEB中生成完后输出到客户端后会删除，所以，一般不需要传入这个参数
         * @param excelModule
         *            输出时所需要的数据
         * @param templeteFile
         *            模板文件名 ,放在默认路径下
         * @return 所导出的文件
         */
        public static File expExcel(ExcelModule excelModule, String templeteFile) throws Exception
        {
                if(StringUtil.isEmpty(templeteFile))
                {
                        return null;
                }
                File file = new File(templeteFile);
                String tmpFile = file.getName();
                return expExcel(excelModule, templeteFile, tmpFile);
        }
        public static File expExcel(ExcelModule excelModule, String templeteFile, String tmpFile) throws Exception
        {
        	   return expExcel(excelModule, templeteFile, tmpFile,false);
        }
        /**
         * 导出Excel
         * 
         * @param excelModule
         *            输出时所需要的数据
         * @param templeteFile
         *            模板文件名（带绝对路径）
         * @param tmpFile
         *            导出文件名（带路径）
         * @param isAbsolute true：模板文件绝对路径  false：默认路径
         * @return 所导出的文件
         */
        public static File expExcel(ExcelModule excelModule, String templeteFile, String tmpFile,boolean isAbsolute) throws Exception
        { 
        	FileInputStream fis=null;
        	    if(isAbsolute)
                   fis = new FileInputStream(templeteFile);
        	    else {
					fis=new FileInputStream(ExcelExpUtil.class.getResource(DEFAULT_PATH+templeteFile).getFile());
				}
                return expExcel(excelModule, fis, tmpFile);
        }
        /**
         * 导出Excel到输出流
         * 
         * @param excelModule
         *            输出时所需要的数据
         * @param templeteFile
         *            模板文件名（带绝对路径）
         * @param OutputStream
         *            输出流
         * @param isAbsolute true：模板文件绝对路径  false：默认路径
         * @return 所导出的文件
         */
        public static void expExcel(ExcelModule excelModule, String templeteFile,OutputStream os,boolean isAbsolute) throws Exception
        { 
        	FileInputStream fis=null;
        	 if(isAbsolute)
                fis = new FileInputStream(templeteFile);
        	 else 
				fis=new FileInputStream(ExcelExpUtil.class.getResource(DEFAULT_PATH+templeteFile).getFile());
             expExcel(excelModule, fis, os);
        }
        /**
         * @param excelModule 输出时所需要的数据
         * @param fis   模板文件
         * @param tmpFile  导出文件名（带路径）
         * @return
         * @throws Exception
         */
        public static File expExcel(ExcelModule excelModule, FileInputStream fis, String tmpFile) throws Exception
        {
                
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                cellStyle = wb.createCellStyle();
                for (int i = 0; i < wb.getNumberOfSheets(); i++)
                {
                        HSSFSheet sheet = wb.getSheetAt(i);
                        // 如果当前sheet为空,或者行全部 为空,则不用设值了。
                        if (sheet == null || sheet.getPhysicalNumberOfRows() == 0)
                        {
                                continue;
                        }
                        // 设置sheet名称
                        if(StringUtil.isNotEmpty(excelModule.getSheetName(i)))
                        {
                                wb.setSheetName(i, excelModule.getSheetName(i));
                        }
                        // 得到%STRNUM%的行标
                        int strNumRow = getRowNum(sheet);
                        // 1.设置#colName#的值,（这里不能根据wb、sheet得到sheet序号,只能传递下去。poi3.5版本可以的）
                        setOnceValue(wb, sheet, i, excelModule, strNumRow);
                        // 2.设置%colName%的值
                        setMultiValue(wb, sheet, i, excelModule, strNumRow);
                }
                // 3.生成结果文件
                return createResultFile(wb, fis, tmpFile);
        }
        /**
         * 生成excel 输出流
         * @param excelModule 输出时所需要的数据
         * @param fis   模板文件
         * @param OutputStream  输出流
         * @return
         * @throws Exception
         */
        public static void expExcel(ExcelModule excelModule, FileInputStream fis, OutputStream os) throws Exception
        {
                
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                cellStyle = wb.createCellStyle();
                for (int i = 0; i < wb.getNumberOfSheets(); i++)
                {
                        HSSFSheet sheet = wb.getSheetAt(i);
                        // 如果当前sheet为空,或者行全部 为空,则不用设值了。
                        if (sheet == null || sheet.getPhysicalNumberOfRows() == 0)
                        {
                                continue;
                        }
                        // 设置sheet名称
                        if(StringUtil.isNotEmpty(excelModule.getSheetName(i)))
                        {
                                wb.setSheetName(i, excelModule.getSheetName(i));
                        }
                        // 得到%STRNUM%的行标
                        int strNumRow = getRowNum(sheet);
                        // 1.设置#colName#的值,（这里不能根据wb、sheet得到sheet序号,只能传递下去。poi3.5版本可以的）
                        setOnceValue(wb, sheet, i, excelModule, strNumRow);
                        // 2.设置%colName%的值
                        setMultiValue(wb, sheet, i, excelModule, strNumRow);
                }
                // 3.生成结果写入输出流
                createResultFile(wb, fis, os);
        }
        
        private static void setMultiValue(HSSFWorkbook wb, HSSFSheet sheet,
                        int sheetIndex, ExcelModule excelData, int strNumRow)
        {
                // 得到要设置的数据信息
                List<Map<String, String>> list = excelData.getMultData(sheetIndex);
                // 如果没有找到%STRNUM%,则表示不需要再进行设值了,直接返回
                if (strNumRow == -1)
                {
                        return;
                }
                
                // 复制一些空行
                copyRow(wb, sheet, list, strNumRow);
                // 将数据设置到%rowName%单元格中
                setMultiValue(sheet, list, strNumRow);
        }

        private static void setMultiValue(HSSFSheet sheet,
                        List<Map<String, String>> list, int strNumRow)
        {
                if (list == null || list.size() <= 0)
                {
                        return;
                }
                // 1.得到%STRNUM%的行标
                // 2.遍历每一行,设值
                for (int i = strNumRow; i <= (strNumRow + list.size() - 1); i++)
                {
                        HSSFRow row = sheet.getRow(i);
                        if (row == null)
                        {
                                continue;
                        }
                        for (short j = 0; j <= row.getLastCellNum(); j++)
                        {
                                HSSFCell cell = row.getCell(j);
                                setAutoLine(cell); // 设置自动换行
                                if (cell == null)
                                {
                                        continue;
                                }
                                String cellValue = cell.getStringCellValue();
                                if (STRNUM.equals(cellValue))
                                {
                                        cell.setCellValue("" + (i - strNumRow + 1));
                                } else
                                {
                                        setMultiValue(list.get(i - strNumRow), cell);
                                }
                        }
                }
        }

        private static void setMultiValue(Map<String, String> data, HSSFCell cell)
        {
                if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING)
                { // 如果单元格不是String类型
                        return;
                }
                String oldValue = cell.getStringCellValue();
                if (StringUtil.isEmpty(oldValue) || oldValue == null
                                || oldValue.length() <= 0)
                { // 如果单元格信息为空
                        return;
                }
                // 从%colName%中得到 colName 信息
                Matcher m = p2.matcher(oldValue);
                if (m.find())
                {
                        String colName = m.group(1);
                        String target = data.get(colName);
                        if (target == null)
                                target = "";
                        // 替换数据
                        String newValue = oldValue.replace("%" + colName + "%", (colName == null || colName.length() <= 0) ? "" : target);
                        cell.setCellValue(newValue);
                }
        }

        private static void copyRow(HSSFWorkbook wb, HSSFSheet sheet, List<Map<String, String>> list, int strNumRow)
        {
                // 0.最后一行行标
                int endRow = sheet.getLastRowNum();
                // 1.得到%STRNUM%的行标
                // 2.如果list为空,则清空 strNumRow 行
                if (list == null)
                {
                        HSSFRow hssfRow = sheet.getRow(strNumRow);
                        if(hssfRow == null)
                        {
                                return;
                        }
                        int first = hssfRow.getFirstCellNum();
                        int last = hssfRow.getLastCellNum();
                        for(int i = first; i <= last; i++)
                        {
                                HSSFCell cell = hssfRow.getCell((short)i);
                                if(cell == null)
                                {
                                        continue;
                                }
                                cell.setCellValue("");
                        }
                        return;
                }
                // 3.复制行数据
                if(strNumRow != endRow && list.size() > 1)
                        sheet.shiftRows(strNumRow + 1, endRow, list.size() - 1); // 向下移动,从而实现复制空行,
                // 标准行
                HSSFRow templeteRow = sheet.getRow(strNumRow);
                // 4.复制单元格信息:数据(%colName%)、格式(字体、样式、合并单元格)
                HSSFCellStyle style = wb.createCellStyle();
                for (int i = strNumRow + 1; i <= (strNumRow + list.size() - 1); i++)
                {
                        HSSFRow row = sheet.getRow(i);
                        row = ((row == null) ? (sheet.createRow(i)) : row);
                        row.setHeight(templeteRow.getHeight()); // 拷贝行的高度
                        for (short j = 0; j <= templeteRow.getLastCellNum(); j++)
                        {
                                HSSFCell templeteCell = templeteRow.getCell(j);
                                HSSFCell cell = row.createCell(j);
                                // 值
                                String value = null;
                                
                                if (templeteCell != null)
                                {
                                        value = templeteCell.getStringCellValue();
                                        style = templeteCell.getCellStyle();
                                }
                                cell.setCellValue(value);
                                cell.setCellStyle(style);
                        }
                }
                // 5.合并单元格
                dealCellMergedRegion(sheet, strNumRow, list.size());
        }

        /**
         * 拷贝 合并单元格 的设置
         */
        private static void dealCellMergedRegion(HSSFSheet sheet, int strNumRow,
                        int size)
        {
                // 需要合并单元格的 行 区间
                int startRow = strNumRow + 1;
                int endRow = strNumRow + size - 1;
                // 总共数量
                int sum = sheet.getNumMergedRegions();
                for (int i = 0; i < sum; i++)
                {
                        Region range = sheet.getMergedRegionAt(i);
                        if (range == null)
                        {
                                continue;
                        }
                        int firstRow = range.getRowFrom();
                        int lastRow = range.getRowTo();
                        // 如果 是 %STRNUM% 这行
                        if (firstRow == strNumRow && lastRow == strNumRow)
                        {
                                short firstCol = range.getColumnFrom();
                                short lastCol = range.getColumnTo();
                                // 拷贝 合并单元格 的设置
                                for (int j = startRow; j <= endRow; j++)
                                {
                                        Region region = new Region(j, firstCol, j, lastCol);
                                        sheet.addMergedRegion(region);
                                }
                        }
                }
        }

        /**
         * 找到%STRNUM%的行标 如果没有找到,则返回-1:
         */
        private static int getRowNum(HSSFSheet sheet)
        {
                int startRow = sheet.getFirstRowNum();
                int endRow = sheet.getLastRowNum();
                // 遍历每一行
                for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++)
                {
                        HSSFRow row = sheet.getRow(rowIndex);
                        if (row == null)
                        {
                                continue;
                        }
                        // 遍历每一个单元格
                        short startCol = row.getFirstCellNum();
                        short endCol = row.getLastCellNum();
                        for (short colIndex = startCol; colIndex <= endCol; colIndex++)
                        {
                                HSSFCell cell = row.getCell(colIndex);
                                if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING || StringUtil.isEmpty(cell.getStringCellValue()))
                                {
                                        continue;
                                } else
                                {
//                                      if (STRNUM.equals(cell.getStringCellValue()))
//                                      {
//                                              return rowIndex;
//                                      }
                                        // 从%colName%中得到 colName 信息
                                        Matcher m = p2.matcher(cell.getStringCellValue());
                                        if (m.find())
                                        {
                                                return rowIndex;
                                        }
                                }
                        }
                }
                // throw new RuntimeException("异常:模板中没有找到"+STRNUM+"信息");
                return -1;
        }

        /**
         * 设置唯一值
         */
        private static void setOnceValue(HSSFWorkbook wb, HSSFSheet sheet,
                        int sheetIndex, ExcelModule excelData, int strNumRow) throws Exception
        {
                // 得到要设置的数据信息
                Map<String, String> data = excelData.getOnceData(sheetIndex);
                if (data == null || data.size() <= 0)
                {
                        return;
                }
                int startRow = sheet.getFirstRowNum();
                int endRow = sheet.getLastRowNum();
                // 遍历每一行
                for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++)
                {
                        HSSFRow row = sheet.getRow(rowIndex);
                        if (row == null)
                        {
                                continue;
                        }
                        // 遍历每一个单元格
                        short startCol = row.getFirstCellNum();
                        short endCol = row.getLastCellNum();
                        for (short colIndex = startCol; colIndex <= endCol; colIndex++)
                        {
                                HSSFCell cell = row.getCell(colIndex);
                                if (cell == null)
                                {
                                        continue;
                                } else
                                {
                                        // 取得##数据并且替换成值
                                        setOnceValue(data, cell);
                                }
                        }
                }
        }

        private static void setOnceValue(Map<String, String> data, HSSFCell cell)
        {
                if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING)
                { // 如果单元格不是String类型
                        return;
                }
                setAutoLine(cell); // 设置自动换行
                String oldValue = cell.getStringCellValue();
                if (oldValue == null || oldValue.length() <= 0)
                { // 如果单元格信息为空
                        return;
                }
                // 处理回车换行符,如果含有回车换行符,则这个正则表达式的匹配会有问题
                String str = oldValue.replaceAll("\n", " ").replace("\r", " ");
                // 从#colName#中得到 colName 信息
                Matcher m = p1.matcher(str);
                if (m.find())
                {
                        String colName = m.group(1);
                        // 替换数据
                        String newValue = oldValue.replace("#" + colName + "#", (colName == null || colName.length() <= 0) ? "" : data.get(colName));
                        cell.setCellValue(newValue);
                }
        }

        /**
         * 产生结果文件
         */
        private static File createResultFile(HSSFWorkbook wb, FileInputStream fis, String tmpFile) throws Exception
        {
                // 防止多个线程同时输出时，文件覆盖。
                String fileName = generateFileName(tmpFile);
                
                FileOutputStream fos = new FileOutputStream(fileName);
                wb.write(fos);
                fos.close();
                fis.close();
                return new File(fileName);
        }
        
        /**
         * 将结果写入输出流
         */
        private static void createResultFile(HSSFWorkbook wb, FileInputStream fis, OutputStream os) throws Exception
        {
                
                wb.write(os);
                os.close();
                fis.close();
               
        }
        
        private static String generateFileName(String fileName)
        {
                if(StringUtil.isEmpty(fileName))
                {
                        return null;
                }
                String dateString = DateUtil.formate(new Date());
                int index = fileName.lastIndexOf(".");
                if(index == -1)
                {
                        return fileName + dateString;
                }
                return fileName.substring(0, index) + dateString + fileName.substring(index);
        }
        
        public static void main(String[] args)
        {
                System.out.println(generateFileName("src.xls"));
        }

        /**
         * 设置单元格自动换行
         * 
         * @param cell
         */
        private static void setAutoLine(HSSFCell cell)
        {
                if (cell == null)
                        return;
                try
                {
                        HSSFCellStyle style = cell.getCellStyle();
                        if (style == null)
                                style = cellStyle;
                        style.setWrapText(true);
                        cell.setCellStyle(style);
                } catch (Exception e)
                {
                }
        }
}