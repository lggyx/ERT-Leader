package com.lggyx.result;

import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.SuccessCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    @Schema(description = "状态码")
    private int code;   // 编码：1成功，其它值为错误码
    @Schema(description = "错误信息")
    private String msg; // 错误信息
    @Schema(description = "数据")
    private T data;     // 数据

    /*=========== 成功 ===========*/
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = "操作成功";
        result.data = object;
        return result;
    }
    public static <T> Result<T> success(SuccessCode successCode, T object) {
        Result<T> result = new Result<>();
        result.code = successCode.getCode();
        result.msg = successCode.getMsg();
        result.data = object;
        return result;
    }
    public static <T>  Result<T> success(SuccessCode successCode) {
        Result<T> result = new Result<>();
        result.code = successCode.getCode();
        result.msg = successCode.getMsg();
        return result;
    }
    /*=========== 失败 ===========*/

    /**
     * 兼容老代码：自定义错误信息
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 0;   // 默认通用错误码 0
        result.msg = msg;
        return result;
    }

    /**
     * 推荐用法：直接传枚举
     */
    public static <T> Result<T> error(ErrorCode errorCode) {
        Result<T> result = new Result<>();
        result.code = errorCode.getCode();
        result.msg = errorCode.getMsg();
        return result;
    }


}