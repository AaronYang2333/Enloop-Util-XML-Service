package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.Dom4jException;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 15:00
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 15:00.
 * @description
 */
public class XMLPrettyPrintUtil {

    /**
     * 格式化XML
     *
     * @param xml
     * @return
     */
    public static String prettyPrint(Document xml) {
        Document doc = null;
        try {
            String xmlStr = xml.asXML().toString();
            SAXReader reader = new SAXReader();
            StringReader in = new StringReader(xmlStr);
            doc = reader.read(in);
        } catch (DocumentException e) {
            throw new Dom4jException("读取Document对象时发生错误！");
        } catch (NullPointerException e) {
            throw new NullPointerException("Document对象可能为空！");
        }
        OutputFormat formater = OutputFormat.createPrettyPrint();
        formater.setEncoding("UTF-8");
        StringWriter out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, formater);
        try {
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            throw new IORuntimeException(XMLWriter.class, "IO操作发生异常");
        }
        return out.toString();
    }

    public static String prettyPrint(String xmlStr) {
        return StringEscapeUtils.unescapeJava(xmlStr);
    }

    public static void main(String[] args) {
        String s = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n<root mode=\\\"1\\\" objName=\\\"com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity\\\" tableName=\\\"XXXX\\\"><com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity><column1>string</column1><column2>0</column2><column3>2017-11-25 15:37:17</column3><column4>0</column4><column5>com.enjoyor.bigdata.EnloopUtilXMLService.entity.POJO@7bcd9dbc</column5><column6>string</column6><column7>string</column7></com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity></root>";
        String s1 = prettyPrint(s);
        System.out.println(s1);
    }

}
