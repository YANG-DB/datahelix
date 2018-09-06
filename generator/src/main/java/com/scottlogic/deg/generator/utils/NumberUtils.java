package com.scottlogic.deg.generator.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParsePosition;

public class NumberUtils {
    public static boolean isInteger(BigDecimal decimalValue) {
        // stolen from https://stackoverflow.com/questions/1078953/check-if-bigdecimal-is-integer-value
        return decimalValue.signum() == 0 || decimalValue.scale() <= 0 || decimalValue.stripTrailingZeros().scale() <= 0;
    }

    public static BigDecimal coerceToBigDecimal( Object value ) {
        // stolen from http://www.java2s.com/Code/Java/Data-Type/ConvertObjecttoBigDecimal.htm
        if (value == null)
            return null;
        else if (value instanceof BigDecimal)
            return (BigDecimal)value;
        else if (value instanceof String)
            return (BigDecimal) bigDecimalFormatter.parse((String)value, new ParsePosition(0));
        else if (value instanceof BigInteger)
            return new BigDecimal((BigInteger)value);
        else if (value instanceof Number)
            return new BigDecimal(((Number)value).doubleValue());
        else
            return null;
    }

    private static final DecimalFormat bigDecimalFormatter;

    static {
        bigDecimalFormatter = new DecimalFormat();
        bigDecimalFormatter.setParseBigDecimal(true);
    }

    // static class
    private NumberUtils() { }
}
