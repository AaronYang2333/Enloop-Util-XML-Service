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
     * @param entityList
     * @return
     */
    String table2Xml(List<?> entityList);

    /**
     * 读取XSD文件内容并生成XML
     * @param xsdFile
     * @param localPart
     * @return
     */
    String xsd2xml(MultipartFile xsdFile, String localPart);

    /**
     * 根据给定的XSD文档验证XML文件是否合法
     * @param xsdFile
     * @param xmlFile
     * @return
     */
    ValidateResult validate(MultipartFile xsdFile, MultipartFile xmlFile);
}
