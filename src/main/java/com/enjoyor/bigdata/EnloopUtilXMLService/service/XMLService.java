package com.enjoyor.bigdata.EnloopUtilXMLService.service;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.ValidateResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 11:41
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 11:41.
 * @description
 */
public interface XMLService {

    /**
     * 将列表内容转换成XML
     *
     * @param entityList 实体对象list
     * @return 生成的xml内容
     */
    String table2Xml(List<?> entityList);

    /**
     * 读取XSD文件内容并生成XML
     *
     * @param xsdFile xsd文件对象
     * @return 生成的xml内容
     */
    String xsd2xml(MultipartFile xsdFile);

    /**
     * 根据给定的XSD文档验证XML文件是否合法
     *
     * @param xsdFile
     * @param xmlFile
     * @return
     */
    ValidateResult validate(MultipartFile xsdFile, MultipartFile xmlFile);

    /**
     * 格式化XML文档的内容
     *
     * @param xmlContent 未格式化的XML字符串
     * @return  格式化后的XML字符串
     */
    String formatXML(String xmlContent);

    /**
     * XML转JSON
     *
     * @param xmlFile XML文件对象
     * @return  转换后的JSON字符串
     */
    String xml2Json(MultipartFile xmlFile);

    /**
     * XML转JSON
     *
     * @param xmlContent XML字符串
     * @return  转换后的JSON字符串
     */
    String xml2Json(String xmlContent);

    /**
     * XML转XSD
     * @param xmlFile XML文件对象
     * @return
     */
    String xml2xsd(MultipartFile xmlFile);

    /**
     * XML转XSD
     * @param xmlContent    XML字符串
     * @return
     */
    String xml2xsd(String xmlContent);

    /**
     * JSON转XML
     * @param json  JSON字符串
     * @return
     */
    String json2xml(String json);

    /**
     * 将XML字符串内容根据XSL文件转换XHTML或XML
     * @param xmlFile   XML文件对象
     * @param xslFile   XSL文件对象
     * @return
     */
    String xml2sthByXsl(MultipartFile xmlFile, MultipartFile xslFile);

    /**
     * 压缩XML内容
     * @param xmlContent    XML字符串
     * @return
     */
    String compress(String xmlContent);
}
