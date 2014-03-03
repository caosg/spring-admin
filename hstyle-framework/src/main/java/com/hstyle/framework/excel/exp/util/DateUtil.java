package com.hstyle.framework.excel.exp.util;

import java.util.Date;

/**
 * 日期格式处理类
 */
public class DateUtil
{
        private DateUtil(){};
        
        /**
         * 返回:20100910210637578
         */
        public static String formate(Date date)
        {
                if(date == null)
                {
                        return "";
                }
                return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", date);
        }
        
        /**
         * 返回:2010-09-10 21:08:17
         */
        public static String formateYMDHMS(Date date)
        {
                if(date == null)
                {
                        return "";
                }
                return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", date);
        }
        
        /**
         * 返回:2010-09-10
         */
        public static String formateYMD(Date date)
        {
                if(date == null)
                {
                        return "";
                }
                return String.format("%1$tY-%1$tm-%1$td", date);
        }
        
        /**
         * 返回:2010年09月10日
         */
        public static String formateYMD_CN(Date date)
        {
                if(date == null)
                {
                        return "";
                }
                return String.format("%1$tY年%1$tm月%1$td日", date);
        }
        
        /**
         * 返回:09-10
         */
        public static String formateMD(Date date)
        {
                if(date == null)
                {
                        return "";
                }
                return String.format("%1$tm-%1$td", date);
        }
        
        /**
         * 返回:09月10日
         */
        public static String formateMD_CN(Date date)
        {
                if(date == null)
                {
                        return "";
                }
                return String.format("%1$tm月%1$td日", date);
        }
        
        public static void main(String[] args)
        {
                System.out.println(formate(new Date()));
                System.out.println(formateYMDHMS(new Date()));
                System.out.println(formateYMD(new Date()));
                System.out.println(formateYMD_CN(new Date()));
                System.out.println(formateMD(new Date()));
                System.out.println(formateMD_CN(new Date()));
        }
}
