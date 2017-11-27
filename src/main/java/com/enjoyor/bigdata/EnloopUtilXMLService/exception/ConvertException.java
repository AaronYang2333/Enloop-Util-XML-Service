package com.enjoyor.bigdata.EnloopUtilXMLService.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 14:00
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 14:00.
 * @description 类型转换异常，主要发生在服务器内部
 */
@Data
public class ConvertException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    private String errorMsg;

    private Object reason;

    private Class<?> clazz;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ConvertException(Object reason, Class<?> clazz, String errorMsg) {
        super(errorMsg);
        this.reason = reason;
        this.clazz = clazz;
        this.errorMsg = errorMsg;
    }

    @Deprecated
    public ConvertException(Object reason, Class<?> clazz, String errorMsg,HttpStatus status) {
        super(errorMsg);
        this.reason = reason;
        this.clazz = clazz;
        this.errorMsg = errorMsg;
        this.status = status;
    }
}
