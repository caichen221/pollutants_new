package com.iscas.common.tools.core.io.file;

import java.util.*;
import java.io.*;

@Deprecated
public class PropsUtils {

     public PropsUtils() {

     }

    public String getPropsFilePath()
    {
        String filePath = this.getClass().getResource("/").getPath().toString();
        filePath = filePath.substring(0, filePath.indexOf("classes")-1) + "/destinations.properties";
        return filePath;
    }

    public InputStream getPropsIS()
    {
        InputStream ins = this.getClass().getResourceAsStream("/destinations.properties");
        return ins;
    }



    /**
     * 获取字符型属性值
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param attr 属性key
     * @throws
     * @return java.lang.String
     */
    public String readSingleProps(String attr){
        String retValue = "";
        Properties props = new Properties();
        try {
            /*if (!FileUtil.isFileExist(getPropsFilePath())) {
                return "";
            }
            FileInputStream fi = new FileInputStream(getPropsFilePath());*/
            InputStream fi = getPropsIS();
            props.load(fi);
            fi.close();

            retValue = props.getProperty(attr);
        } catch (Exception e) {
            return "";
        }
        return retValue;
    }

    /**
     * 获取所有属性
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @throws
     * @return java.util.HashMap
     */
    public HashMap readAllProps(){

        HashMap h = new HashMap();
        Properties props = new Properties();

        try {

            InputStream fi = getPropsIS();
            props.load(fi);
            fi.close();
            Enumeration er = props.propertyNames();
            while (er.hasMoreElements()) {
                String paramName = (String) er.nextElement();
                h.put(paramName, props.getProperty(paramName));
            }
        } catch (Exception e) {
            return new HashMap();
        }
        return h;
    }
}
