package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.ConvertException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import static com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert.isJavaClass;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/27 13:50
 * @email aaron19940628@gmail.com
 * @date 2017/11/27 13:50.
 * @description
 */
public class XMLParseUtil {

    /**
     * 实体对象LIST转换成Document
     *
     * @param entityList
     * @param document
     * @return
     */
    public static Document list2Document(List<?> entityList, Document document) {
        if (null == document) {
            document = DocumentHelper.createDocument();
        }
        //构建根节点
        Element _root = document.addElement("root");
        String elementName = entityList.get(0).getClass().getName();
        //设置根节点的第一项为模式
        _root.addAttribute("mode", String.valueOf(1));
        //设置根节点第二项为类名
        _root.addAttribute("objName", elementName);
        //设置根节点第三项为数据库表名
        _root.addAttribute("tableName", "XXXX");
        //遍历list中所有的类
        traversalList(entityList, _root);
        return document;
    }


    private static void traversalList(List<?> entityList, Element _root) {
        //获得List中的对象CLASS
        String elementClassName = entityList.get(0).getClass().getName();
        //获取List中的对象类中所有的字段
        Field[] fields = entityList.get(0).getClass().getDeclaredFields();
        for (Iterator localIterator = entityList.iterator(); localIterator.hasNext(); ) {
            //item --> List中实体对象
            Object item = localIterator.next();
            Element elementTemp = _root.addElement(elementClassName);
            //遍历类中的每一项，来确定将类中的值转化为我们所需要的添加入document
            for (int i = 0; i < fields.length; i++) {
                if (!fields[i].getName().equals("serialVersionUID")) {
                    Element filedElement = elementTemp.addElement(fields[i].getName());//实体对象的属性节点
                    fields[i].setAccessible(true);//要先设成TRUE
                    Object objItemValue = null; //实体对象每个属性的值
                    try {
                        objItemValue = fields[i].get(item);
                    } catch (Exception e) {
                        throw new ConvertException(item, Object.class, "CLASS反射失败！");
                    }
                    if (isJavaClass(fields[i].getType())) {
                        //如果列表中的实体对象的属性类型 是JAVA自带类型 （String, List，Map + 8种基本类型）
                        XMLValueProvider.getJavaValue(objItemValue, filedElement);
                    } else if (!isJavaClass(fields[i].getType())) {
                        //如果是自定义类型,则需要再次进行遍历，生成节点
                        XMLValueProvider.getPojoElement(objItemValue, filedElement);
                    }
                    fields[i].setAccessible(fields[i].isAccessible());//然后在进行更改，不然会报错
                }
            }
        }
    }

    public static String getLocalPart(StringBuffer xsdDataBuffer) {
        String row = xsdDataBuffer.toString().split("name")[1];
        String[] split = row.split("[=|><]");
        for (String temp : split) {
            if (!"".equals(temp) && !temp.contains("xs:")) {
                return temp.substring(1, temp.length() - 1);
            }
        }
        return "";
    }
}
