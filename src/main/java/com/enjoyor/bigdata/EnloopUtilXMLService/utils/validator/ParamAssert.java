package com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ParamException;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.TransformRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
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
//            TransformerFactory factory = TransformerFactory.newInstance();
//            ByteArrayInputStream byteAlternativeStr = new ByteArrayInputStream(alternativeStr.getBytes());
//            Source alternativeSource = new StreamSource(byteAlternativeStr);
//            Transformer transformer = null;
//            try {
//                transformer = factory.newTransformer(alternativeSource);
//            } catch (TransformerConfigurationException e) {
//                throw new TransformRuntimeException(alternativeSource.getClass(),"TransformerConfigurationException");
//            }
//            ByteArrayInputStream alternativeInputStream = new ByteArrayInputStream(alternativeStr.getBytes());
//            Source text = new StreamSource(alternativeInputStream);
//
//            transformer.transform(text, new StreamResult(outputStream));
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

    public static void main(String[] str) {

            String test = "aaa";
            System.out.println("1--test : " + test);
            String bbb = "2222";
            change(test, bbb);
            System.out.println("2--test : " + test);

    }

    private static void change(String aaa, String bbb){
        try {
            if(null == aaa){
                aaa = new String();
            }
            Field values = aaa.getClass().getDeclaredField("value");
            values.setAccessible(true);
            char[] ref = (char[]) values.get(aaa);
            for(int i = 0; i < ref.length; i++){
                ref[i] = bbb.toCharArray()[i];
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
