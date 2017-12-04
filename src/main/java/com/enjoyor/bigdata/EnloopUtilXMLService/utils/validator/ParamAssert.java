package com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ParamException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/23 9:12
 * @email aaron19940628@gmail.com
 * @date 2017/11/23 9:12.
 * @description 自定义参数异常
 */
public class ParamAssert {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @param errorMsg
     */
    public static void isBlank(String str, String errorMsg) {
        if (StringUtils.isBlank(str)) {
            fail(errorMsg, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 判断对象是否为NULL
     *
     * @param object
     * @param errorMsg
     */
    public static void isNull(Object object, String errorMsg) {
        if (object == null) {
            fail(errorMsg, object);
        }
    }

    /**
     * 判断是否是JDK自带类型
     *
     * @param clazz
     * @return
     */
    public static boolean isJavaClass(Class<?> clazz) {
        return clazz != null && clazz.getClassLoader() == null;
    }

    /**
     * 常规检查，若为空，则抛出异常
     *
     * @param condition
     * @param errorMsg
     * @param parameters
     */
    public static void check(boolean condition, String errorMsg, Object... parameters) {
        if (!condition) {
            fail(errorMsg, parameters);
        }
    }

    /**
     * 判断字符串是否为空，为空则替换
     *
     * @param originalStr
     * @param alternativeStr
     */
    public static String ifNullThenReplace(String originalStr, String alternativeStr) {
        if (null == originalStr || originalStr.length() == 0) {
            return alternativeStr;
        } else {
            return originalStr;
        }
    }

    private static void fail(String errorMsg, Object... parameters) {
        throw new ParamException(errorMsg, HttpStatus.UNPROCESSABLE_ENTITY, parameters);
    }

    private static void fail(String errorMsg, HttpStatus httpStatus) {
        throw new ParamException(errorMsg, httpStatus);
    }

    public static void main(String[] args) {
        System.out.println(isJavaClass(Date.class)); // true
        System.out.println(isJavaClass(TableEntity.class)); // false
    }
}
