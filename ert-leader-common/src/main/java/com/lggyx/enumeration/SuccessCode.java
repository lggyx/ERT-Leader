package com.lggyx.enumeration;

import lombok.Getter;

/**
 * 描述: 成功码枚举
 */
@Getter
public enum SuccessCode {
    SUCCESS(200, "操作成功"),
    SUCCESS_NO_DATA(201, "成功，无数据");

    private final int code;
    private final String msg;

    SuccessCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
