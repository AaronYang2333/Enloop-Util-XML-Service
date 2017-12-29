package com.enjoyor.bigdata.EnloopUtilXMLService.controller;

import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 12/12/2017 10:06 AM
 * @email aaron19940628@gmail.com
 * @date 12/12/2017 10:06 AM.
 * @description
 */
@RestController
@RequestMapping("/xml")
@Api(tags = "XML2Something", description = "XML 2 Something API")
public class XML2SthAPI {

    @Autowired
    private XMLService xmlService;

    @RequestMapping(value = "/2json", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "将XML文件内容转换成JSON", notes = "将XML文件内容转换成JSON")
    public ResponseResult<String> xml2Json(@RequestParam(name = "xmlFile") MultipartFile xmlFile) {
        return ResponseResult.ok(xmlService.xml2Json(xmlFile));
    }

    @RequestMapping(value = "/2json/v2", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "将XML字符串内容转换成JSON", notes = "将XML字符串内容转换成JSON")
    @ApiImplicitParam(name = "xml", value = "未格式化的XML字符串", paramType = "query")
    public ResponseResult<String> xml2JsonV2(String xml) {
        return ResponseResult.ok(xmlService.xml2Json(xml));
    }

    @RequestMapping(value = "/2xsd", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "将XML字符串内容转换成XSD", notes = "将XML字符串内容转换成XSD")
    public ResponseResult<String> xml2xsdV2(@RequestParam(name = "xmlFile") MultipartFile xmlFile) {
        String xsdContent = xmlService.xml2xsd(xmlFile);
        System.out.println(xsdContent);
        return ResponseResult.ok(xsdContent);
    }

    @RequestMapping(value = "/2xsd/v2", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "将XML字符串内容转换成XSD", notes = "将XML字符串内容转换成XSD")
    @ApiImplicitParam(name = "xml", value = "未格式化的XML字符串", paramType = "query")
    public ResponseResult<String> xml2xsd(String xml) {
        return ResponseResult.ok(xmlService.xml2xsd(xml));
    }


}
