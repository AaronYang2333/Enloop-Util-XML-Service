package com.enjoyor.bigdata.EnloopUtilXMLService.utils;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.POJO;
import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static boolean isJavaClass(Class<?> clazz) {
        return clazz != null && clazz.getClassLoader() == null;
    }

    public static void main(String[] args) {
        System.out.println(isJavaClass(Date.class)); // true
        System.out.println(isJavaClass(TableEntity.class)); // false
    }

}
