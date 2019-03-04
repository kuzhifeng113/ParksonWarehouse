package com.woyun.warehouse.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    //乘---toString
    public static double getMultiply(double x, double y) {
        BigDecimal x1 = new BigDecimal(Double.toString(x));
        BigDecimal y1 = new BigDecimal(Double.toString(y));
        return x1.multiply(y1).doubleValue();
    }

    //加
    public static double getAdd(double x, double y) {
        BigDecimal x1 = new BigDecimal(Double.toString(x));
        BigDecimal y1 = new BigDecimal(Double.toString(y));
        return x1.add(y1).doubleValue();
    }

    //- x-y
    public static double geSub(double x, double y) {
        BigDecimal x1 = new BigDecimal(Double.toString(x));
        BigDecimal y1 = new BigDecimal(Double.toString(y));
        return x1.subtract(y1).doubleValue();
    }
}
