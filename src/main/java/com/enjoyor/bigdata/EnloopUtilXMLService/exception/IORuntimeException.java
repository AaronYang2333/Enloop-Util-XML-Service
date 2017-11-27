package com.enjoyor.bigdata.EnloopUtilXMLService.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 16:23
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 16:23.
 * @description
 */
public class IORuntimeException extends RuntimeException {
    private static final long serialVersionUID = 4L;

    private String errorMsg;

    private Class<?> clazz;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public IORuntimeException(Class<?> clazz, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.clazz = clazz;
    }
}
