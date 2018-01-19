package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil.closeAllStream;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/10/19 13:16
 * @email aaron19940628@gmail.com
 * @date 2017/10/19 13:16.
 * @description
 */
public class JsonUtil {

    private static XmlMapper XMLMAPPER = new XmlMapper();
    // 定义jackson对象
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    private static final String EMPTY_STR = new String("");

    private static final String EMPTY_OBJ = null;

    public static String obj2Json(Object object) {
        try {
            String string = OBJECTMAPPER.writeValueAsString(object);
            return string;
        } catch (JsonProcessingException e) {
            fail(OBJECTMAPPER.getClass(), "obj2Json发生错误");
        }
        return EMPTY_STR;
    }

    public static List<String> objList2strList(List<?> list, Class<?> objClass) {
        List strings = new ArrayList<String>();
        try {
            for (int i = 0; i < list.size(); i++) {
                String string = OBJECTMAPPER.writeValueAsString(list.get(i));
                strings.add(string);
            }
        } catch (JsonProcessingException e) {
            fail(OBJECTMAPPER.getClass(), "objList2strList发生错误");
        }
        return strings;
    }


    public static <T> T json2obj(String json, Class<T> objectClass) {
        try {
            return OBJECTMAPPER.readValue(json, objectClass);
        } catch (IOException e) {
            fail(OBJECTMAPPER.getClass(), "JSON2OBJ发生错误");
        }
        return (T) EMPTY_OBJ;
    }

    public static String xml2Json(String xmlContent) {
        StringWriter stringWriter = new StringWriter();
        try {
            JsonParser jsonParser = XMLMAPPER.getFactory().createParser(xmlContent);
            JsonGenerator jsonGenerator = OBJECTMAPPER.getFactory().createGenerator(stringWriter);
            while (jsonParser.nextToken() != null) {
                jsonGenerator.copyCurrentEvent(jsonParser);
            }
            closeAllStream(jsonParser,jsonGenerator);
        } catch (Exception e) {
            fail(OBJECTMAPPER.getClass(), "XML2JSON发生错误");
        }
        return stringWriter.toString();
    }

    public static String format(String rowJson) {
        try {
            Object obj = OBJECTMAPPER.readValue(rowJson, Object.class);
            return OBJECTMAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            fail(OBJECTMAPPER.getClass(), "格式化JSON发生错误");
        }
        return EMPTY_OBJ;
    }

    public static String plain(String prettyJson){
        return prettyJson.replaceAll("[\\s&&[^\r\n]]*(?:[\r\n][\\s&&[^\r\n]]*)+", "");
    }

    private static void fail(Class<?> clazz, String errorMsg) {
        throw new IORuntimeException(clazz, errorMsg);
    }

}
