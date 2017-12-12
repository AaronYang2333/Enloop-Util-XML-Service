package com.enjoyor.bigdata.EnloopUtilXMLService.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 15:03
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 15:03.
 * @description
 */
@Data
public class Dom4jException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorMsg;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public Dom4jException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public Dom4jException(String errorMsg, HttpStatus status) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.status = status;
    }
}
