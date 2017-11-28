package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/10/19 13:16
 * @email aaron19940628@gmail.com
 * @date 2017/10/19 13:16.
 * @description
 */
public class JsonUtil {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();


    public static String obj2Json(Object object) {
        try {
            String string = MAPPER.writeValueAsString(object);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> objList2strList(List<?> list, Class<?> objClass) {
        List strings = new ArrayList<String>();
        try {
            for (int i = 0; i < list.size(); i++) {
                String string = MAPPER.writeValueAsString(list.get(i));
                strings.add(string);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return strings;
    }

//    public static <T> T json2obj(String json, Class<T> objectClass){
//        T obj = JSON.parseObject(json, new TypeReference<T>() {});
//        return obj;
//    }

    public static <T> T json2obj(String json, Class<T> objectClass) {
        T obj = null;
        try {
            obj = MAPPER.readValue(json, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
