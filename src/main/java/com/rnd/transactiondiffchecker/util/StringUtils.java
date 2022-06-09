package com.rnd.transactiondiffchecker.util;

public class StringUtils {

    public static String getTrimmedLowerCaseValue(String value) {
        return value != null ? value.trim().toLowerCase() : "";
    }
}
