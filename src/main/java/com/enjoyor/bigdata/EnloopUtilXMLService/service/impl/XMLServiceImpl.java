package com.enjoyor.bigdata.EnloopUtilXMLService.service.impl;

import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.GenXMLUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLParseUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.XMLPrettyPrintUtil;
import org.dom4j.Document;
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
    public String xsd2xml(MultipartFile xsdFile, String localPart) {
        //参数检查
        ParamAssert.isNull(xsdFile, "需要指定XSD，才能生成XML");
        LOGGER.info("接收到XSD文件" + xsdFile.getOriginalFilename() + ",开始转换！");
        //读取XSD文件内容
        StringBuffer xsdDataFromFile = FileUtil.readFile(xsdFile);
        localPart = ParamAssert.ifNullThenReplace(localPart, XMLParseUtil.getLocalPart(xsdDataFromFile.toString()));
        try {
            //首先尝试从本地转换生成XML
            return GenXMLUtil.genXMLInLocal(xsdFile, localPart);
        } catch (Exception e) {
            //出错后，远程调用解析XSD
            return GenXMLUtil.genXMLByHTTP(xsdDataFromFile);
        }
    }

}
