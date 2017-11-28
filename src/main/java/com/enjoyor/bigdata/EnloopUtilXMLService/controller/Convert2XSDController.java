package com.enjoyor.bigdata.EnloopUtilXMLService.controller;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
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
@Api(tags = "Something2XML", description = "Convert XSD API")
public class Convert2XSDController {

    @Autowired
    private XMLService xmlService;

    @RequestMapping(value = "/from/table", method = RequestMethod.POST)
    @ApiOperation(value = "将表格数据转换成XML文件", notes = "将表格数据转换成XML文件，并返回转换后的XML内容")
    public ResponseResult table2XML(@RequestBody List<TableEntity> entityList) {
        String xml = xmlService.table2Xml(entityList);
        System.out.println(xml);
        return ResponseResult.ok(xml);
    }

    @RequestMapping(value = "/from/xsd/file", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "将XSD文件转换成XML文件并填充内容", notes = "将XSD文件转换成XML文件并填充内容，返回填充后的XML内容")
    @ApiImplicitParam(name = "localPart", value = "指定XSD文件中需要转换的Element名称,（可不填，默认第一个）",paramType = "form")
    public ResponseResult xsd2xml(@RequestParam(name = "xsdFile") MultipartFile xsdFile,String localPart) {
        String xml = xmlService.xsd2xml(xsdFile,localPart);
        System.out.println(xml);
        return ResponseResult.ok(xml);
    }

}
