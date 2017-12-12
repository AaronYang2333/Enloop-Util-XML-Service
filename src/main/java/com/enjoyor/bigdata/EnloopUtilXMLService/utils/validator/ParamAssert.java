package com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ParamException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;

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
     * 判断字符串是否为空("")，为空则替换
     *
     * @param originalStr
     * @param alternativeStr
     */
    public static void ifEmptyThenReplace(String originalStr, String alternativeStr) throws NoSuchFieldException {
        isBlank(originalStr, "传入参数为空，无法替换！");
        Field field = originalStr.getClass().getDeclaredField("value");
        field.setAccessible(true);
        try {
            field.set(originalStr, alternativeStr.toCharArray());
        } catch (IllegalAccessException e) {
            throw new IORuntimeException(field.getClass(), "字符串替换失败！");
        }
    }

    private static void fail(String errorMsg, Object... parameters) {
        throw new ParamException(errorMsg, HttpStatus.UNPROCESSABLE_ENTITY, parameters);
    }

    private static void fail(String errorMsg, HttpStatus httpStatus) {
        throw new ParamException(errorMsg, httpStatus);
    }

}
