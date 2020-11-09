package com.wangwb.web.common.util;

import java.math.BigDecimal;

/**
 * 十进制计算
 * @author wangwb@sparknet.com.cn
 *
 */
public class DecimalUtil {

	/**
	 * 两数相加
	 * @param var1
	 * @param var2
	 * @return
	 */
	public static double add(double var1, double var2) {
		BigDecimal b1 = new BigDecimal(Double.toString(var1));
		BigDecimal b2 = new BigDecimal(Double.toString(var2));
		return b1.add(b2).doubleValue();
	}

   /**
    * 两数相减var1-var2
    * @param var1
    * @param var2
    * @return
    */
	public static double sub(double var1, double var2) {
		BigDecimal b1 = new BigDecimal(Double.toString(var1));
		BigDecimal b2 = new BigDecimal(Double.toString(var2));
		return b1.subtract(b2).doubleValue();
	}

    /**
     * 两数相乘
     * @param var1
     * @param var2
     * @return
     */
	public static double mul(double var1, double var2) {
		BigDecimal b1 = new BigDecimal(Double.toString(var1));
		BigDecimal b2 = new BigDecimal(Double.toString(var2));
		return b1.multiply(b2).doubleValue();
    }

    /**
     * 两数相除
     * @param v1
     * @param v2
     * @param scale  保留位数
     * @return
     */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or ");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     * @param v
     * @param scale
     * @return
     */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
}
