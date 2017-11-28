package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ConvertException;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.DateUtil;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/26 14:08
 * @email aaron19940628@gmail.com
 * @date 2017/11/26 14:08.
 * @description
 */
public class XMLValueProvider {

    /**
     * 根据JAVA类型去生成对应的Value
     *
     * @param objItemValue
     * @return
     */
    public static void getJavaValue(Object objItemValue, Element filedElement) {
        if (objItemValue == null) {
            filedElement.setText("");
        } else if (objItemValue instanceof Float) {
            filedElement.addAttribute("type", "java.lang.Float");
            filedElement.setText(objItemValue.toString() + "f");
        } else if (objItemValue instanceof Double) {
            filedElement.addAttribute("type", "java.lang.Double");
            filedElement.setText(objItemValue.toString() + "d");
        } else if (objItemValue instanceof Long) {
            filedElement.addAttribute("type", "java.lang.Long");
            filedElement.setText(objItemValue.toString() + "l");
        } else if (objItemValue instanceof Date) {
            filedElement.setText(DateUtil.format((Date) objItemValue, DateUtil.DATE_TIME_PATTERN));
        } else if (objItemValue instanceof List) {
            //如果是LIST 需要创建List.size()个同级节点，同时节点名称相同
            Element superElement = filedElement.getParent();
            for (int i = 0; i < ((List) objItemValue).size(); i++) {
                superElement.addElement(filedElement.getName()).setText(String.valueOf(((List) objItemValue).get(i)));
            }
            filedElement.detach();//移除原始的节点
        } else if (objItemValue instanceof Map) {
            //如果是MAP 类型 需要在当前节点下创建Map.EntrySet().size()个子节点，MAP的KEY作为节点名称，Value作为内容
            Iterator iterator = ((Map) objItemValue).entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                filedElement.addElement(String.valueOf(entry.getKey())).setText((String) entry.getValue());
            }
        } else {
            filedElement.setText(objItemValue.toString());
        }
    }

    public static void getPojoElement(Object objItemValue, Element filedElement) {
        Field[] fields = objItemValue.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("serialVersionUID")) {
                Element tempElement = filedElement.addElement(field.getName());//实体对象的属性节点
                field.setAccessible(true);//要先设成TRUE
                Object tempValue = null; //实体对象每个属性的值
                try {
                    tempValue = field.get(objItemValue);
                } catch (Exception e) {
                    throw new ConvertException(objItemValue, Object.class, "CLASS反射失败！");
                }
                getJavaValue(tempValue, tempElement);
                field.setAccessible(field.isAccessible());//然后在进行更改，不然会报错
            }
        }
    }
}
