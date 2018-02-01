package com.enjoyor.bigdata.EnloopUtilXMLService.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/12/6 16:56
 * @email aaron19940628@gmail.com
 * @date 2017/12/6 16:56.
 * @description
 */
public class TransformRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    private String errorMsg;

    private Class<?> clazz;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public TransformRuntimeException(Class<?> clazz, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.clazz = clazz;
    }

    public TransformRuntimeException( String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.clazz = null;
    }
}
