package com.hstyle.framework.license;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

/** 
 * <p> MacAddressUtil.java </p> 
 * 读取机器的Mac地址。
 * 
 */
public class MacAddressUtil {

    static String getMacOnWindow() {
        String s = "";
        try {
            String s1 = "ipconfig /all";
            Process process = Runtime.getRuntime().exec(s1);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedreader.readLine();
            do {
                if(line == null)
                    break;
                String nextLine = bufferedreader.readLine();
                String nextString = new String(nextLine.getBytes("ISO-8859-1"),"GBK");
                System.out.println(nextString);
                if(line.indexOf("物理地址") > 0) {
                    int i = line.indexOf("物理地址") + 36;
                    s = line.substring(i);
                    break;
                }
                line = nextLine;
            } while(true);
            bufferedreader.close();
            process.waitFor();
        } catch(Exception e) {
            e.printStackTrace();
            s = "";
        }
        return s.trim();
    }

    static String getMacOnUnix() {
        String s = "";
        try {
            String s1 = "ifconfig";
            Process process = Runtime.getRuntime().exec(s1);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedreader.readLine();
            do {
                if(line == null)
                    break;
                String nextLine = bufferedreader.readLine();
                if(line.indexOf("HWaddr") > 0)
                {
                    int i = line.indexOf("HWaddr") + 7;
                    s = line.substring(i);
                    break;
                }
                line = nextLine;
            } while(true);
            bufferedreader.close();
            process.waitFor();
        } catch(Exception e) {
            e.printStackTrace();
            s = "";
        }
        return s.trim().replace(':', '-');
    }

    static String getMacOnAix() {
        String s = "";
        try {
            String s1 = "netstat -ia";
            Process process = Runtime.getRuntime().exec(s1);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedreader.readLine();
            if(line != null) {
                String nextLine = bufferedreader.readLine();
                String datas[] = nextLine.split(" ");
                s = datas[3];
            }
            bufferedreader.close();
            process.waitFor();
        } catch(Exception e) {
            e.printStackTrace();
            s = "";
        }
        return s.trim().replace('.', '-');
    }

    public static String getMacAddress(){
        String os = ((String)System.getProperties().get("os.name")).toUpperCase();
        System.out.println(os);
        if(os.indexOf("WINDOWS") >= 0)
            return getMacOnWindow();
        if(os.indexOf("AIX") >= 0)
            return getMacOnAix();
        else
            return getMacOnUnix();
        
        //return "6C-62-6D-C6-49-9E";
    }
    public static void main(String args[]){
    	System.out.println("0000000000");
    	System.out.println(MacAddressUtil.getMacAddress());
    }
}