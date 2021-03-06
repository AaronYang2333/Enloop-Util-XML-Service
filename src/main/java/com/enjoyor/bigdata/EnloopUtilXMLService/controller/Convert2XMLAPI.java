package com.enjoyor.bigdata.EnloopUtilXMLService.controller;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.enjoyor.bigdata.EnloopUtilXMLService.entity.ValidateResult;
import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.JsonUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 11:21
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 11:21.
 * @description
 */
@RestController
@RequestMapping("/xml")
@Api(tags = "Something2XML", description = "Something 2 XML API")
public class Convert2XMLAPI {

    @Autowired
    private XMLService xmlService;

    @RequestMapping(value = "/from/table", method = RequestMethod.POST)
    @ApiOperation(value = "将表格数据转换成XML文件", notes = "将表格数据转换成XML文件，并返回转换后的XML内容")
    public ResponseResult<String> table2XML(@RequestBody List<TableEntity> rowData) {
        return ResponseResult.ok(xmlService.table2Xml(rowData));
    }

    @RequestMapping(value = "/from/xsd/file", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "将XSD文件转换成XML文件并填充内容", notes = "将XSD文件转换成XML文件并填充内容，返回填充后的XML内容")
    public ResponseResult<String> xsd2XML(@RequestParam(name = "xsdFile") MultipartFile xsdFile) {
        return ResponseResult.ok(xmlService.xsd2xml(xsdFile));
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "验证XML文档", notes = "根据给定的XSD文件，验证XML文档的合法性，返回出错内容及行数")
    public ResponseResult<String> xsd2XML(@RequestParam(name = "xsdFile") MultipartFile xsdFile,
                                          @RequestParam(name = "xmlFile") MultipartFile xmlFile) {
        return ResponseResult.ok(JsonUtil.obj2Json(xmlService.validate(xsdFile, xmlFile)));
    }

    @RequestMapping(value = "/format", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "格式化XML的内容", notes = "格式化XML的内容")
    @ApiImplicitParam(name = "rowData", value = "未格式化的XML字符串", paramType = "query")
    public ResponseResult<String> formatXML(String rowData) {
        return ResponseResult.ok(xmlService.formatXML(rowData));
    }

    @RequestMapping(value = "/from/json", method = RequestMethod.POST , produces = {"application/json"})
    @ApiOperation(value = "将JSON数据转换成XML文件", notes = "将JSON数据转换成XML文件，并返回转换后的XML内容")
    public ResponseResult<String> json2xml(String rowData){
        return ResponseResult.ok(xmlService.json2xml(JsonUtil.plain(rowData)));
    }
}
