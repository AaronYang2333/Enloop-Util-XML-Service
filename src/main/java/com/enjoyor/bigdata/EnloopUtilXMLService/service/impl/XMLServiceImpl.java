package com.enjoyor.bigdata.EnloopUtilXMLService.service.impl;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.ValidateResult;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.Dom4jException;
import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.GenXMLUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLParseUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLPrettyPrintUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLValidator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public String formatXML(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            return XMLPrettyPrintUtil.prettyPrint(document);
        } catch (DocumentException e) {
            throw new Dom4jException("不合法的XML文档内容！");
        }
    }

}
