package com.enjoyor.bigdata.EnloopUtilXMLService.utils;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ParamException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/23 9:12
 * @email aaron19940628@gmail.com
 * @date 2017/11/23 9:12.
 * @description 自定义参数异常
 */
public class ParamAssert {
    public static void isBlank(String str, String errorMsg) {
        if (StringUtils.isBlank(str)) {
            fail(errorMsg,HttpStatus.BAD_REQUEST);
        }
    }

    public static void isNull(Object object, String errorMsg) {
        if (object == null) {
            fail(errorMsg, object);
        }
    }

    public boolean isJavaClass(Class<?> clazz) {
        return clazz != null && clazz.getClassLoader() == null;
    }

    public static void isNull(Object... object) {
        if (object == null || object.length == 0) {
            fail("NullPointException", object);
        }
    }

    public static void check(boolean condition, String errorMsg, Object... parameters) {
        if (!condition) {
            fail(errorMsg, parameters);
        }
    }

    private static void fail(String errorMsg, Object... parameters) {
        throw new ParamException(errorMsg, HttpStatus.UNPROCESSABLE_ENTITY, parameters);
    }

    private static void fail(String errorMsg, HttpStatus httpStatus) {
        throw new ParamException(errorMsg, httpStatus);
    }
}
