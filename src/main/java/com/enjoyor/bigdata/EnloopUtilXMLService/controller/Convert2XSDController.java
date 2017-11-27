package com.enjoyor.bigdata.EnloopUtilXMLService.controller;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.enjoyor.bigdata.EnloopUtilXMLService.service.XMLService;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/table", method = RequestMethod.POST)
    @ApiOperation(value = "将表格数据转换成XML文件", notes = "将表格数据转换成XML文件，并返回转换后的XML内容")
    public ResponseResult table2XML(@RequestBody List<TableEntity> entityList) {
        String xml = xmlService.table2Xml(entityList);
        System.out.println(xml);
        return ResponseResult.ok(xml);
    }
}
