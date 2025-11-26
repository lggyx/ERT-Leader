package com.lggyx.utils;

import java.util.regex.Pattern;

public final class ValidatorUtils {

    /** 中国手机号：1 开头，第二位 3-9，后面 9 位数字 */
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^1[3-9]\\d{9}$");

    /** 邮箱：local@domain，允许数字字母下划线、中划线、点号 */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static boolean isMobile(String str) {
        return str != null && MOBILE_PATTERN.matcher(str).matches();
    }

    public static boolean isEmail(String str) {
        return str != null && EMAIL_PATTERN.matcher(str).matches();
    }

}