package com.wangwb.web.common.util;

public class StringUtil {

	/**
	 * string字符过滤
	 */
	public static String strFiltrate(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&#x3C;");
        str = str.replaceAll(">", "&#x3E;");
        str = str.replaceAll("\"", "");
        str = str.replaceAll("\'", "");
        str = str.replaceAll("%", "");
        str = str.replaceAll("eval", "");
        str = str.replaceAll("expression", "");
        str = str.replaceAll("unescape", "");
        str = str.replaceAll(";", "；");
        str = str.replaceAll(":", "：");
        str = str.replaceAll("null", "");
        return str;
    }
	
	/**
	 * string null转空
	 */
	public static final String nullToEmpty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return (obj + "").trim();
        }
    }
	
	/**
     * 	判断是否不为null
     * @param string
     * @return boolean
     */
	public static final boolean isNotEmpty(String string) {
		return string != null &&(!string.equals(""))&& string.length() > 0;
	}
	
	/**
	 * 	判断是否为null
	 * @param string
	 * @return boolean
	 */
	public static final boolean isEmpty(String string) {
		return string == null || string.length() == 0|| "null".equals(string);
	}
	
}
