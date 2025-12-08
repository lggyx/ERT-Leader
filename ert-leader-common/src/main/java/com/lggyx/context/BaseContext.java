package com.lggyx.context;

/**
 *
 */
public class BaseContext {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentAccount(String account) {
        threadLocal.set(account);
    }

    public static String getCurrentAccount() {
        return threadLocal.get();
    }

    public static void removeCurrentAccount() {
        threadLocal.remove();
    }

}