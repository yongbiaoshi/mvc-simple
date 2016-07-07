package com.tsingda.simple.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MybatisGeneratorUtil {
    public static String tableNameToClassName(String tableName){
        //首字母转大写
        String className = (tableName.charAt(0) + "").toUpperCase() + tableName.substring(1);
        //下划线后第一个字母转大字
        Pattern p = Pattern.compile("_[a-z]");
        Matcher matcher = p.matcher(className);
        while(matcher.find()){
            String str = matcher.group();
            className = className.replace(str, str.replace("_", "").toUpperCase());
        }
        return className;
    }
    
    public static void main(String[] args) {
        System.out.println(String.format("%04d", 1));
        String ts = "mall_attr_group,mall_attr,mall_activity_source,mall_activity_unit,mall_activity_category,mall_activity_group,mall_activity,mall_attr_group_pic_bank,mall_activity_order,mall_activity_enroll";
        String[] tns = ts.split(",");
        String template = "<table tableName=\"%s\" domainObjectName=\"%s\" enableCountByExample=\"false\" enableUpdateByExample=\"false\" enableDeleteByExample=\"false\" enableSelectByExample=\"false\" selectByExampleQueryId=\"false\"></table>";
        for (String tableName : tns) {
            String className = tableNameToClassName(tableName);
//            System.out.println(className);
            System.out.println(String.format(template, tableName, className));
        }
    }
}
