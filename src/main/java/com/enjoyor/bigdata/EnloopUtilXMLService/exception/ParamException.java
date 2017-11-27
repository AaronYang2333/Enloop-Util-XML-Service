package com.enjoyor.bigdata.EnloopUtilXMLService.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/23 9:14
 * @email aaron19940628@gmail.com
 * @date 2017/11/23 9:14.
 * @description 参数验证异常，主要来自调用方错误
 */
@Data
public class ParamException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorMsg;

    private Object[] reason;

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public ParamException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ParamException(String errorMsg, HttpStatus status) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.status = status;
    }

    public ParamException(String errorMsg, HttpStatus status, Object... parameters) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.status = status;
        this.reason = parameters;
    }

    public ParamException(Throwable cause) {
        super(cause);
        this.errorMsg = cause.getMessage();
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return "ParamException{" +
                "errorMsg='" + errorMsg + '\'' +
                ", reason=" + Arrays.toString(reason) +
                '}';
    }
}
