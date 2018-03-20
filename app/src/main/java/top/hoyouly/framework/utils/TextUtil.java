package com.example.hoyouly.myframework.utils;

/**
 * Created by hoyouly on 15/5/3.
 */
public class TextUtil {
    /**
     * 判断字符串是否为null，或者是空字符串
     * @param content
     * @return
     */
    public static  boolean isValidate(String content){
        return content!=null&&"".equals(content.trim());
    }
}
