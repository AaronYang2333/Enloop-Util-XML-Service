package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import lombok.Data;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/22 16:40
 * @email aaron19940628@gmail.com
 * @date 2017/11/22 16:40.
 * @description
 */
@Data
public class ResponseResult<T> {

    private Integer statusCode;

    private String message;

    private T data;

    private String url;

    public ResponseResult() {
    }

    public ResponseResult(T data) {
        this.statusCode = 200;
        this.message = "OK";
        this.data = data;
    }

    public ResponseResult(Integer statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public static <T> ResponseResult ok(T body) {
        return new ResponseResult(body);
    }


}
