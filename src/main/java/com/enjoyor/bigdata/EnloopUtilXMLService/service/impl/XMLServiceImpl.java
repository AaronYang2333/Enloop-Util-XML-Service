package com.enjoyor.bigdata.EnloopUtilXMLService.service.impl;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.ValidateResult;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.Dom4jException;
import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.CmdUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.JsonUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.ResponseResult;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.GenXMLUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLParseUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLPrettyPrintUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLValidator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 11:42
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 11:42.
 * @description
 */
@Service
public class XMLServiceImpl implements XMLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLServiceImpl.class);

    private static final String XML_SAVE_PATH = "xml/";

    @Override
    public String table2Xml(List<?> entityList) {
        //参数检查
        ParamAssert.isNull(entityList, "List<TableEntity> 不能为空");
        LOGGER.info(" 传入List<?>类型对象 值属性正常！");
        //创建Document对象
        Document document = DocumentHelper.createDocument();
        //将LIST转换成Document
        document = XMLParseUtil.list2Document(entityList, document);
        //生成XML并格式化
        return XMLPrettyPrintUtil.prettyPrint(document);
    }

    @Override
    public String xsd2xml(MultipartFile xsdFile) {
        //文件参数检查
        ParamAssert.isNull(xsdFile, "需要指定XSD，才能生成XML");
        LOGGER.info("接收到XSD文件" + xsdFile.getOriginalFilename() + ",开始转换！");
        //读取XSD文件内容
        StringBuffer xsdDataFromFile = FileUtil.readFile(xsdFile);
        try {
            //首先尝试从本地转换生成XML
            LOGGER.info("尝试采用本地解析策略...");
            return GenXMLUtil.genXMLInLocal(xsdFile, XMLParseUtil.getLocalPart(xsdDataFromFile));
        } catch (Exception e) {
            //出错后，远程调用解析XSD
            LOGGER.info("本地策略解析失败，尝试外部解析！");
            return GenXMLUtil.genXMLByHTTP(xsdDataFromFile);
        }
    }

    @Override
    public ValidateResult validate(MultipartFile xsdFile, MultipartFile xmlFile) {
        return XMLValidator.validateWithSingleSchema(xsdFile, xmlFile);
    }

    @Override
    public String formatXML(String xmlContent) {
        ParamAssert.isBlank(xmlContent, "XML内容不能为空！");
        try {
            Document document = DocumentHelper.parseText(xmlContent);
            return XMLPrettyPrintUtil.prettyPrint(document);
        } catch (DocumentException e) {
            throw new Dom4jException("不合法的XML文档内容！");
        }
    }

    @Override
    public String xml2Json(MultipartFile xmlFile) {
        ParamAssert.isNull(xmlFile, "XML文件内容不能为空");
        StringBuffer xmlContent = FileUtil.readFile(xmlFile);
        return xml2Json(xmlContent.toString());
    }

    @Override
    public String xml2Json(String xmlContent) {
        ParamAssert.isBlank(xmlContent, "XML内容不能为空！");
        return JsonUtil.format(JsonUtil.xml2Json(xmlContent));
    }

    @Override
    public String xml2xsd(MultipartFile xmlFile) {
        ParamAssert.isNull(xmlFile, "XML文件内容不能为空");
        StringBuffer xmlContent = FileUtil.readFile(xmlFile);
        return xml2xsd(xmlContent.toString());
    }

    @Override
    public String xml2xsd(String xmlContent) {
        ParamAssert.isBlank(xmlContent, "XML内容不能为空！");
        //write a temp file which contain parameter's content in local file system 先在本地写一个文件
        Map<String, Object> saveFileResult = FileUtil.saveFile(XML_SAVE_PATH, xmlContent);
        String xmlSavedPath = saveFileResult.get(FileUtil.XML_GEN_PATH).toString();
        //then use 'java -jar trang.jar aaa.xml bbb.xsd' to generate a xsd file 用命令转换
        Map<String, String> genXsdResult = CmdUtil.trang(xmlSavedPath);
        //after translation, u can get the path where the xsd file generated 转换完成之后，读取生成的XSD文件
        String xsdSavedPath = genXsdResult.get(CmdUtil.XSD_GEN_PATH).toString();
        //read the file and write the file's content into the response entity 返回文件内容
        return FileUtil.readFile(xsdSavedPath).toString();
    }

}
